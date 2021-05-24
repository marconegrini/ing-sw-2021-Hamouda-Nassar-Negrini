package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.server.Server;

public class UpdateLeaderCardsMessage extends ServerMessage{

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
        serverHandler.getView().showMessage("Leader cards correctly updated");
        serverHandler.getView().showLeaderCards(serverHandler.getLightModel().getLeaderCards());
    }
}
