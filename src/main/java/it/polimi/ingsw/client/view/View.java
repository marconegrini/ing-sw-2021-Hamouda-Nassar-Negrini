package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Super abstract class that is used to make it transparent to the client the using of cli or gui. Dynamically instantiated
 * as ClIView or GUIView in serverHandler, it will be called inside messages' clientProcess methods
 */
public abstract class   View {

    public abstract ClientMessage logClient();

    public abstract ClientMessage initializeCalamaio(String strIn);

    public abstract ClientMessage calamaioErrHandelr(String strIn);

    public abstract ClientMessage selectLeaderCards(List<LeaderCard> leaderCards);

    public abstract ClientMessage selectAction(String choice,boolean err);

    public abstract ClientMessage storeResources(List<Resource> resources);

    public abstract ClientMessage buyDVCard(ArrayList<DevelopmentCard> devCards, boolean err);

    public abstract ClientMessage waitingRoom();

    public abstract void endWaitingRoom();

    public abstract void startGame();

    public abstract void showMessage(String message);

    public abstract void showLeaderCards(List<LeaderCard> leaderCards);

    public abstract void showResources(List<Resource> resources);

    public abstract ClientMessage activateProduction();

    public abstract void showParticipantsNumber(String s);
}

