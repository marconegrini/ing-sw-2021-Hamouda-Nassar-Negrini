package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.cards.LeaderCards.CardsCompositionMethods;
import it.polimi.ingsw.server.model.enumerations.CardType;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.HashMap;
import java.util.List;

/**
 * The card is initially unflipped it means it's on its face at the begging, it can't be used, isFlipped=false;
 * when a card isFlipped it can be used.
 */
public abstract class LeaderCard extends Card{
    protected boolean isActivated;
    protected CardType cardType;

    public int discardAndReceiveVPointsIfNotActivated() {
        //TODO add a method that discards the card or manage the discard from the controller.
        this.isActivated=false;
        return getVictoryPoints();
    }

    public boolean isActivated() {return this.isActivated;}

    public CardType getCardType() {
        return this.cardType;
    }

    public abstract String toString();

}
