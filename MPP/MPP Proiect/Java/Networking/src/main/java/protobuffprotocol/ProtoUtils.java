package protobuffprotocol;

import model.Participant;
import model.Referee;
import model.Test;

import java.util.ArrayList;

public class ProtoUtils{
    public static Test getTest(Protobufs.Response updateResponse){
        Test test = new Test();
        test.setIdParticipant(updateResponse.getTest().getIdParticipant());
        test.setIdReferee(updateResponse.getTest().getIdReferee());
        test.setPoints(updateResponse.getTest().getPoints());
        test.setDate(updateResponse.getTest().getDate());
        test.setId(updateResponse.getTest().getId());
        return test;
    }

    public static Referee getReferee(Protobufs.Response response){
        Referee referee = new Referee();
        referee.setName(response.getReferee().getName());
        referee.setEmail(response.getReferee().getEmail());
        referee.setPassword(response.getReferee().getPassword());
        referee.setId(response.getReferee().getId());
        return referee;
    }

    public static String getError(Protobufs.Response response){
        String error = response.getError();
        return error;
    }

    public static Iterable<Participant> getAllParticipants(Protobufs.Response response){
        ArrayList<Participant> participants = new ArrayList<>();
        for(Protobufs.Participant participant : response.getParticipantsList()){
            Participant participant1 = new Participant(participant.getName(),
                    participant.getTotalPoints());
            participant1.setId(participant.getId());
            participants.add(participant1);
        }
        return participants;
    }


    public static Referee getReferee(Protobufs.Request request){
        Referee referee = new Referee(request.getReferee().getName(),
                request.getReferee().getEmail(), request.getReferee().getPassword());
        referee.setId(request.getReferee().getId());
        return referee;
    }

    public static Test getTest(Protobufs.Request request){
        Test test = new Test();
        test.setIdParticipant(request.getTest().getIdParticipant());
        test.setIdReferee(request.getTest().getIdReferee());
        test.setPoints(request.getTest().getPoints());
        test.setDate(request.getTest().getDate());
        test.setId(request.getTest().getId());
        return test;
    }


    public static int getId(Protobufs.Request request){
        return request.getId();
    }

    public static Protobufs.Request createLoginRequest(Referee referee){
        Protobufs.Referee refereeProtobufs = Protobufs.Referee.newBuilder()
                .setEmail(referee.getEmail()).setPassword(referee.getPassword())
                .build();
        Protobufs.Request request = Protobufs.Request.newBuilder()
                .setType(Protobufs.Request.Type.LOGIN)
                .setReferee(refereeProtobufs)
                .build();
        return request;
    }

    public static Protobufs.Request createAddTestRequest(Test test){
        Protobufs.Test testProtobufs = Protobufs.Test.newBuilder()
                .setIdParticipant(test.getIdParticipant())
                .setIdReferee(test.getIdReferee())
                .setPoints(test.getPoints())
                .setDate(test.getDate())
                .build();
        Protobufs.Request request = Protobufs.Request.newBuilder()
                .setType(Protobufs.Request.Type.ADD_TEST)
                .setTest(testProtobufs)
                .build();
        return request;
    }

    public static Protobufs.Request createLogoutRequest(Referee referee){
        Protobufs.Referee refereeProtobufs = Protobufs.Referee.newBuilder()
                .setId(referee.getId())
                .setName(referee.getName())
                .setEmail(referee.getEmail())
                .setPassword(referee.getPassword())
                .build();
        Protobufs.Request request = Protobufs.Request.newBuilder()
                .setType(Protobufs.Request.Type.LOGOUT)
                .setReferee(refereeProtobufs)
                .build();
        return request;
    }

    public static Protobufs.Request createGetAllParticipantsRequest(){
        Protobufs.Request request = Protobufs.Request.newBuilder()
                .setType(Protobufs.Request.Type.GET_ALL_PARTICIPANTS)
                .build();
        return request;
    }


    public static Protobufs.Request createGetAllActiveParticipantsRequest(int id){
        Protobufs.Request request = Protobufs.Request.newBuilder()
                .setType(Protobufs.Request.Type.GET_ALL_ACTIVE_PARTICIPANTS)
                .setId(id)
                .build();
        return request;
    }

    public static Protobufs.Response createOkResponse(){
        Protobufs.Response response = Protobufs.Response.newBuilder()
                .setType(Protobufs.Response.Type.OK)
                .build();
        return response;
    }

    public static Protobufs.Response createOkResponse(Referee referee){
        Protobufs.Referee refereeProtobufs = Protobufs.Referee.newBuilder()
                .setId(referee.getId())
                .setName(referee.getName())
                .setEmail(referee.getEmail())
                .setPassword(referee.getPassword())
                .build();
        Protobufs.Response response = Protobufs.Response.newBuilder()
                .setType(Protobufs.Response.Type.OK)
                .setReferee(refereeProtobufs)
                .build();
        return response;
    }

    public static Protobufs.Response createOkResponse(Test test){
        Protobufs.Test testProtobufs = Protobufs.Test.newBuilder()
                .setId(test.getId())
                .setIdParticipant(test.getIdParticipant())
                .setIdReferee(test.getIdReferee())
                .setPoints(test.getPoints())
                .setDate(test.getDate())
                .build();
        Protobufs.Response response = Protobufs.Response.newBuilder()
                .setType(Protobufs.Response.Type.OK)
                .setTest(testProtobufs)
                .build();
        return response;
    }

    public static Protobufs.Response createErrorResponse(String message){
        Protobufs.Response response = Protobufs.Response.newBuilder()
                .setType(Protobufs.Response.Type.ERROR)
                .setError(message)
                .build();
        return response;
    }

    public static Protobufs.Response createGetAllParticipantsResponse(ArrayList<Participant> participants){
        Protobufs.Response.Builder response = Protobufs.Response.newBuilder()
                .setType(Protobufs.Response.Type.GET_ALL_PARTICIPANTS);
        for(Participant participant : participants){
            Protobufs.Participant participant1 = Protobufs.Participant.newBuilder()
                    .setId(participant.getId())
                    .setName(participant.getName())
                    .setTotalPoints(participant.getTotalPoints())
                    .build();
            response.addParticipants(participant1);
        }
        return response.build();
    }

    public static Protobufs.Response createGetAllActiveParticipantsResponse(ArrayList<Participant> activeParticipants){
        Protobufs.Response.Builder response = Protobufs.Response.newBuilder()
                .setType(Protobufs.Response.Type.GET_ALL_ACTIVE_PARTICIPANTS);
        for(Participant participant : activeParticipants){
            Protobufs.Participant participant1 = Protobufs.Participant.newBuilder()
                    .setId(participant.getId())
                    .setName(participant.getName())
                    .setTotalPoints(participant.getTotalPoints())
                    .build();
            response.addParticipants(participant1);
        }
        return response.build();
    }

    public static Protobufs.Response createRefreshAllResponse(Test test){
        Protobufs.Test test1 = Protobufs.Test.newBuilder()
                .setId(test.getId())
                .setIdParticipant(test.getIdParticipant())
                .setIdReferee(test.getIdReferee())
                .setPoints(test.getPoints())
                .setDate(test.getDate())
                .build();
        Protobufs.Response response = Protobufs.Response.newBuilder()
                .setType(Protobufs.Response.Type.TEST_ADDED)
                .setTest(test1)
                .build();
        return response;
    }
}
