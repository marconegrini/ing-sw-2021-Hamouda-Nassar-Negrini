package it.polimi.ingsw.model.cards;

import java.util.HashMap;

/**
 * The card is initially unflipped it means it's on its face at the begging, it can't be used, isFlipped=false;
 * when a card isFlipped it can be used.
 */
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
