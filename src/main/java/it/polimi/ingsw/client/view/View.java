package it.polimi.ingsw.client.view;

import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.List;

/**
 * a super abstract class that is used to make it transparent to the client the using of the cli or the gui after the
 */
public abstract class View {

    public abstract ClientMessage logClient();

//    public abstract ClientMessage printToClient();

    public abstract ClientMessage initializeCalamaio(String strIn);

    public abstract ClientMessage selectLeaderCards(List<LeaderCard> leaderCards);

    public abstract ClientMessage selectAction();

    public abstract void showMessage(String message);

    public abstract void showLeaderCards(List<LeaderCard> leaderCards);

    public abstract void showComponent();
}

