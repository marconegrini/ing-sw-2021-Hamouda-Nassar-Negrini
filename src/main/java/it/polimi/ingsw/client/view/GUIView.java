package it.polimi.ingsw.client.view;

import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Resource;

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

    @Override
    public ClientMessage selectAction() {
        return null;
    }

    @Override
    public ClientMessage storeResources(List<Resource> resources) {
        return null;
    }

    @Override
    public ClientMessage waitingRoom() {
        return null;
    }

    @Override
    public void endWaitingRoom() {

    }

    @Override
    public void showMessage(String message) {}

    @Override
    public void showLeaderCards(List<LeaderCard> leaderCards) {}

    @Override
    public void showResources(List<Resource> resources) {}
}
