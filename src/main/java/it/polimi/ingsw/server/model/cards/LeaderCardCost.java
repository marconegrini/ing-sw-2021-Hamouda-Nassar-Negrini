package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.cards.LeaderCards.CardsCompositionMethods;
import it.polimi.ingsw.server.model.enumerations.CardColor;

import it.polimi.ingsw.server.model.enumerations.Level;

public class LeaderCardCost {
private final CardColor color;
private final Level level;

private CardsCompositionMethods cardMethodClass;

public LeaderCardCost(CardColor color, Level level){
    this.color=color;
    this.level=level;
}

    public CardColor getColor() {
        return color;
    }

    public Level getLevel() {
        return level;
    }

//    public HashMap<CardColor,Integer> getOccurrenceNumber() {
//        cardMethodClass.verifyToActivate(this);
//        return occurrenceNumber;
//    }

}

