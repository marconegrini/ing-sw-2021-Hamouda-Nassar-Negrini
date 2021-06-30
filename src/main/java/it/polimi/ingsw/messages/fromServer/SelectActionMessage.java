package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.server.Server;

/**
 * Message sent to the client at the start of a turn. Invokes selectAction method in serverHandler's view and waits
 * for a user selection.
 */
public class SelectActionMessage extends ServerMessage{
    public SelectActionMessage() {
        super(ServerMessageType.SELECTACTION);
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        Runnable runnable = () -> {
            ClientMessage message = serverHandler.getView().selectAction("",false);
            serverHandler.sendJson(message);
        };

        Thread selectAction = new Thread (runnable);
        selectAction.start();
    }
}
