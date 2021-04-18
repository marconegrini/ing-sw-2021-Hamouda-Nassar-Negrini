package it.polimi.ingsw.model.cards;

import java.util.HashMap;


public abstract class LeaderCard extends Card{
    protected boolean isFlipped;


    //public abstract HashMap<Object, Integer> getActivationCost();

    public int discardAndReceiveVPointsIfNotActivated() {
        //TODO add a method that discards the card or manage the discard from the controller.
        this.isFlipped=false;
        return getVictoryPoints();
    }

    public boolean isFlippes(){return this.isFlipped;}

}
