package it.polimi.ingsw.client.view;

import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.List;

public class GUIView extends View{

    @Override
    public ClientMessage logClient(){
        return null;
    }

    @Override
    public ClientMessage initializeCalamaio(String strIn) {
        return null;
    }

//    @Override
//    public ClientMessage printToClient() {
//        return null;
//    }

    @Override
    public ClientMessage selectLeaderCards(List<LeaderCard> leaderCards){
        return null;
    }
}