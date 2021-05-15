package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.cards.LeaderCards.CardsCompositionMethods;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;

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

