package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.List;

public class ChooseLeaderCardMessage extends ServerMessage{

    List<LeaderCard> leaderCards;

    public ChooseLeaderCardMessage(List<LeaderCard> leaderCards) {
        super(ServerMessageType.CHOOSELEADERCARDS);
        this.leaderCards = leaderCards;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {

    }
}
