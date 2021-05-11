package it.polimi.ingsw.client.ClientModel.cards;

import it.polimi.ingsw.client.ClientModel.cards.LeaderCards.CardsCompositionMethods;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.enumerations.CardType;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.HashMap;
import java.util.List;

/**
 * The card is initially unflipped it means it's on its face at the begging, it can't be used, isFlipped=false;
 * when a card isFlipped it can be used.
 */
public abstract class LeaderCard extends Card {
    protected boolean isFlipped;
    protected CardType cardType;
    protected CardsCompositionMethods cardsCompositionMethods;


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


    public boolean verifyToActivate(Player player, HashMap<Resource,Integer> resourceIn){
        return false;
    }

    public boolean verifyToActivate(List<LeaderCardCost> cards){
        return cardsCompositionMethods.verifyToActivate(cards);
    }

    public abstract String toString();
}
