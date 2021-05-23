package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;

public class ChooseLeaderCardMessage extends ServerMessage{

    public ChooseLeaderCardMessage() {
        super(ServerMessageType.CHOOSELEADERCARDS);
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {

    }
}
