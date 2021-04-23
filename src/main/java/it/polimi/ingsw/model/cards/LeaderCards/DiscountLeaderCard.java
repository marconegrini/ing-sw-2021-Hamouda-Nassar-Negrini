package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;

/**
 * Discount Leader Card
 * A Leader Card that ,when activated and, gives a discount on one (or more) resources when the player wants to buy a Development card.
 *
 * */

public class DiscountLeaderCard extends LeaderCard {
    private final HashMap <Resource, Integer> discountedResource;
    private final HashMap<CardColor, Integer> activationCost;

    public DiscountLeaderCard(int vp, HashMap<CardColor, Integer> activationCost, HashMap <Resource, Integer> discountedResource) {
        this.Vp = vp;
        this.isFlipped = false;

        this.activationCost=activationCost;
        this.discountedResource=discountedResource;
    }


    //getters
    public HashMap<CardColor, Integer> getActivationCost() {
        return activationCost;
    }

    public HashMap<Resource, Integer> getDiscountedResourceInfo() {
        return discountedResource;
    }


}
