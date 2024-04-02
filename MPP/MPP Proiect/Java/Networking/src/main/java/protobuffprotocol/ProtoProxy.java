package protobuffprotocol;

import model.Participant;
import model.Referee;
import model.Test;
import service.IObserver;
import service.IService;
import service.MyException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoProxy implements IService{
    private final String host;
    private final int port;

    private IObserver client;

    private InputStream input;
    private OutputStream output;
    private Socket connection;

    private final BlockingQueue<Protobufs.Response> qresponses;
    private volatile boolean finished;

    public ProtoProxy(String host, int port){
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<>();
    }


    @Override
    public Referee login(Referee referee, IObserver refereeObserver) throws MyException{
        initializeConnection();
        System.out.println("Login request ...");
        sendRequest(ProtoUtils.createLoginRequest(referee));
        Protobufs.Response response = readResponse();
        if(response.getType() == Protobufs.Response.Type.OK){
            this.client = refereeObserver;
        }
        if(response.getType() == Protobufs.Response.Type.ERROR){
            String errorText = ProtoUtils.getError(response);
            closeConnection();
            throw new MyException(errorText);
        }
        return ProtoUtils.getReferee(response);
    }

    @Override
    public Iterable<Participant> getAllParticipants() throws MyException{
        sendRequest(ProtoUtils.createGetAllParticipantsRequest());
        Protobufs.Response response = readResponse();
        if(response.getType() == Protobufs.Response.Type.ERROR){
            String errorText = ProtoUtils.getError(response);
            throw new MyException(errorText);
        }
        return ProtoUtils.getAllParticipants(response);
    }

    @Override
    public void addTest(Test test) throws MyException{
        sendRequest(ProtoUtils.createAddTestRequest(test));
        Protobufs.Response response = readResponse();
        if(response.getType() == Protobufs.Response.Type.ERROR){
            String errorText = ProtoUtils.getError(response);
            throw new MyException(errorText);
        }
    }

    @Override
    public Iterable<Participant> getAllActiveParticipants(int id) throws MyException{
        sendRequest(ProtoUtils.createGetAllActiveParticipantsRequest(id));
        Protobufs.Response response = readResponse();
        if(response.getType() == Protobufs.Response.Type.ERROR){
            String errorText = ProtoUtils.getError(response);
            throw new MyException(errorText);
        }
        return ProtoUtils.getAllParticipants(response);
    }

    @Override
    public void logout(Referee referee, IObserver refereeObserver) throws MyException{
        sendRequest(ProtoUtils.createLogoutRequest(referee));
        Protobufs.Response response = readResponse();
        closeConnection();
        if(response.getType() == Protobufs.Response.Type.ERROR){
            String errorText = ProtoUtils.getError(response);
            throw new MyException(errorText);
        }
    }

    private void closeConnection(){
        finished = true;
        try{
            input.close();
            output.close();
            connection.close();
            client = null;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void sendRequest(Protobufs.Request request) throws MyException{
        try{
            System.out.println("Sending request ..." + request);
            //request.writeTo(output);
            request.writeDelimitedTo(output);
            output.flush();
            System.out.println("Request sent.");
        }catch(IOException e){
            throw new MyException("Error sending object " + e);
        }

    }

    private Protobufs.Response readResponse() throws MyException{
        Protobufs.Response response = null;
        try{
            response = qresponses.take();

        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws MyException{
        try{
            connection = new Socket(host, port);
            output = connection.getOutputStream();
            //output.flush();
            input = connection.getInputStream();
            //new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void startReader(){
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(Protobufs.Response updateResponse){
        if(updateResponse.getType() == Protobufs.Response.Type.TEST_ADDED){
            Test test = ProtoUtils.getTest(updateResponse);
            client.refreshAll(test);
        }

    }

    private class ReaderThread implements Runnable{
        public void run(){
            while(!finished){
                try{
                    Protobufs.Response response = Protobufs.Response.parseDelimitedFrom(input);
                    System.out.println("response received " + response);

                    if(isUpdateResponse(response.getType())){
                        handleUpdate(response);
                    }else{
                        try{
                            qresponses.put(response);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }catch(IOException e){
                    System.out.println("Reading error " + e);
                }
            }
        }

    }

    private boolean isUpdateResponse(Protobufs.Response.Type type){
        switch(type){
            case TEST_ADDED:
                return true;
        }
        return false;
    }
}
