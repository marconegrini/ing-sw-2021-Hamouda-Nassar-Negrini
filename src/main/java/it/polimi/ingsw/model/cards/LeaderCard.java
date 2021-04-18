package it.polimi.ingsw.model.cards;

import java.util.HashMap;


public abstract class LeaderCard extends Card{
    protected boolean isFlipped;


    //public abstract HashMap<Object, Integer> getActivationCost();
    public abstract int discardAndReceiveVPointsIfNotActivated();
    public boolean isFlippes(){return this.isFlipped;}
}
