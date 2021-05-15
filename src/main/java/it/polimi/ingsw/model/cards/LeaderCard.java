package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.enumerations.CardType;
import it.polimi.ingsw.model.exceptions.AlreadyActivatedLeaderCardException;
import it.polimi.ingsw.model.exceptions.AlreadyDiscardedLeaderCardException;

/**
 * The card is initially unflipped it means it's on its face at the begging, it can't be used, isFlipped=false;
 * when a card isFlipped it can be used.
 */
public abstract class LeaderCard extends Card{
    protected boolean isActivated;
    protected boolean isDiscarded;
    protected CardType cardType;

    public boolean isActivated() {return this.isActivated;}

    public boolean isDiscarded() {return this.isDiscarded;}

    public void activate() throws AlreadyActivatedLeaderCardException, AlreadyDiscardedLeaderCardException {
        if(!this.isActivated) {
            if(!this.isDiscarded) {
                this.isActivated = true;
            } else throw new AlreadyDiscardedLeaderCardException();
        }else throw new AlreadyActivatedLeaderCardException();
    }

    public void discard() throws AlreadyActivatedLeaderCardException, AlreadyDiscardedLeaderCardException{
        if(!this.isActivated){
            if(!this.isDiscarded){
                this.isDiscarded = true;
            } else throw new AlreadyDiscardedLeaderCardException();
        } else throw new AlreadyActivatedLeaderCardException();
    }

    public CardType getCardType() {
        return this.cardType;
    }

    public abstract String toString();

}
