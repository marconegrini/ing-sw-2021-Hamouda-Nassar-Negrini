package it.polimi.ingsw.model.cards;

import java.util.HashMap;


public abstract class LeaderCard extends Card{
    private boolean isFlipped;
    private HashMap<Object, Integer> activationCost;

    public abstract HashMap<Object, Integer> getActivationCost();
    public abstract void use();
    public abstract int discardAndReceiveVPointsIfNotActivated();
}
