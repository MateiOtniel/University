import utils.AbstractServer;
import utils.ProtoConcurrentServer;
import utils.RpcConcurrentServer;
import utils.ServerException;
import persistence.repository.dbrepositories.ParticipantRepository;
import persistence.repository.dbrepositories.RefereeRepository;
import persistence.repository.dbrepositories.TestRepository;
import persistence.repository.interfaces.IParticipantRepository;
import persistence.repository.interfaces.IRefereeRepository;
import persistence.repository.interfaces.ITestRepository;
import server.ServicesImplementation;
import service.IService;

import java.io.IOException;
import java.util.Properties;

public class RpcServer{

    public static void main(String[] args){

        Properties serverProps = new Properties();
        try{
            serverProps.load(RpcServer.class
                    .getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        }catch(IOException e){
            System.err.println("Cannot find server.properties " + e);
            return;
        }

        IParticipantRepository playerRepo = new ParticipantRepository(serverProps);
        //IRefereeRepository refereeRepo = new RefereeRepository(serverProps);
        IRefereeRepository refereeRepo = new RefereeRepository();
        ITestRepository testRepo = new TestRepository(serverProps);
        IService serviceImplementation =
                new ServicesImplementation(playerRepo, refereeRepo, testRepo);


        int defaultPort = 55555;
        int serverPort = defaultPort;
        try{
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        }catch(NumberFormatException nef){
            System.err.println("Wrong  Port Number" + nef.getMessage());
            System.err.println("Using default port " + defaultPort);
        }

        System.out.println("Starting server on port: " + serverPort);
        AbstractServer server = new ProtoConcurrentServer(serverPort, serviceImplementation);
        try{
            server.start();
        }catch(ServerException e){
            System.err.println("Error starting the server" + e.getMessage());
        }finally{
            try{
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server " + e.getMessage());
            }
        }
    }
}
