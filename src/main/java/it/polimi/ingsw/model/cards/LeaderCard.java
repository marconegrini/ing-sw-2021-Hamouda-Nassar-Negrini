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
 * If the card is initially unflipped it means it's on its face at the begging, it can't be used, isFlipped=false;
 * When a card isFlipped it can be used.
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

    /**
     * Activates leader card
     * @throws AlreadyActivatedLeaderCardException if the leader card was already activated
     * @throws AlreadyDiscardedLeaderCardException if the leader card was discarded
     */
    public void activate() throws AlreadyActivatedLeaderCardException, AlreadyDiscardedLeaderCardException {
        if (!this.isActivated) {
            if (!this.isDiscarded) {
                this.isActivated = true;
            } else throw new AlreadyDiscardedLeaderCardException();
        } else throw new AlreadyActivatedLeaderCardException();
    }

    /**
     * Discards the leader card
     * @throws AlreadyActivatedLeaderCardException if the leader card was activated
     * @throws AlreadyDiscardedLeaderCardException if the leader card was already discarded
     */
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

    /**
     * @return the activation cost of the leader card
     * @throws WrongCardTypeException if the leader card is not a DISCOUNT, PRODUCTION or MARBLE leader card.
     */
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

    /**
     * @return the activation cost of the STORAGE leader card type, as an hashMap of <Resource, Integer>
     * @throws WrongCardTypeException if the card is not a STORAGE leader card type.
     */
    public HashMap<Resource, Integer> getStorageCardActivationCostResources() throws WrongCardTypeException {
        if (STORAGE.equals(this.getCardType())) {
            StorageLeaderCard storageLeaderCard = (StorageLeaderCard) this;
            return storageLeaderCard.getActivationCost();
        }
        throw new WrongCardTypeException();
    }

    /**
     * @return the hashMap corresponding to the leader card power
     */
    public abstract HashMap<Resource, Integer> getLeaderCardPower();

    public abstract String toString();

    /**
     * This method transform a card to a String that represent the image name in the resources directory. It will be used to upload the image of the card in the GUI
     * @return  a String that will be used for the path of the image
     */
    public abstract String toPath();
}
