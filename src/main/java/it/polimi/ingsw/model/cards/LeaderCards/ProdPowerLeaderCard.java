package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;


import java.util.HashMap;
import java.util.Set;


/**
 * Production Power Leader Card
 * A Leader Card that takes one (or more) resource and gives back --> one (or more) resource chosen by the player and one (or more) Faith point.
 * It is activated with one (or more) Development Card.
 *
 * */

public class ProdPowerLeaderCard extends LeaderCard {


    private final HashMap <Resource, Integer> resourceInProductionType;
    private final HashMap<CardColor, Level> activationCost;
    private final int outProductionResourceNum ;
    private final int outProductionFaithPoints ;

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


    /**
     *  An important supposition: FOR ALL THE LEADER CARDS THE ACTIVATION COST CAN'T BE TWO DV CARDS WITH THE SAME COLOUR!!
     * @param givenCardsToUse they are cards passed by the user to activate the leader card
     * @return true if the player have all the necessary DV cards to activate the leader cards otherwise returns false.
     */
    public boolean verifyToActivate(HashMap<CardColor, Level> givenCardsToUse){
        Set<CardColor> activationCostColors = activationCost.keySet();
        Set<CardColor> cardsToCheck = givenCardsToUse.keySet();

        boolean innerVerification = false;
        boolean result = false;

        /**
         * if the given cards cover all the cost cards the result
         */
        for (CardColor activationCostColor  : activationCostColors){
            for (CardColor cardColorToCheck : cardsToCheck)
                if (activationCostColor.equals(cardColorToCheck) && activationCost.get(activationCostColor).equals(givenCardsToUse.get(cardColorToCheck))) {
                    result = true;
                    break;
                }else
                    result = false;
        }
    return result;
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



}
