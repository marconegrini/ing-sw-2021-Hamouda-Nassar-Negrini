package it.polimi.ingsw.client.ClientModel.cards;
import it.polimi.ingsw.model.enumerations.LorenzoCardType;

public class LorenzoCard {

    private final LorenzoCardType cardType;


    public LorenzoCard(LorenzoCardType cardType) {
        this.cardType = cardType;
    }

    public LorenzoCardType getType(){return this.cardType;}
}

