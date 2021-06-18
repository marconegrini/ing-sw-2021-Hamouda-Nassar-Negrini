package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;

/**
 * This class is used to exemplify the cost of a development card, especially useful when checking the
 * activation cost of a leader card that needs development cards in order to be activated.
 */
public class LeaderCardCost {

    private final CardColor color;
    private final Level level;

    /**
     * @param color color of the development card
     * @param level level of the development card
     */
    public LeaderCardCost(CardColor color, Level level) {
        this.color = color;
        this.level = level;
    }

    public CardColor getColor() {
        return color;
    }

    public Level getLevel() {
        return level;
    }
}

