package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.model.enumerations.CardType;

public class Coin implements Resource{

    private CardType cardType;

    public Coin(){
        this.cardType = CardType.COIN;
    }

    @Override
    public CardType getResource(){
        return this.cardType;
    }
}
