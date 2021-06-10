package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.List;

/**
 * Message sent from server after the client choose two leader cards out of four. The method updates client's
 * light model and prints selected leader cards to the client
 */
public class ChooseLeaderCardMessage extends ServerMessage{

    List<LeaderCard> leaderCards;

    public ChooseLeaderCardMessage(List<LeaderCard> leaderCards) {
        super(ServerMessageType.CHOOSELEADERCARDS);
        this.leaderCards = leaderCards;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getLightModel().setLeaderCards(this.leaderCards);
        ClientMessage message = serverHandler.getView().selectLeaderCards(this.leaderCards);
        serverHandler.sendJson(message);
    }
}
