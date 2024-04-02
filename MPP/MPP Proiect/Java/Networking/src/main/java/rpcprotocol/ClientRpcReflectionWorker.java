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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;

public class ClientRpcReflectionWorker implements Runnable, IObserver{

    private final IService service;
    private final Socket connection;

    private static final Logger logger = LogManager.getLogger();

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ClientRpcReflectionWorker(IService service, Socket connection){
        logger.info("Creating worker");
        this.service = service;
        this.connection = connection;
        try{
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
            logger.info("Worker created");
        } catch(IOException e){
            logger.error(e);
        }
    }

    @Override
    public void run(){
        while(connected){
            try{
                Object request = input.readObject();
                Response response = handleRequest((Request) request);
                if(response != null){
                    sendResponse(response);
                }
            } catch(IOException | ClassNotFoundException e){
                logger.error("Error in worker (reading): " + e);
            }
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                logger.error("Error in worker (sleeping): " + e);
            }
        }
        try{
            input.close();
            output.close();
            connection.close();
        } catch(IOException e){
            logger.error("Error in worker (closing connection): " + e);
        }
    }

    private void sendResponse(Response response) throws IOException{
        logger.traceEntry("method entered: sendResponse with parameters " + response);
        output.writeObject(response);
        output.flush();
        logger.info("Response sent");
    }

    private static final Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request){
        Response response = null;
        String handlerName = "handle" + (request).type();
        logger.traceEntry("method entered: " + handlerName + " with parameters " + request);
        try{
            Method method = this.getClass().getDeclaredMethod(handlerName, Request.class);
            response = (Response) method.invoke(this, request);
            logger.info("Method invoked: " + handlerName + " with response " + response);
        } catch(NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
            e.printStackTrace();
            logger.error("Error in worker (invoking method handleRequest): " + e);
        }
        return response;
    }

    private Response handleLOGIN(Request request){
        logger.trace("method entered: handleLOGIN with parameters " + request);
        Referee referee = (Referee) request.data();
        try{
            Referee foundReferee = service.login(referee, this);
            logger.info("Referee logged in");
            return new Response.Builder().type(ResponseType.OK).data(foundReferee).build();
        } catch(MyException e){
            connected = false;
            logger.error("Error in worker (solving method handleLOGIN): " + e);
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleADD_TEST(Request request){
        logger.traceEntry("method entered: handleADD_TEST with parameters " + request);
        Test test = (Test) request.data();
        try{
            service.addTest(test);
            logger.info("Ticket bought");
            return new Response.Builder().type(ResponseType.OK).data(test).build();
        } catch(MyException e){
            logger.error("Error in worker (solving method handleADD_TEST): " + e);
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_ALL_PARTICIPANTS(Request request){
        logger.traceEntry("method entered: handleGET_ALL_PARTICIPANTS with parameters " + request);
        try{
            ArrayList<Participant> participants = (ArrayList<Participant>) service.getAllParticipants();
            logger.info("PARTICIPANTS found " + participants);
            return new Response.Builder().type(ResponseType.GET_ALL_PARTICIPANTS).data(participants).build();
        } catch(MyException e){
            logger.error("Error in worker (solving method handleGET_ALL_PARTICIPANTS): " + e);
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_ALL_ACTIVE_PARTICIPANTS(Request request){
        logger.traceEntry("method entered: handleGET_ALL_PARTICIPANTS with parameters " + request);
        int id = (int) request.data();
        try{
            ArrayList<Participant> participants = (ArrayList<Participant>) service.getAllActiveParticipants(id);
            logger.info("PARTICIPANTS found " + participants);
            return new Response.Builder().type(ResponseType.GET_ALL_PARTICIPANTS).data(participants).build();
        } catch(MyException e){
            logger.error("Error in worker (solving method handleGET_ALL_PARTICIPANTS): " + e);
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request){
        logger.traceEntry("method entered: handleLOGOUT with parameters " + request);
        Referee referee = (Referee) request.data();
        try{
            service.logout(referee, this);
            connected = false;
            logger.info("Referee logged out");
            return new Response.Builder().type(ResponseType.OK).build();
        } catch(MyException e){
            logger.error("Error in worker (solving method handleLOGOUT): " + e);
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    @Override
    public void refreshAll(Test test){
        Response resp= new Response.Builder().type(ResponseType.TEST_ADDED).data(test).build();
        logger.info("RefreshAll invoked");
        try {
            sendResponse(resp);
            logger.info("Response sent");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error in worker (sending response): "+e);
        }
    }
}
