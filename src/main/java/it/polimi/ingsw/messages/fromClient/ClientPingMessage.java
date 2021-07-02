package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.server.handlers.ClientHandler;

import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Test class used to ping the server.
 */
public class ClientPingMessage extends ClientMessage{

    String playerHostAddress;
    private static final Logger logger = Logger.getLogger(ClientPingMessage.class.getName());

    public ClientPingMessage(){
        super(ClientMessageType.PING);
    }

    @Override
    public void serverProcess(ClientHandler clientHandler){
        logger.log(Level.INFO,"PING from client");
//        timer.schedule();
    }
}
