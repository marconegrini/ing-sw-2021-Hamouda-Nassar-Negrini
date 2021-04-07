package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.model.enumerations.CardType;

public class Stone implements Resource{

    private CardType cardType;

    public Stone(){
        this.cardType = CardType.STONE;
    }

    @Override
    public CardType getResource(){
        return this.cardType;
    }

}
