package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.model.enumerations.CardType;

public class Servant implements Resource{

    private CardType cardType;

    public Servant(){
        this.cardType = CardType.SERVANT;
    }

    @Override
    public CardType getResource(){
        return this.cardType;
    }
}
