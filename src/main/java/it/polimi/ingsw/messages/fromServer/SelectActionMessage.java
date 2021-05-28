package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.server.Server;

public class SelectActionMessage extends ServerMessage{
    public SelectActionMessage() {
        super(ServerMessageType.SELECTACTION);
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        Runnable runnable = () -> {
            ClientMessage message = serverHandler.getView().selectAction();
            serverHandler.sendJson(message);
        };

        Thread selectAction = new Thread (runnable);
        selectAction.start();


        //ClientMessage message = serverHandler.getView().selectAction();
        //serverHandler.sendJson(message);
    }
}
