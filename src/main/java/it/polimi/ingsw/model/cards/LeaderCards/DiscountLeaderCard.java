package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCardCost;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.CardType;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;
import java.util.List;

/**
 * Discount Leader Card
 * A Leader Card that ,when activated, gives a discount on one (or more) resources when the player wants to buy a Development card.
 */

public class DiscountLeaderCard extends LeaderCard {
    private final List<LeaderCardCost> activationCost;
    private final HashMap<Resource, Integer> discountedResource;

    /**
     * @param vp                       Victory points
     * @param cardType                 leader card type
     * @param activationCost           the cost to activate the Leader card for the first time,
     *                                 the activation cost for this specific Leader card is a Leader Card Cost.
     * @param discountedResource       number of resources given as discount on a development card purchase
     */
    public DiscountLeaderCard(
            CardType cardType,
            int vp,
            List<LeaderCardCost> activationCost,
            HashMap<Resource, Integer> discountedResource) {
        this.cardType = cardType;
        this.Vp = vp;
        this.isActivated = false;

        this.activationCost = activationCost;
        this.discountedResource = discountedResource;
        //this.cardsCompositionMethods =new CardsCompositionMethods(activationCost);
    }


    //getters
    public List<LeaderCardCost> getActivationCost() {
        return activationCost;
    }

    /**
     * @return an hashmap containing the disconunted resources, given as:
     * - key: resource
     * - value: discount value
     */
    @Override
    public HashMap<Resource, Integer> getLeaderCardPower() {
        return (HashMap<Resource, Integer>) discountedResource.clone();
    }

    /**
     * @param developmentCards
     * @return true if, given the list of development cards, the leader card (this) is activatable
     */
    public boolean isActivatable(List<DevelopmentCard> developmentCards) {

        boolean activatable = true;

        for (int i = 0; i < this.activationCost.size(); i++) {
            Integer equalCards = 1;
            Integer satisfied = 0;
            CardColor cardColor = this.activationCost.get(i).getColor();
            Level cardLevel = this.activationCost.get(i).getLevel();

            for (int j = 0; j < this.activationCost.size(); j++) {
                if(i != j)
                    if (this.activationCost.get(j).getColor().equals(this.activationCost.get(i).getColor())
                            && this.activationCost.get(j).getLevel().equals(this.activationCost.get(i).getLevel()))
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
        return "\nCard type: " + this.cardType +
                "\nVictory points: " + this.Vp +
                "\nDiscounted resources: " + discountedResource.toString() +
                "\nActivation cost: " + activationCost.toString();
    }

    @Override
    public String toPath() {
        return "discount" +
                discountedResource.keySet().iterator().next().toString().charAt(0) +
                discountedResource.keySet().iterator().next().toString().substring(1).toLowerCase();
    }

}
