using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using Google.Protobuf;
using Java;
using services;

namespace protobuf{
    public class ProtoProxy: IServices{
        private readonly string host;
        private readonly int port;
        private IObserver refereeObserver;
        private NetworkStream stream;
        private IFormatter formatter;
        private TcpClient connection;
        private readonly Queue<Response> responses;
        private volatile bool finished;
        private EventWaitHandle waitHandle;

        public ProtoProxy(string host, int port){
            this.host = host;
            this.port = port;
            responses = new Queue<Response>();
        }

        private void CloseConnection(){
            finished = true;
            try{
                stream.Close();
                connection.Close();
                waitHandle.Close();
                refereeObserver = null;
                Console.WriteLine("Connection closed!");
            } catch(Exception e){
                Console.WriteLine(e.StackTrace);
            }
        }

        private void SendRequest(Request request){
            try{
                request.WriteDelimitedTo(stream);
                stream.Flush();
            } catch(Exception e){
                throw new MyException("Error sending object " + e);
            }
        }

        private Response ReadResponse(){
            Response response = null;
            try{
                waitHandle.WaitOne();
                lock(responses){
                    response = responses.Dequeue();
                }
            } catch(Exception e){
                Console.WriteLine(e.StackTrace);
            }

            return response;
        }

        private void InitializeConnection(){
            try{
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                finished = false;
                waitHandle = new AutoResetEvent(false);
                StartReader();
            } catch(Exception e){
                Console.WriteLine(e.StackTrace);
            }
        }

        private void StartReader(){
            var tw = new Thread(Run);
            tw.Start();
        }

        private void HandleUpdate(Response response){
            if(response.Type != Response.Types.Type.TestAdded) return;
            var test = ProtoUtils.GetTest(response);
            Console.WriteLine("Test added " + test);
            try{
                refereeObserver.RefreshAll(test);
            } catch(MyException e){
                Console.WriteLine("Error handle update: " + e);
            }
        }

        private static bool IsUpdate(Response response){
            return response.Type == Response.Types.Type.TestAdded;
        }

        protected virtual void Run(){
            while(!finished){
                try{
                    var response = Response.Parser.ParseDelimitedFrom(stream);
                    Console.WriteLine("response received " + response);
                    if(IsUpdate((Response)response)){
                        HandleUpdate((Response)response);
                    } else{
                        lock(responses){
                            responses.Enqueue((Response)response);
                        }

                        waitHandle.Set();
                    }
                } catch(Exception e){
                    Console.WriteLine("Reading error " + e);
                }
            }
        }

        public model.Referee Login(model.Referee referee, IObserver client){
            InitializeConnection();
            Console.WriteLine("Login request ...");
            var req = ProtoUtils.CreateLoginRequest(referee);
            SendRequest(req);
            var response = ReadResponse();
            switch(response.Type){
                case Response.Types.Type.Ok:
                    refereeObserver = client;
                    Console.WriteLine("Logged in");
                    return ProtoUtils.GetReferee(response);
                case Response.Types.Type.Error:{
                    var err = ProtoUtils.GetError(response);
                    Console.WriteLine("Error logging in" + err);
                    CloseConnection();
                    throw new MyException(err);
                }
                default:
                    return null;
            }
        }

        public IEnumerable<model.Participant> GetAllParticipantsSortedByName(){
            var req = ProtoUtils.CreateAllParticipantsRequest();
            SendRequest(req);
            var response = ReadResponse();
            if (response.Type == Response.Types.Type.Error)
            {
                var err = ProtoUtils.GetError(response);
                Console.WriteLine("Error getting shows" + err);
                throw new MyException(err);
            }
            Console.WriteLine("Got shows");
            return ProtoUtils.GetParticipants(response);
        }

        public void AddTest(model.Test test){
            var req = ProtoUtils.CreateAddTestRequest(test);
            SendRequest(req);
            var response = ReadResponse();
            if (response.Type == Response.Types.Type.Error)
            {
                var err = ProtoUtils.GetError(response);
                Console.WriteLine("Error adding test" + err);
                throw new MyException(err);
            }
            Console.WriteLine("Test added");
        }

        public IEnumerable<model.Participant> GetAllActiveParticipants(int refereeId){
            var req = ProtoUtils.CreateAllActiveParticipantsRequest(refereeId);
            SendRequest(req);
            var response = ReadResponse();
            if (response.Type == Response.Types.Type.Error)
            {
                var err = ProtoUtils.GetError(response);
                Console.WriteLine("Error getting active participants" + err);
                throw new MyException(err);
            }
            Console.WriteLine("Got active participants");
            return ProtoUtils.GetParticipants(response);
        }

        public void Logout(model.Referee referee){
            var req = ProtoUtils.CreateLogoutRequest(referee);
            SendRequest(req);
            var response = ReadResponse();
            CloseConnection();
            if (response.Type == Response.Types.Type.Error)
            {
                var err = ProtoUtils.GetError(response);
                Console.WriteLine("Error logging out" + err);
                throw new MyException(err);
            }
            Console.WriteLine("Logged out");
        }
    }
}