package it.polimi.ingsw.server.model.cards.LeaderCards;

import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.cards.LeaderCardCost;
import it.polimi.ingsw.server.model.enumerations.CardColor;
import it.polimi.ingsw.server.model.enumerations.CardType;
import it.polimi.ingsw.server.model.enumerations.Level;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.UnsufficientResourcesException;


import java.util.HashMap;
import java.util.List;


/**
 * Production Power Leader Card
 * A Leader Card that takes one (or more) resource and gives back --> one (or more) resource chosen by the player and one (or more) Faith point.
 * It is activated with one (or more) Development Card.
 *
 * */

public class ProdPowerLeaderCard extends LeaderCard {


    private final HashMap <Resource, Integer> productionIn;

    private final int outProductionResourceNum ;
    private final int outProductionFaithPoints ;
    private final List<LeaderCardCost> activationCost;

    /**
     *
     * @param vp Victory points
     * @param activationCost the cost to activate the Leader card for he first time,
     *                       the activation cost for this specific Leader card is of Development card type.
     *                      * An important supposition: FOR ALL THE LEADER CARDS THE ACTIVATION COST CAN'T BE TWO DV CARDS WITH THE SAME COLOUR!!
     * @param productionIn the type and number of resources that are necessary to activate the card and produce output.
     * @param outProductionFaithPoints number of faith points out of the production (in output)
     * @param outProductionResourceNum number of resources in output, the controller or the class that manage the production will
     *                                 use this number to know how many resources should the user choose.
     */
    public ProdPowerLeaderCard(
            CardType cardType,
            int vp,
            List<LeaderCardCost> activationCost,
            HashMap <Resource, Integer> productionIn,
            int outProductionResourceNum,
            int outProductionFaithPoints)

    {
        this.cardType=cardType;
        this.Vp = vp;
        this.isActivated=false;
        this.activationCost = activationCost;
        this.productionIn=productionIn;

        this.outProductionFaithPoints = outProductionFaithPoints;
        this.outProductionResourceNum = outProductionResourceNum;

        //this.cardsCompositionMethods =new CardsCompositionMethods(activationCost);

    }


    //getters
    public HashMap <Resource, Integer> getResourceInProduction()
    { return productionIn; }

    public List<LeaderCardCost> getActivationCost() {
        return activationCost;
    }


    //public boolean activateLeaderCard(HashMap<CardColor, Level> cardsIn){} //in the super class


    public HashMap <Resource, Integer> useCard(HashMap<Resource, Integer> resourceIn) throws UnsufficientResourcesException {
        //TODO call the verification method from the coffer&Warehouse and if true return the resources..
        // TODO give the player the possibility to choose one (or more) Resource as a given output as well as the faithPoint

        Integer faithPoints = faithPointsNum();
        HashMap<Resource, Integer> tempHash = new HashMap<>();
        tempHash.put(Resource.FAITH,faithPoints);

        return tempHash;
    }


    public Integer faithPointsNum(){
        return outProductionFaithPoints;
    }

    /**
     *  the controller or the class that manage the production will use this attribute to know how many resources should the user choose
     */
    public Integer getOutProductionResourceNum(){
        return outProductionResourceNum;
    }

    public boolean isActivatable(List<DevelopmentCard> developmentCards) {
        boolean activatable = true;

        for (int i = 0; i < this.activationCost.size(); i++) {
            Integer equalCards = 1;
            Integer satisfied = 0;
            CardColor cardColor = this.activationCost.get(i).getColor();
            Level cardLevel = this.activationCost.get(i).getLevel();
            for (int j = 0; j < this.activationCost.size() && j != i; j++) {
                if (this.activationCost.get(j).equals(this.activationCost.get(i)))
                    equalCards++;
            }
            if (cardLevel.equals(Level.ANY))
                for (DevelopmentCard dv : developmentCards)
                    if (dv.getColor().equals(cardColor))
                        satisfied++;

            if (cardLevel.equals(Level.FIRST))
                for (DevelopmentCard dv : developmentCards)
                    if (dv.getLevel().equals(Level.FIRST) && dv.getColor().equals(cardColor))
                        satisfied++;

            if (cardLevel.equals(Level.SECOND))
                for (DevelopmentCard dv : developmentCards)
                    if (dv.getLevel().equals(Level.SECOND) && dv.getColor().equals(cardColor))
                        satisfied++;

            if (satisfied < equalCards) activatable = false;
        }

        return activatable;
    }

    @Override
    public String toString() {
        return  "\nCard type: " + this.cardType +
                "\nVictory points: " + this.Vp +
                "\nProduction in: " + productionIn.toString() +
                "\nActivation cost: " + activationCost.toString();
    }




}
