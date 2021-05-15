package it.polimi.ingsw.client.ClientModel.cards.LeaderCards;

import it.polimi.ingsw.client.ClientModel.cards.LeaderCard;
import it.polimi.ingsw.client.ClientModel.cards.LeaderCardCost;
import it.polimi.ingsw.model.enumerations.CardType;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;
import java.util.List;


public class WhiteMarbleLeaderCard extends LeaderCard {
    private final HashMap <Resource, Integer> productionOut;
    //    private final HashMap <Resource, Integer> outProductionResource;
    private final HashMap<LeaderCardCost,Integer> activationCost;


    public WhiteMarbleLeaderCard(CardType cardType, int vp, HashMap<LeaderCardCost,Integer> activationCost, HashMap <Resource, Integer> productionOut) {
        this.Vp = vp;
        this.isFlipped = false;
        this.activationCost = activationCost;
        this.productionOut = productionOut;
        this.cardType=cardType;
        this.cardsCompositionMethods =new CardsCompositionMethods(activationCost);
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
    public HashMap<LeaderCardCost,Integer> getActivationCost() {
        return activationCost;
    }

    public HashMap<Resource, Integer> getOutProductionResource() {
        return productionOut;
    }


    public boolean verifyToActivate(List<LeaderCardCost> cards){
        return cardsCompositionMethods.verifyToActivate(cards);
    }

    @Override
    public String toString() {
        return  "\nCard type: " + this.cardType +
                "\nVictory points: " + this.Vp +
                "\nProduction out: " + productionOut.toString() +
                "\nActivation cost: " + activationCost.toString();
    }
}
