using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using log4net;
using model;
using services;

namespace networking{
    public class ServicesRpcProxy: IServices{
        private static readonly ILog Logger = LogManager.GetLogger("ServicesRpcProxy");
        private readonly string _host;
        private readonly int _port;
        private IObserver _refereeObserver;
        private NetworkStream _stream;
        private IFormatter _formatter;
        private TcpClient _connection;
        private readonly Queue<Response> _responses;
        private volatile bool _finished;
        private EventWaitHandle _waitHandle;

        public ServicesRpcProxy(string host, int port){
            _host = host;
            _port = port;
            _responses = new Queue<Response>();
        }

        private void CloseConnection(){
            _finished = true;
            try{
                _stream.Close();
                _connection.Close();
                _waitHandle.Close();
                _refereeObserver = null;
            } catch(Exception e){
                Console.WriteLine(e.StackTrace);
            }
        }

        private void SendRequest(Request request){
            try{
                _formatter.Serialize(_stream, request);
                _stream.Flush();
            } catch(Exception e){
                throw new MyException("Error sending object " + e);
            }
        }

        private Response ReadResponse(){
            Response response = null;
            try{
                _waitHandle.WaitOne();
                lock(_responses){
                    response = _responses.Dequeue();
                }
            } catch(Exception e){
                Console.WriteLine(e.StackTrace);
            }

            return response;
        }

        private void InitializeConnection(){
            try{
                _connection = new TcpClient(_host, _port);
                _stream = _connection.GetStream();
                _formatter = new BinaryFormatter();
                _finished = false;
                _waitHandle = new AutoResetEvent(false);
                StartReader();
            } catch(Exception e){
                Console.WriteLine(e.StackTrace);
            }
        }

        private void StartReader(){
            var tw = new Thread(Run);
            tw.Start();
        }

        protected virtual void Run(){
            while(!_finished){
                try{
                    var response = _formatter.Deserialize(_stream);
                    Console.WriteLine("response received " + response);
                    if(IsUpdate((Response)response)){
                        HandleUpdate((Response)response);
                    } else{
                        lock(_responses){
                            _responses.Enqueue((Response)response);
                        }

                        _waitHandle.Set();
                    }
                } catch(Exception e){
                    Console.WriteLine("Reading error " + e);
                }
            }
        }

        private void HandleUpdate(Response response){
            if(response.Type != ResponseType.TEST_ADDED) return;
            Logger.Info("Test added ");
            try{
                _refereeObserver.RefreshAll(response.Data as Test);
            } catch(MyException e){
                Logger.Error("Error handle update: " + e);
            }
        }

        private static bool IsUpdate(Response response){
            return response.Type == ResponseType.TEST_ADDED;
        }

        public Referee Login(Referee referee, IObserver client){
            InitializeConnection();
            var request = new Request.Builder().Type(RequestType.LOGIN)
                .Data(referee).Build();
            SendRequest(request);
            var response = ReadResponse();
            switch(response.Type){
                case ResponseType.OK:
                    _refereeObserver = client;
                    Logger.Info("Login successful");
                    return (Referee)response.Data;
                case ResponseType.ERROR:
                    Logger.Info("Login failed" + response.Data);
                    CloseConnection();
                    throw new MyException((string)response.Data);
                default:
                    return null;
            }
        }

        public void AddTest(Test test){
            var request = new Request.Builder().Type(RequestType.ADD_TEST)
                .Data(test).Build();
            SendRequest(request);
            var response = ReadResponse();
            if(response.Type == ResponseType.ERROR){
                Logger.Info("Add test failed" + response.Data);
                throw new MyException((string)response.Data);
            }

            Console.WriteLine("Add test successful");
            Logger.Info("Add test successful");
        }

        public IEnumerable<Participant> GetAllParticipantsSortedByName(){
            var request = new Request.Builder().Type(RequestType.GET_ALL_PARTICIPANTS)
                .Build();
            SendRequest(request);
            var response = ReadResponse();
            if(response.Type == ResponseType.ERROR){
                Logger.Info("Get all participants failed" + response.Data);
                throw new MyException((string)response.Data);
            }

            Logger.Info("Get all participants successful");
            return (IEnumerable<Participant>)response.Data;
        }

        public IEnumerable<Participant> GetAllActiveParticipants(int refereeId){
            var request = new Request.Builder()
                .Type(RequestType.GET_ALL_ACTIVE_PARTICIPANTS)
                .Data(refereeId).Build();
            SendRequest(request);
            var response = ReadResponse();
            if(response.Type == ResponseType.ERROR){
                Logger.Info("Get all active participants failed" + response.Data);
                throw new MyException((string)response.Data);
            }

            Logger.Info("Get all active participants successful");
            return (IEnumerable<Participant>)response.Data;
        }

        public void Logout(Referee referee){
            var request = new Request.Builder().Type(RequestType.LOGOUT)
                .Data(referee).Build();
            SendRequest(request);
            var response = ReadResponse();
            CloseConnection();
            if(response.Type == ResponseType.ERROR){
                Logger.Info("Logout failed" + response.Data);
                throw new MyException((string)response.Data);
            }

            Logger.Info("Logged out");
        }
    }
}