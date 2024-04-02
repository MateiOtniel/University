using System;
using System.Net.Sockets;
using System.Reflection;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using log4net;
using model;
using services;

namespace networking{
    public class ClientRpcReflectionWorker: IObserver{
        private static readonly ILog Logger =
            LogManager.GetLogger("ClientRpcReflectionWorker");

        private readonly IServices _service;
        private readonly TcpClient _connection;
        private readonly NetworkStream _stream;
        private readonly IFormatter _formatter;
        private volatile bool _connected;

        public ClientRpcReflectionWorker(IServices service, TcpClient connection){
            _service = service;
            _connection = connection;
            try{
                _stream = connection.GetStream();
                _formatter = new BinaryFormatter();
                _connected = true;
            } catch(Exception e){
                Console.WriteLine(e.StackTrace);
            }
        }

        public virtual void Run(){
            while(_connected){
                try{
                    var request = _formatter.Deserialize(_stream);
                    object response = HandleRequest((Request)request);
                    if(response != null){
                        SendResponse((Response)response);
                    }
                } catch(Exception e){
                    Console.WriteLine(e.StackTrace);
                }

                try{
                    Thread.Sleep(1000);
                } catch(Exception e){
                    Console.WriteLine(e.StackTrace);
                }
            }

            try{
                _stream.Close();
                _connection.Close();
            } catch(Exception e){
                Console.WriteLine("Error " + e);
            }
        }

        private Response HandleRequest(Request request){
            Response response = null;
            var handlerName = "Handle" + request.Type;
            Console.WriteLine("HandlerName " + handlerName);
            try{
                var method = GetType()
                    .GetMethod(handlerName, new[]{typeof(Request)});
                response = (Response)method.Invoke(this, new[]{request});
                Console.WriteLine("Method " + handlerName + " invoked");
            } catch(TargetInvocationException ex){
                Console.WriteLine(ex.InnerException);
            } catch(AmbiguousMatchException ex){
                Console.WriteLine(ex.Message);
            } catch(TargetParameterCountException ex){
                Console.WriteLine(ex.Message);
            } catch(TargetException ex){
                Console.WriteLine(ex.Message);
            } catch(Exception ex){
                Console.WriteLine(ex.Message);
            }

            return response;
        }

        public Response HandleLOGIN(Request request){
            Logger.InfoFormat("method entered: handleLOGIN with parameters {0}",
                request);
            var referee = (Referee)request.Data;
            try{
                var foundReferee = _service.Login(referee, this);
                Logger.Info("Referee logged in");
                Console.WriteLine("Referee logged in");
                return new Response.Builder().Type(ResponseType.OK).Data(foundReferee)
                    .Build();
            } catch(MyException e){
                _connected = false;
                Logger.Error("Error in worker (solving method handleLOGIN): {0}", e);
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message)
                    .Build();
            }
        }

        public Response HandleLOGOUT(Request request){
            Logger.InfoFormat("method entered: handleLOGOUT with parameters {0}",
                request);
            var referee = (Referee)request.Data;
            try{
                _service.Logout(referee);
                _connected = false;
                Logger.Info("Referee logged out");
                Console.WriteLine("Referee logged out");
                return new Response.Builder().Type(ResponseType.OK).Build();
            } catch(MyException e){
                Logger.Error("Error in worker (solving method handleLOGOUT): {0}", e);
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message)
                    .Build();
            }
        }

        public Response HandleGET_ALL_PARTICIPANTS(Request request){
            Logger.InfoFormat(
                "method entered: handleGET_ALL_PARTICIPANTS with parameters {0}",
                request);
            try{
                var participants = _service.GetAllParticipantsSortedByName();
                Logger.Info("Participants returned");
                Console.WriteLine("Participants returned");
                return new Response.Builder().Type(ResponseType.OK).Data(participants)
                    .Build();
            } catch(MyException e){
                Logger.Error(
                    "Error in worker (solving method handleGET_ALL_PARTICIPANTS): {0}",
                    e);
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message)
                    .Build();
            }
        }

        public Response HandleGET_ALL_ACTIVE_PARTICIPANTS(Request request){
            Logger.InfoFormat(
                "method entered: handleGET_ALL_ACTIVE_PARTICIPANTS with parameters {0}",
                request);
            var id = (int)request.Data;
            try{
                var participants = _service.GetAllActiveParticipants(id);
                Logger.Info("Active participants returned");
                Console.WriteLine("Active participants returned");
                return new Response.Builder().Type(ResponseType.OK).Data(participants)
                    .Build();
            } catch(MyException e){
                Logger.Error(
                    "Error in worker (solving method handleGET_ALL_ACTIVE_PARTICIPANTS): {0}",
                    e);
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message)
                    .Build();
            }
        }

        public Response HandleADD_TEST(Request request){
            Logger.InfoFormat("method entered: handleADD_TEST with parameters {0}",
                request);
            var test = (Test)request.Data;
            try{
                _service.AddTest(test);
                Logger.Info("Test added");
                Console.WriteLine("Test added");
                return new Response.Builder().Type(ResponseType.OK).Build();
            } catch(MyException e){
                Logger.Error("Error in worker (solving method handleADD_TEST): {0}",
                    e);
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message)
                    .Build();
            }
        }

        public void RefreshAll(Test test){
            var response =
                new Response.Builder().Type(ResponseType.TEST_ADDED).Data(test).Build();
            Logger.Info("Sending response " + response);
            Console.WriteLine("Sending response " + response);
            try{
                SendResponse(response);
                Logger.Info("Response sent");
                Console.WriteLine("Response sent");
            } catch(Exception e){
                Logger.Error("Error sending response " + e);
                Console.WriteLine("Error sending response " + e);
            }
        }

        private void SendResponse(Response response){
            Console.WriteLine("sending response " + response);
            lock(_stream){
                _formatter.Serialize(_stream, response);
                _stream.Flush();
            }
        }
    }
}