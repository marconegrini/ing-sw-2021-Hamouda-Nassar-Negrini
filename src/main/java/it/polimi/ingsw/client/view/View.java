package it.polimi.ingsw.client.view;

import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.List;

public abstract class View {

    public abstract ClientMessage logClient();

    public abstract ClientMessage selectLeaderCards(List<LeaderCard> leaderCards);

    public abstract void showMessage(String message);

    public abstract void showLeaderCards(List<LeaderCard> leaderCards);

}
