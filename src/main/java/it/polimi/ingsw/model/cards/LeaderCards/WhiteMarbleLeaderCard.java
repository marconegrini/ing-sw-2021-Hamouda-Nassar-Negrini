package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.model.cards.LeaderCardCost;
import it.polimi.ingsw.model.cards.LeaderCard;

import it.polimi.ingsw.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.HashMap;


public class WhiteMarbleLeaderCard extends LeaderCard {

    private final HashMap <Resource, Integer> outProductionResource;
    private final HashMap<LeaderCardCost,Integer> activationCost;

    private CardsCompositionMethods cardsCompositionMethods;



    public WhiteMarbleLeaderCard(int vp, HashMap<LeaderCardCost,Integer> activationCost, HashMap <Resource, Integer> OutProductionResource) {
        this.Vp = vp;
        this.isFlipped = false;
        this.outProductionResource = OutProductionResource;
        this.activationCost=activationCost;

        cardsCompositionMethods =new CardsCompositionMethods(activationCost);
    }

    /**
     * the method is called by the controller only when the player picks up a white marble
     * from the market and decide to use his WhiteMarble LeaderCard to use obtain the resources.
     * @return returns the resources in output
     */
    public HashMap <Resource, Integer> useLeaderCard(){
        return outProductionResource;
    }


    //getters
    public HashMap<LeaderCardCost,Integer> getActivationCost() {
        return activationCost;
    }

    public HashMap<Resource, Integer> getOutProductionResource() {
        return outProductionResource;
    }


    public boolean verifyToActivate(ArrayList<LeaderCardCost> cards){
        return cardsCompositionMethods.verifyToActivate(cards);
    }



}
