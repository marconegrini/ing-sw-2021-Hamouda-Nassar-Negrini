package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.CardType;

import java.util.ArrayList;
import java.util.List;

/**
 * The card is initially unflipped it means it's on its face at the begging, it can't be used, isFlipped=false;
 * when a card isFlipped it can be used.
 */
public abstract class LeaderCard extends Card{
    protected boolean isFlipped;
    protected CardType cardType;

    //public abstract HashMap<Object, Integer> getActivationCost();

    public int discardAndReceiveVPointsIfNotActivated() {
        //TODO add a method that discards the card or manage the discard from the controller.
        this.isFlipped=false;
        return getVictoryPoints();
    }

    public boolean isFlipped() {return this.isFlipped;}

    public CardType getCardType() {
        return this.cardType;
    }

    public abstract boolean verifyToActivate(List<LeaderCardCost> cardsIn);

}
