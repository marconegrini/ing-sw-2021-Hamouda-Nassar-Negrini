package it.polimi.ingsw.client.ClientModel.cards;

import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;

public class DevelopmentCard extends Card {

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
        return (HashMap<Resource, Integer>) cardCost.clone();
    }

    public HashMap<Resource, Integer> getProductionIn() {
        return (HashMap<Resource, Integer>) productionIn.clone();
    }

    public HashMap<Resource, Integer> getProductionOut() {
        return (HashMap<Resource, Integer>) productionOut.clone();
    }
}
