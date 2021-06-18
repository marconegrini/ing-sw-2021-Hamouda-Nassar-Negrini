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

    /**
     * @param Vp            victory points given
     * @param cardColor
     * @param level
     * @param cardCost      development card cost specified as HashMap
     * @param productionIn  resources needed to activate production on the development card
     * @param productionOut resources given as production output by the development card
     */
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

    /**
     * @return victory points given by the development card
     */
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

    @Override
    public String toString() {
        return "DevelopmentCard{" +
                "color=" + color +
                ", level=" + level +
                ", cardCost=" + cardCost +
                ", productionIn=" + productionIn +
                ", productionOut=" + productionOut +
                '}';
    }

    /**
     * Ad hoc clone method to copy a development card
     * @return
     */
    public DevelopmentCard clone(){
        HashMap<Resource, Integer> cost = this.getCardCost();
        HashMap<Resource, Integer> prodIn = this.getProductionIn();
        HashMap<Resource, Integer> prodOut = this.getProductionOut();
        DevelopmentCard dv = new DevelopmentCard(this.getVictoryPoints(), this.getColor(), this.getLevel(), cost, prodIn, prodOut);
        return dv;
    }

}
