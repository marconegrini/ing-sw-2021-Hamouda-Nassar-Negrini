package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromClient.ClientPingMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server's ping message. Not currently used: just adopted initially to test client-server connection
 */
public class ServerPingMessage extends ServerMessage {

    private static final Logger logger = Logger.getLogger(ServerPingMessage.class.getName());

    public ServerPingMessage() {
        super(ServerMessageType.PING);
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        logger.log(Level.INFO,"PING from server");
        ClientMessage ping = new ClientPingMessage();
        try {
            synchronized (this) {
                this.wait(3000);
                serverHandler.sendJson(ping);
            }
        } catch (InterruptedException e) {}
    }
}

