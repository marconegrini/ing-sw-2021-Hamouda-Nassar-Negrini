package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;

public class StartGameMessage extends ServerMessage{

    public StartGameMessage() {
        super(ServerMessageType.STARTGAME);
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getView().endWaitingRoom();
    }
}
