package rpcprotocol;


import model.Participant;
import model.Referee;
import model.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IObserver;
import service.IService;
import service.MyException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public
class ServicesRpcProxy implements IService{

    private final String host;
    private final int port;

    private IObserver refereeObserver;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private static final Logger logger = LogManager.getLogger();

    private final BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ServicesRpcProxy(String host, int port){
        logger.info("ServicesRpcProxy created");
        this.host = host;
        this.port = port;
        this.qresponses = new LinkedBlockingQueue<>();
    }

    public void logout(Referee referee, IObserver client) throws MyException{
        Request req =
                new Request.Builder().type(RequestType.LOGOUT).data(referee).build();
        sendRequest(req);
        Response response = readResponse();
        closeConnection();
        if(response.type() == ResponseType.ERROR){
            logger.error("Error logging out" + response.data().toString());
            String err = response.data().toString();
            throw new MyException(err);
        }else{
            logger.info("Logged out");
        }
    }

    private void closeConnection(){
        finished = true;
        try{
            input.close();
            output.close();
            connection.close();
            refereeObserver = null;
            logger.info("Closed connection");
        }catch(IOException e){
            logger.error("Error closing connection: " + e);
        }
    }

    private void sendRequest(Request request) throws MyException{
        try{
            output.writeObject(request);
            output.flush();
            logger.info("Request sent: " + request);
        }catch(IOException e){
            logger.error("Error sending object " + e);
            throw new MyException("Error sending object " + e);
        }
    }

    private Response readResponse() throws MyException{
        Response response;
        try{
            response = qresponses.take();
            logger.info("Response received: " + response);
        }catch(InterruptedException e){
            logger.error("Reading response error " + e);
            throw new MyException("Reading response error " + e);
        }
        return response;
    }

    private void initializeConnection() throws MyException{
        try{
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
            logger.info("Connection initialized");
        }catch(IOException e){
            logger.error("Error connecting to server " + e);
            throw new MyException("Error connecting to server " + e);
        }
    }

    private void startReader(){
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response){
        if(response.type() == ResponseType.TEST_ADDED){
            logger.info("Test added.");
            refereeObserver.refreshAll((Test) response.data());
        }
    }

    private boolean isUpdate(Response response){
        return response.type() == ResponseType.TEST_ADDED;
    }


    @Override
    public Referee login(Referee referee, IObserver refereeObserver) throws MyException{
        initializeConnection();
        Request req =
                new Request.Builder().type(RequestType.LOGIN).data(referee).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type() == ResponseType.OK){
            this.refereeObserver = refereeObserver;
            logger.info("Logged in " + referee.toString());
            return (Referee) response.data();
        }
        if(response.type() == ResponseType.ERROR){
            logger.error("Error logging in" + response.data().toString());
            String err = response.data().toString();
            closeConnection();
            throw new MyException(err);
        }
        return null;
    }

    @Override
    public Iterable<Participant> getAllParticipants() throws MyException{
        Request req = new Request.Builder().type(RequestType.GET_ALL_PARTICIPANTS)
                .data(null).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type() == ResponseType.ERROR){
            logger.error("Error getting participants" + response.data().toString());
            String err = response.data().toString();
            throw new MyException(err);
        }else{
            logger.info("Got participants");
        }
        return (ArrayList<Participant>) response.data();
    }

    @Override
    public Iterable<Participant> getAllActiveParticipants(int id) throws MyException{
        Request req = new Request.Builder().type(RequestType.GET_ALL_ACTIVE_PARTICIPANTS)
                .data(id).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type() == ResponseType.ERROR){
            logger.error(
                    "Error getting active participants" + response.data().toString());
            String err = response.data().toString();
            throw new MyException(err);
        }else{
            logger.info("Got active participants");
        }
        return (ArrayList<Participant>) response.data();
    }

    @Override
    public void addTest(Test test) throws MyException{
        Request req =
                new Request.Builder().type(RequestType.ADD_TEST).data(test).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type() == ResponseType.ERROR){
            logger.error("Error adding test" + response.data().toString());
            String err = response.data().toString();
            throw new MyException(err);
        }else{
            logger.info("Test added");
        }
    }

    private
    class ReaderThread implements Runnable{
        public void run(){
            while(!finished){
                try{
                    Object response = input.readObject();
                    logger.info("response received " + response);
                    if(isUpdate((Response) response)){
                        handleUpdate((Response) response);
                    }else{
                        try{
                            qresponses.put((Response) response);
                        }catch(InterruptedException e){
                            logger.error("Queue putting response error: " + e);
                        }
                    }
                }catch(IOException | ClassNotFoundException e){
                    if(e instanceof SocketException)
                        logger.info("Socket closed: " + e);
                    else
                        logger.error("Reading error: " + e);
                }
            }
        }
    }
}
