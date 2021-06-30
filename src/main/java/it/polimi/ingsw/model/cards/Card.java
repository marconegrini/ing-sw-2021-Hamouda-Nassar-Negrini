package it.polimi.ingsw.model.cards;

/**
 * Main abstract class inherited by cards classes.
 */
public abstract class Card {
    protected int Vp;
    //setters

    //getters
    public int getVictoryPoints(){return this.Vp;};
}
