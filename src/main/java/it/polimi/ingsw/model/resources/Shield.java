package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.model.enumerations.CardType;

public class Shield implements Resource{

    private CardType cardType;

    public Shield(){
        this.cardType = CardType.SHIELD;
    }

    @Override
    public CardType getResource(){
        return this.cardType;
    }
}
