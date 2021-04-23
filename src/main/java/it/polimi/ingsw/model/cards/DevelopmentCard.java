package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.enumerations.CardColor;

import java.util.HashMap;

public class DevelopmentCard extends Card{

    private final CardColor color;
    private final Level level;
    private final HashMap<Resource, Integer> cardCost;
    private final HashMap<Resource, Integer> productionIn;
    private final HashMap<Resource, Integer> productionOut;

    public DevelopmentCard(int Vp, CardColor cardColor, Level level,
                           HashMap<Resource, Integer> cardCost,
                           HashMap<Resource, Integer> productionIn,
                           HashMap<Resource, Integer> productionOut){
        this.Vp = Vp;
        this.color = cardColor;
        this.level = level;
        this.cardCost = cardCost;
        this.productionIn = productionIn;
        this.productionOut = productionOut;
    }

    @Override
    public int getVictoryPoints(){
        return this.Vp;
    }

    public CardColor getColor() {
        return color;
    }

    public Level getLevel() {
        return level;
    }

    public HashMap<Resource, Integer> getCardCost() {
        return cardCost;
    }

    public HashMap<Resource, Integer> getProductionIn() {
        return productionIn;
    }

    public HashMap<Resource, Integer> getProductionOut() {
        return productionOut;
    }
}
