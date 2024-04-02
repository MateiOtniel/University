using System;
using System.Net.Sockets;
using System.Threading;
using log4net;
using services;

namespace networking.utils{
    public class RpcConcurrentServer : AbsConcurrentServer
    {
        private readonly IServices _service;
        private static readonly ILog logger = LogManager.GetLogger("RpcConcurrentServer");
      
        private ClientRpcReflectionWorker worker;
        public RpcConcurrentServer(string host, int port, IServices service) : base(host, port)
        {
            _service = service; 
            logger.Info("RpcConcurrentServer created");
            Console.WriteLine(@"RpcConcurrentServer created");
        }
        protected override Thread CreateWorker(TcpClient client)
        {
            worker = new ClientRpcReflectionWorker(_service, client);
            return new Thread(new ThreadStart(worker.Run));
        }

    }
}