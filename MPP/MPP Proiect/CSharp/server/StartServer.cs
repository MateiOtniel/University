using System;
using System.Collections.Generic;
using System.Configuration;
using log4net;
using networking.utils;
using persistance.DBRepositories;
using persistance.Interfaces;
using services;

namespace server{
    internal class StartServer{
        private static readonly ILog Log = LogManager.GetLogger("Start Server");
        
        static void Main(string[] args){
            IDictionary<string, string> props = new SortedList<string, string>();
            props.Add("ConnectionString", GetConnectionStringByName("triathlonDB"));

            IParticipantRepository participantRepository =
                new ParticipantRepository(props);
            IRefereeRepository refereeRepository =
                new RefereeRepository(props);
            ITestRepository testRepository =
                new TestRepository(props);
            IServices serviceImpl =
                new ServerImpl(participantRepository, refereeRepository,
                    testRepository);

            //AbstractServer server = new RpcConcurrentServer("127.0.0.1", 55556, serviceImpl);
            AbstractServer server = new ProtobufConcurrentServer("127.0.0.1", 55556, serviceImpl);
            server.Start();
            Log.Info("Server started ...");
            Console.WriteLine("Server started ...");
        }

        private static string GetConnectionStringByName(string name){
            string returnValue = null;
            var settings = ConfigurationManager.ConnectionStrings[name];

            if(settings != null){
                returnValue = settings.ConnectionString;
            }

            return returnValue;
        }
    }
}