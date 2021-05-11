package it.polimi.ingsw.server.model.cards;
import it.polimi.ingsw.server.model.enumerations.LorenzoCardType;

public class LorenzoCard {

    private final LorenzoCardType cardType;


    public LorenzoCard(LorenzoCardType cardType) {
        this.cardType = cardType;
    }

    public LorenzoCardType getType(){return this.cardType;}
}
