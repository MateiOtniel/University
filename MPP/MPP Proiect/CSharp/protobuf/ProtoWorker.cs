using System;
using System.Net.Sockets;
using System.Threading;
using Google.Protobuf;
using Java;
using services;

namespace protobuf{
    public class ProtoWorker: IObserver{
        private IServices server;
        private TcpClient connection;

        private NetworkStream stream;
        private volatile bool connected;

        public ProtoWorker(IServices server, TcpClient connection){
            this.server = server;
            this.connection = connection;
            try{
                stream = connection.GetStream();
                connected = true;
            } catch(Exception e){
                Console.WriteLine(e.StackTrace);
            }
        }

        public virtual void Run(){
            while(connected){
                try{
                    Request request = Request.Parser.ParseDelimitedFrom(stream);
                    Response response = handleRequest(request);
                    if(response != null){
                        sendResponse(response);
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
                stream.Close();
                connection.Close();
            } catch(Exception e){
                Console.WriteLine("Error " + e);
            }
        }

        private Response handleRequest(Request request){
            Response response = null;
            Request.Types.Type reqType = request.Type;
            switch(reqType){
                case Request.Types.Type.Login:{
                    Console.WriteLine("Login request ...");
                    model.Referee referee = ProtoUtils.GetReferee(request);
                    try{
                        lock(server){
                            return ProtoUtils.CreateLoginResponse(
                                server.Login(referee, this));
                        }
                    } catch(MyException e){
                        connected = false;
                        return ProtoUtils.CreateErrorResponse(e.Message);
                    }
                }
                case Request.Types.Type.Logout:{
                    Console.WriteLine("Logout request");
                    model.Referee referee = ProtoUtils.GetReferee(request);
                    try{
                        lock(server){
                            server.Logout(referee);
                        }

                        connected = false;
                        return ProtoUtils.CreateOkResponse();
                    } catch(MyException e){
                        return ProtoUtils.CreateErrorResponse(e.Message);
                    }
                }
                case Request.Types.Type.AddTest:{
                    Console.WriteLine("Add test request");
                    model.Test test = ProtoUtils.GetTest(request);
                    try{
                        lock(server){
                            server.AddTest(test);
                        }

                        return ProtoUtils.CreateOkResponse();
                    } catch(MyException e){
                        return ProtoUtils.CreateErrorResponse(e.Message);
                    }
                }
                case Request.Types.Type.GetAllParticipants:{
                    Console.WriteLine("Get all participants request");
                    try{
                        lock(server){
                            return ProtoUtils.CreateGetAllParticipantsResponse(
                                server.GetAllParticipantsSortedByName());
                        }
                    } catch(MyException e){
                        return ProtoUtils.CreateErrorResponse(e.Message);
                    }
                }
                case Request.Types.Type.GetAllActiveParticipants:{
                    Console.WriteLine("Get all active participants request");
                    try{
                        var id = ProtoUtils.GetId(request);
                        lock(server){
                            return ProtoUtils.CreateGetAllActiveParticipantsResponse(
                                server.GetAllActiveParticipants(id));
                        }
                    } catch(MyException e){
                        return ProtoUtils.CreateErrorResponse(e.Message);
                    }
                }
            }

            return response;
        }

        private void sendResponse(Response response){
            Console.WriteLine("sending response " + response);
            lock(stream){
                response.WriteDelimitedTo(stream);
                stream.Flush();
            }
        }

        public void RefreshAll(model.Test test){
            Console.WriteLine("Test added " + test);
            try{
                sendResponse(ProtoUtils.CreateTestAddedResponse(test));
            } catch(Exception e){
                Console.WriteLine(e.StackTrace);
            }
        }
    }
}