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
import java.util.ArrayList;

public class ProtoWorker implements Runnable, IObserver{
    private final IService server;
    private final Socket connection;
    private InputStream input;
    private OutputStream output;
    private volatile boolean connected;

    public ProtoWorker(IService server, Socket connection){
        this.server = server;
        this.connection = connection;
        try{
            output = connection.getOutputStream();
            input = connection.getInputStream();
            connected = true;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(connected){
            try{
                //Object request=input.readObject();
                System.out.println("Waiting requests ...");
                Protobufs.Request request = Protobufs.Request.parseDelimitedFrom(input);
                System.out.println("Request received: " + request);
                Protobufs.Response response = handleRequest(request);
                if(response != null){
                    sendResponse(response);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        try{
            input.close();
            output.close();
            connection.close();
        }catch(IOException e){
            System.out.println("Error " + e);
        }
    }

    @Override
    public void refreshAll(Test test){
        System.out.println("RefreshAll ... with test: " + test);
        try{
            sendResponse(ProtoUtils.createRefreshAllResponse(test));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private Protobufs.Response handleRequest(Protobufs.Request request){
        switch(request.getType()){
            case LOGIN:{
                System.out.println("Login request ...");
                Referee referee = ProtoUtils.getReferee(request);
                try{
                    Referee loggedReferee = server.login(referee, this);
                    return ProtoUtils.createOkResponse(loggedReferee);
                }catch(MyException e){
                    connected = false;
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case LOGOUT:{
                System.out.println("Logout request");
                Referee referee = ProtoUtils.getReferee(request);
                try{
                    server.logout(referee, this);
                    connected = false;
                    return ProtoUtils.createOkResponse();

                }catch(MyException e){
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case ADD_TEST:{
                System.out.println("ADD_TEST Request ...");
                Test test = ProtoUtils.getTest(request);
                try{
                    server.addTest(test);
                    return ProtoUtils.createOkResponse(test);
                }catch(MyException e){
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case GET_ALL_PARTICIPANTS:{
                System.out.println("Get_All_Participants Request ...");
                try{
                    ArrayList<Participant> participants =
                            (ArrayList<Participant>) server.getAllParticipants();
                    return ProtoUtils.createGetAllParticipantsResponse(participants);
                }catch(MyException e){
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }

            }
            case GET_ALL_ACTIVE_PARTICIPANTS:{
                System.out.println("Get_All_Active_Participants Request ...");
                int id = ProtoUtils.getId(request);
                try{
                    ArrayList<Participant> activeParticipants =
                            (ArrayList<Participant>) server.getAllActiveParticipants(id);
                    return ProtoUtils.createGetAllActiveParticipantsResponse(activeParticipants);
                }catch(MyException e){
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
        }
        return null;
    }

    private void sendResponse(Protobufs.Response response) throws IOException{
        System.out.println("sending response " + response);
        response.writeDelimitedTo(output);
        //output.writeObject(response);
        output.flush();
    }
}
