package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.enumerations.LorenzoCardsTypes;

public class LorenzoCard {

    private final LorenzoCardsTypes cardType;


    public LorenzoCard(LorenzoCardsTypes cardType) {
        this.cardType = cardType;
    }

    public LorenzoCardsTypes getType(){return this.cardType;}
}

