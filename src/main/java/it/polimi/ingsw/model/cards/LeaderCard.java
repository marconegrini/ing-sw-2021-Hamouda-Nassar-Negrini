package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.cards.LeaderCards.DiscountLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.ProdPowerLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.StorageLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.WhiteMarbleLeaderCard;
import it.polimi.ingsw.model.enumerations.CardType;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.AlreadyActivatedLeaderCardException;
import it.polimi.ingsw.model.exceptions.AlreadyDiscardedLeaderCardException;
import it.polimi.ingsw.model.exceptions.EmptySlotException;
import it.polimi.ingsw.model.exceptions.WrongCardTypeException;

import java.util.HashMap;
import java.util.List;

import static it.polimi.ingsw.model.enumerations.CardType.*;

/**
 * The card is initially unflipped it means it's on its face at the begging, it can't be used, isFlipped=false;
 * when a card isFlipped it can be used.
 */
public abstract class LeaderCard extends Card {
    protected boolean isActivated;
    protected boolean isDiscarded;
    protected CardType cardType;

    public boolean isActivated() {
        return this.isActivated;
    }

    public boolean isDiscarded() {
        return this.isDiscarded;
    }

    public void activate() throws AlreadyActivatedLeaderCardException, AlreadyDiscardedLeaderCardException {
        if (!this.isActivated) {
            if (!this.isDiscarded) {
                this.isActivated = true;
            } else throw new AlreadyDiscardedLeaderCardException();
        } else throw new AlreadyActivatedLeaderCardException();
    }

    public void discard() throws AlreadyActivatedLeaderCardException, AlreadyDiscardedLeaderCardException {
        if (!this.isActivated) {
            if (!this.isDiscarded) {
                this.isDiscarded = true;
            } else throw new AlreadyDiscardedLeaderCardException();
        } else throw new AlreadyActivatedLeaderCardException();
    }

    public CardType getCardType() {
        return this.cardType;
    }


    /* method that makes the cast based on the type(between: DISCOUNT, PRODUCTION, MARBLE) of the card and returns its cost */
    public List<LeaderCardCost> getCardActivationCostColours() throws WrongCardTypeException {
        if ((List.of(DISCOUNT, PRODUCTION, MARBLE).contains(this.getCardType()))) {
            if (DISCOUNT.equals(this.getCardType())) {
                DiscountLeaderCard discountLeaderCard = (DiscountLeaderCard) this;
                return discountLeaderCard.getActivationCost();
            } else if (PRODUCTION.equals(this.getCardType())) {
                ProdPowerLeaderCard prodPowerLeaderCard = (ProdPowerLeaderCard) this;
                return prodPowerLeaderCard.getActivationCost();
            } else if (MARBLE.equals(this.getCardType())) {
                WhiteMarbleLeaderCard whiteMarbleLeaderCard = (WhiteMarbleLeaderCard) this;
                return whiteMarbleLeaderCard.getActivationCost();
            }
        }
        throw new WrongCardTypeException();
    }

    /* method that makes the cast based on the type( STORAGE ) of the card and returns its cost */
    public HashMap<Resource, Integer> getStorageCardActivationCostResources() throws WrongCardTypeException {
        if (STORAGE.equals(this.getCardType())) {
            StorageLeaderCard storageLeaderCard = (StorageLeaderCard) this;
            return storageLeaderCard.getActivationCost();
        }
        throw new WrongCardTypeException();
    }


    public abstract HashMap<Resource, Integer> getLeaderCardPower();

    public abstract String toString();

}
