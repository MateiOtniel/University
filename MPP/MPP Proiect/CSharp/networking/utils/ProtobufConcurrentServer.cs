using System;
using System.Net.Sockets;
using System.Threading;
using log4net;
using protobuf;
using services;

namespace networking.utils{
    public class ProtobufConcurrentServer: AbsConcurrentServer{
        private readonly IServices service;

        private static readonly ILog logger =
            LogManager.GetLogger("RpcConcurrentServer");

        private ProtoWorker worker;

        public ProtobufConcurrentServer(string host, int port, IServices service):
            base(host, port){
            this.service = service;
            logger.Info("ProtobuffConcurrentServer created");
            Console.WriteLine(@"ProtobuffConcurrentServer created");
        }

        protected override Thread CreateWorker(TcpClient client){
            worker = new ProtoWorker(service, client);
            return new Thread(new ThreadStart(worker.Run));
        }
    }
}