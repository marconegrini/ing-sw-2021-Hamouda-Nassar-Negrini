package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;

import java.security.CryptoPrimitive;
import java.util.HashMap;


/**
 * Card that takes one (or more) resource and gives back --> one (or more) resource chosen by the player and one (or more) Faith point.
 * It is activated with one (or more) Development Card.
 *
 * */

public class ProdPowerLeaderCard extends LeaderCard {


    private HashMap <Resource, Integer> resourceInProductionType;
    protected HashMap<CardColor, Level> activationCost;
    private int outProductionResourceNum = 1;
    private int outProductionFaithPoints = 1;

    /**
     *
     * @param vp Victory points
     * @param activationCost the cost to activate the Leader card for he first time,
     *                       the activation cost for this specific Leader card is of Development card type.
     * @param resourceInProductionType the type and number of resources that are necessary to activate the card and produce output.
     * @param outProductionFaithPoints number of faith points out of the production (in output)
     * @param outProductionResourceNum number of resources in output, the controller or the class that manage the production will
     *                                 use this number to know how many resources should the user choose.
     */
    public ProdPowerLeaderCard(int vp, HashMap<CardColor, Level> activationCost, HashMap <Resource, Integer> resourceInProductionType, int outProductionResourceNum, int outProductionFaithPoints) {
        this.Vp = vp;
        this.activationCost = activationCost;
        this.resourceInProductionType=resourceInProductionType;

        this.outProductionFaithPoints = outProductionFaithPoints;
        this.outProductionResourceNum = outProductionResourceNum;
        this.isFlipped=false;
    }


    //getters
    public HashMap <Resource, Integer> getResourceInProduction()
    { return resourceInProductionType; }

    public HashMap<DevelopmentCard, Integer> getActivationCost() {
        return null;
    }


    public int receiveFaithPoints(){
        return outProductionFaithPoints;
    }

    /**
     *  the controller or the class that manage the production will use this attribute to know how many resources should the user choose
     */
    public int getOutProductionResourceNum(){
        return outProductionResourceNum;
    }

    @Override
    public int discardAndReceiveVPointsIfNotActivated() {
        //TODO add a method that discards the card or manage the discard from the controller.
        this.isFlipped=false;
        return getVictoryPoints();
    }


}
