package it.polimi.ingsw.messages.fromServer.update;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.server.Server;

public class UpdateLeaderCardsMessage extends ServerMessage {

    private Integer index1;
    private Integer index2;

    public UpdateLeaderCardsMessage(Integer index1, Integer index2) {
        super(ServerMessageType.UPDATELEADERCARDS);
        this.index1 = index1;
        this.index2 = index2;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getLightModel().chooseLeaderCards(this.index1, this.index2);
        serverHandler.getView().showMessage("Your leader cards:");
        serverHandler.getView().showLeaderCards(serverHandler.getLightModel().getLeaderCards());
    }
}
