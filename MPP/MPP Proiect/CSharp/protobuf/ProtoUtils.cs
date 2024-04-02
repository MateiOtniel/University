using System.Collections.Generic;
using Java;

namespace protobuf{
    public class ProtoUtils{
        public static Request CreateLoginRequest(model.Referee referee){
            var myReferee = new Referee{
                Id = referee.id,
                Name = referee.Name,
                Email = referee.Email,
                Password = referee.Password
            };
            return new Request{
                Type = Request.Types.Type.Login,
                Referee = myReferee
            };
        }

        public static Request CreateLogoutRequest(model.Referee referee){
            var myReferee = new Referee{
                Id = referee.id,
                Name = referee.Name,
                Email = referee.Email,
                Password = referee.Password
            };
            return new Request{
                Type = Request.Types.Type.Logout,
                Referee = myReferee
            };
        }

        public static Request CreateAddTestRequest(model.Test test){
            var myTest = new Test{
                IdParticipant = test.idParticipant,
                IdReferee = test.idReferee,
                Points = test.points,
                Date = test.date
            };
            return new Request{
                Type = Request.Types.Type.AddTest,
                Test = myTest
            };
        }

        public static Request CreateAllParticipantsRequest(){
            return new Request{
                Type = Request.Types.Type.GetAllParticipants
            };
        }

        public static Request CreateAllActiveParticipantsRequest(int refereeId){
            return new Request{
                Type = Request.Types.Type.GetAllActiveParticipants,
                Id = refereeId
            };
        }

        public static model.Test GetTest(Response response){
            var test = response.Test;
            return new model.Test(test.IdParticipant, test.IdReferee, test.Points,
                test.Date){
                id = test.Id
            };
        }

        public static model.Referee GetReferee(Response response){
            var referee = response.Referee;
            return new model.Referee(referee.Name, referee.Email, referee.Password){
                id = referee.Id
            };
        }

        public static string GetError(Response response){ return response.Error; }

        public static IEnumerable<model.Participant>
            GetParticipants(Response response){
            List<model.Participant> participants = new List<model.Participant>();
            foreach(var participant in response.Participants){
                model.Participant currentParticipant = new model.Participant(
                    participant.Name,
                    participant.TotalPoints){
                    id = participant.Id
                };
                participants.Add(currentParticipant);
            }

            return participants;
        }

        public static Response CreateLoginResponse(model.Referee referee){
            var myReferee = new Referee{
                Name = referee.Name,
                Email = referee.Email,
                Password = referee.Password
            };
            myReferee.Id = referee.id;
            return new Response{
                Type = Response.Types.Type.Ok,
                Referee = myReferee
            };
        }

        public static Response CreateErrorResponse(string eMessage){
            return new Response{
                Type = Response.Types.Type.Error,
                Error = eMessage
            };
        }

        public static Response CreateOkResponse(){
            return new Response{
                Type = Response.Types.Type.Ok
            };
        }

        public static Response CreateGetAllParticipantsResponse(
            IEnumerable<model.Participant> getAllParticipantsSortedByName){
            var participants = new List<Participant>();
            foreach(var participant in getAllParticipantsSortedByName){
                var myParticipant = new Participant{
                    Id = participant.id,
                    Name = participant.Name,
                    TotalPoints = participant.TotalPoints
                };
                participants.Add(myParticipant);
            }
            return new Response{
                Type = Response.Types.Type.GetAllParticipants,
                Participants ={participants}
            };
        }

        public static Response CreateGetAllActiveParticipantsResponse(
            IEnumerable<model.Participant> getAllActiveParticipants){
            var participants = new List<Participant>();
            foreach(var participant in getAllActiveParticipants){
                var myParticipant = new Participant{
                    Id = participant.id,
                    Name = participant.Name,
                    TotalPoints = participant.TotalPoints
                };
                participants.Add(myParticipant);
            }
            return new Response{
                Type = Response.Types.Type.GetAllActiveParticipants,
                Participants ={participants}
            };
        }


        public static model.Referee GetReferee(Request request){
            var referee = request.Referee;
            return new model.Referee(referee.Name, referee.Email, referee.Password){
                id = referee.Id
            };
        }

        public static model.Test GetTest(Request request){
            var test = request.Test;
            return new model.Test(test.IdParticipant, test.IdReferee, test.Points,
                test.Date){
                id = test.Id
            };
        }

        public static int GetId(Request request){
            return request.Id;
        }

        public static Response CreateTestAddedResponse(model.Test test){
            var myTest = new Test{
                IdParticipant = test.idParticipant,
                IdReferee = test.idReferee,
                Points = test.points,
                Date = test.date,
                Id = test.id
            };
            return new Response{
                Type = Response.Types.Type.TestAdded,
                Test = myTest
            };
        }

       
    }
}