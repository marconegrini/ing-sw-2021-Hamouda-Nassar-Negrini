package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.List;

public class ChooseLeaderCardMessage extends ServerMessage{

    List<LeaderCard> leaderCards;
    public ChooseLeaderCardMessage() {
        super(ServerMessageType.CHOOSELEADERCARDS);

    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {

    }
}
