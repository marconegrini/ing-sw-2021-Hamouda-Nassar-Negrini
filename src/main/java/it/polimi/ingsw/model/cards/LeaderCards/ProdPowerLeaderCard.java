package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.UnsufficientResourcesException;


import java.util.HashMap;


/**
 * Production Power Leader Card
 * A Leader Card that takes one (or more) resource and gives back --> one (or more) resource chosen by the player and one (or more) Faith point.
 * It is activated with one (or more) Development Card.
 *
 * */

public class ProdPowerLeaderCard extends WhiteProdDiscountCardsSuperClass {


    private final HashMap <Resource, Integer> resourceInProductionType;

    private final int outProductionResourceNum ;
    private final int outProductionFaithPoints ;
    private final Level requiredActivationLevel;

    /**
     *
     * @param vp Victory points
     * @param activationCost the cost to activate the Leader card for he first time,
     *                       the activation cost for this specific Leader card is of Development card type.
     *                      * An important supposition: FOR ALL THE LEADER CARDS THE ACTIVATION COST CAN'T BE TWO DV CARDS WITH THE SAME COLOUR!!
     * @param resourceInProductionType the type and number of resources that are necessary to activate the card and produce output.
     * @param outProductionFaithPoints number of faith points out of the production (in output)
     * @param outProductionResourceNum number of resources in output, the controller or the class that manage the production will
     *                                 use this number to know how many resources should the user choose.
     */
    public ProdPowerLeaderCard(int vp, HashMap<CardColor, Level> activationCost, HashMap <Resource, Integer> resourceInProductionType, int outProductionResourceNum, int outProductionFaithPoints) {
        this.Vp = vp;
        this.isFlipped=false;
        this.requiredActivationLevel = Level.SECOND;
        this.activationCost = activationCost;
        this.resourceInProductionType=resourceInProductionType;

        this.outProductionFaithPoints = outProductionFaithPoints;
        this.outProductionResourceNum = outProductionResourceNum;
    }


    //getters
    public HashMap <Resource, Integer> getResourceInProduction()
    { return resourceInProductionType; }

    public HashMap<CardColor, Level> getActivationCost() {
        return null;
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



}
