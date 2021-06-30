package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.enumerations.*;
import it.polimi.ingsw.enumerations.CardColor;

import java.util.HashMap;

/**
 * Development card class
 */
public class DevelopmentCard extends Card{

    private final CardColor color;
    private final Level level;
    private final HashMap<Resource, Integer> cardCost;
    private final HashMap<Resource, Integer> productionIn;
    private final HashMap<Resource, Integer> productionOut;

    /**
     * @param Vp            victory points given
     * @param cardColor     card color
     * @param level         card level
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
     * @return  a cloned development card
     */
    public DevelopmentCard clone(){
        HashMap<Resource, Integer> cost = this.getCardCost();
        HashMap<Resource, Integer> prodIn = this.getProductionIn();
        HashMap<Resource, Integer> prodOut = this.getProductionOut();
        DevelopmentCard dv = new DevelopmentCard(this.getVictoryPoints(), this.getColor(), this.getLevel(), cost, prodIn, prodOut);
        return dv;
    }

    /**
     * This method transform a card to a String that represent the image name in the resources directory. It will be used to upload the image of the card in the GUI
     * @return  a String that will be used for the path of the image
     */
    public String toPath() throws NullPointerException{
        return this.color.toString() + this.Vp;
    }

}
