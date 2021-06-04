package it.polimi.ingsw.messages.fromServer.update;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.server.Server;

/**
 * The message is sent just one time from server to client to update the client about
 * the selection of two leader cards  within the first four leader card distributed to each player by the server.
 * The clientProcess method will discard the other two leader cards in the lightModel leader card list
 * to keep only the selected ones. When the message is sent the server model has been already updated.
 */
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
        //serverHandler.getView().showMessage("Your leader cards:");
        serverHandler.getView().showLeaderCards(serverHandler.getLightModel().getLeaderCards());
    }
}
