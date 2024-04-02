package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import protobuffprotocol.ProtoWorker;
import rpcprotocol.ClientRpcReflectionWorker;
import service.IService;

import java.net.Socket;

public class ProtoConcurrentServer extends AbsConcurrentServer{
    private final IService server;
    private static final Logger logger = LogManager.getLogger();

    public ProtoConcurrentServer(int port, IService server){
        super(port);
        this.server = server;
        logger.info("RpcConcurrentServer created");
    }

    @Override
    protected Thread createWorker(Socket client){
        ProtoWorker worker = new ProtoWorker(server, client);
        Thread tw = new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        logger.info("Stopping services ...");
    }
}
