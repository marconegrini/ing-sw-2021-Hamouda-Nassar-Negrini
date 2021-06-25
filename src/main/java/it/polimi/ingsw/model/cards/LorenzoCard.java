package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.enumerations.LorenzoCardType;

/**
 * Lorenzo's cards are the six action cards used during the single player game
 */
public class LorenzoCard {

    private final LorenzoCardType cardType;

    public LorenzoCard(LorenzoCardType cardType) {
        this.cardType = cardType;
    }

    public LorenzoCardType getType(){return this.cardType;}
}

