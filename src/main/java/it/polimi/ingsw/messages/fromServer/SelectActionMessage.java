package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.server.Server;

public class SelectActionMessage extends ServerMessage{
    public SelectActionMessage() {
        super(ServerMessageType.SELECTACTION);
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getView().selectAction();
    }
}
