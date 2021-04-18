package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;
import java.util.Set;

public abstract class WhiteProdDiscountCardsSuperClass extends LeaderCard {

    protected HashMap<CardColor, Level> activationCost;

    public boolean activateLeaderCard(HashMap<CardColor, Level> cardsIn){
        HashMap <Resource, Integer> tempHash = new HashMap<>();
        if (verifyToActivate(cardsIn))
        {isFlipped=true;
            return true;}
        else return false;
    }

    /**
     *  An important supposition: FOR ALL THE LEADER CARDS THE ACTIVATION COST CAN'T BE TWO DV CARDS WITH THE SAME COLOUR!!
     * @param cardsIn they are cards passed by the user to activate the leader card
     * @return true if the player have all the necessary DV cards to activate the leader cards otherwise returns false.
     */
    public boolean verifyToActivate(HashMap<CardColor, Level> cardsIn){
        Set<CardColor> activationCostColors = activationCost.keySet();
        Set<CardColor> cardsToCheck = cardsIn.keySet();

        boolean innerResult = false;

        boolean result = false;

        /**
         * if the given cards cover all the costCards the result is true
         */
        for (CardColor activationCostColor  : activationCostColors){

            for (CardColor cardColorToCheck : cardsToCheck) {
                Level activationCostLevel = activationCost.get(activationCostColor);
                Level  cardLevelToCheck = cardsIn.get(activationCostColor);

                if (activationCostColor.equals(cardColorToCheck) && activationCostLevel.equals(cardLevelToCheck) ) {
                    innerResult = true;
                    break;
                } else
                    innerResult = false;
            }

            //if i arrive here with a false it means that i checked one DV card of the cost and it didn't match with ANY of the given card.
            if (innerResult ==false)
            {result=false;
                break;}
            else result = true;
        }

        return result;
    }
}
