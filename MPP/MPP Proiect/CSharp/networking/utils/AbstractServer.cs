using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Runtime.Remoting;
using log4net;

namespace networking.utils{
    public abstract class AbstractServer{
        private static readonly ILog Logger = LogManager.GetLogger("AbstractServer");
        private TcpListener _server;
        private readonly string _host;
        private readonly int _port;

        public AbstractServer(string host, int port){
            _host = host;
            _port = port;
        }

        public void Start(){
            IPAddress adr = IPAddress.Parse(_host);
            IPEndPoint ep = new IPEndPoint(adr, _port);
            try{
                _server = new TcpListener(ep);
                _server.Start();
                while(true){
                    Logger.Info("Waiting for clients ...");
                    TcpClient client = _server.AcceptTcpClient();
                    Logger.Info("Client connected ...");
                    ProcessRequest(client);
                }
            }
            catch(IOException e){
                Logger.Error("Error starting server", e);
                throw new ServerException("Starting server error ", e);
            }
        }

        public abstract void ProcessRequest(TcpClient client);

        public void Stop(){
            try{
                _server.Stop();
                Logger.Info("Server stopped");
            }
            catch(IOException e){
                Logger.Error("Error stopping server", e);
                throw new ServerException("Closing server error ", e);
            }
        }
    }
}