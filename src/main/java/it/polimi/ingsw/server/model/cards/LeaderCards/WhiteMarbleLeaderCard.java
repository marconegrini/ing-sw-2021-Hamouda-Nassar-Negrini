package it.polimi.ingsw.server.model.cards.LeaderCards;

import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.LeaderCardCost;
import it.polimi.ingsw.server.model.cards.LeaderCard;

import it.polimi.ingsw.server.model.enumerations.CardColor;
import it.polimi.ingsw.server.model.enumerations.CardType;
import it.polimi.ingsw.server.model.enumerations.Level;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.HashMap;
import java.util.List;


public class WhiteMarbleLeaderCard extends LeaderCard {
    private final HashMap <Resource, Integer> productionOut;
    //    private final HashMap <Resource, Integer> outProductionResource;
    private final List<LeaderCardCost> activationCost;


    public WhiteMarbleLeaderCard(CardType cardType, int vp, List<LeaderCardCost> activationCost, HashMap <Resource, Integer> productionOut) {
        this.Vp = vp;
        this.isActivated = false;
        this.activationCost = activationCost;
        this.productionOut = productionOut;
        this.cardType=cardType;
        //this.cardsCompositionMethods =new CardsCompositionMethods(activationCost);
    }

    /**
     * the method is called by the controller only when the player picks up a white marble
     * from the market and decide to use his WhiteMarble LeaderCard to use obtain the resources.
     * @return returns the resources in output
     */
    public HashMap <Resource, Integer> useLeaderCard(){
        return productionOut;
    }


    //getters
    public List<LeaderCardCost> getActivationCost() {
        return activationCost;
    }

    public HashMap<Resource, Integer> getOutProductionResource() {
        return (HashMap<Resource, Integer>) productionOut.clone();
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
                "\nProduction out: " + productionOut.toString() +
                "\nActivation cost: " + activationCost.toString();
    }

}
