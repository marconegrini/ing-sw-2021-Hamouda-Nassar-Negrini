package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromClient.ClientPingMessage;

/**
 * Server's ping message. Not currently used: just adopted initially to test client-server connection
 */
public class ServerPingMessage extends ServerMessage {

    public ServerPingMessage() {
        super(ServerMessageType.PING);
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        System.out.println("PING from server");
        ClientMessage ping = new ClientPingMessage();
        try {
            synchronized (this) {
                this.wait(3000);
                serverHandler.sendJson(ping);
            }
        } catch (InterruptedException e) {}
    }
}

