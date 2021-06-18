package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.server.Server;

/**
 * A generic Server Message to show a positive message to the client
 */
public class OkMessage extends ServerMessage{

    private String message;

    public OkMessage(String message) {
        super(ServerMessageType.OK);
        this.message = message;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getView().showMessage(message);
    }
}
