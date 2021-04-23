package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;

public class WhiteMarbleLeaderCard extends LeaderCard {

    private final HashMap <Resource, Integer> productionOut;
    protected final HashMap<CardColor, Integer> activationCost;

    public WhiteMarbleLeaderCard(int vp, HashMap<CardColor, Integer> activationCost, HashMap <Resource, Integer> productionOut) {
        this.Vp = vp;
        this.isFlipped = false;
        this.activationCost = activationCost;
        this.productionOut = productionOut;
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
    public HashMap<CardColor, Integer> getActivationCost() {
        return activationCost;
    }

    public HashMap<Resource, Integer> getOutProductionResource() {
        return productionOut;
    }
}
