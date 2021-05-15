package it.polimi.ingsw.client.ClientModel.cards.LeaderCards;

import it.polimi.ingsw.client.ClientModel.cards.LeaderCard;
import it.polimi.ingsw.client.ClientModel.cards.LeaderCardCost;
import it.polimi.ingsw.model.enumerations.CardType;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;
import java.util.List;

/**
 * Discount Leader Card
 * A Leader Card that ,when activated and, gives a discount on one (or more) resources when the player wants to buy a Development card.
 *
 * */

public class DiscountLeaderCard extends LeaderCard {
    private final HashMap <Resource, Integer> discountedResource;
    private final HashMap<LeaderCardCost,Integer> activationCost;

    public DiscountLeaderCard(
            CardType cardType,
            int vp,
            HashMap<LeaderCardCost,Integer> activationCost,
            HashMap <Resource, Integer> discountedResource)
    {
        this.cardType = cardType;
        this.Vp = vp;
        this.isFlipped = false;

        this.activationCost=activationCost;
        this.discountedResource=discountedResource;

        this.cardsCompositionMethods =new CardsCompositionMethods(activationCost);

    }


    //getters
    public HashMap<LeaderCardCost,Integer> getActivationCost() {
        return activationCost;
    }

    public HashMap<Resource, Integer> getDiscountedResourceInfo() {
        return discountedResource;
    }

    public boolean verifyToActivate(List<LeaderCardCost> cards){
        return cardsCompositionMethods.verifyToActivate(cards);
    }

    @Override
    public String toString() {
        return  "\nCard type: " + this.cardType +
                "\nVictory points: " + this.Vp +
                "\nDiscounted resources: " + discountedResource.toString() +
                "\nActivation cost: " + activationCost.toString();
    }

}
