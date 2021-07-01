package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCardCost;
import it.polimi.ingsw.model.cards.LeaderCard;

import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.CardType;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.enumerations.Resource;

import java.util.HashMap;
import java.util.List;

/**
 * White marble leader card
 */
public class WhiteMarbleLeaderCard extends LeaderCard {

    private final HashMap<Resource, Integer> productionOut;
    private final List<LeaderCardCost> activationCost;

    /**
     * @param cardType          leader card type
     * @param vp                victory points given
     * @param activationCost    cost to activate the leader card, given as arraylist of activation costs
     * @param productionOut     resources given when a white marble is picked
     */
    public WhiteMarbleLeaderCard(CardType cardType, int vp, List<LeaderCardCost> activationCost, HashMap<Resource, Integer> productionOut) {
        this.Vp = vp;
        this.isActivated = false;
        this.activationCost = activationCost;
        this.productionOut = productionOut;
        this.cardType = cardType;
        this.isActivated = false;
        this.isDiscarded = false;
        //this.cardsCompositionMethods =new CardsCompositionMethods(activationCost);
    }

    /**
     * the method is called by the controller only when the player picks up a white marble
     * from the market and decide to use his WhiteMarble LeaderCard to use obtain the resources.
     *
     * @return returns the resources in output
     */
    public HashMap<Resource, Integer> useLeaderCard() {
        return productionOut;
    }

    /**
     * @return the activation cost given as list of leader card cost
     */
    public List<LeaderCardCost> getActivationCost() {
        return activationCost;
    }

    /**
     * @return power production of the leader card:
     * - key value: resource type
     * - object value: number of resources given when a white marble is picked
     */
    @Override
    public HashMap<Resource, Integer> getLeaderCardPower() {
        return (HashMap<Resource, Integer>) productionOut.clone();
    }

    /**
     * @param developmentCards list of development cards held by the player
     * @return true if available development cards are enough to activate the leader card, false otherwise
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
                "\nProduction out: " + productionOut.toString() +
                "\nActivation cost: " + activationCost.toString();
    }

    /**
     * This method transform a card to a String that represent the image name in the resources directory. It will be used to upload the image of the card in the GUI
     * @return  a String that will be used for the path of the image
     */
    @Override
    public String toPath() {
        return "white" +
                productionOut.keySet().iterator().next().toString().charAt(0) +
                productionOut.keySet().iterator().next().toString().substring(1).toLowerCase();
    }



}
