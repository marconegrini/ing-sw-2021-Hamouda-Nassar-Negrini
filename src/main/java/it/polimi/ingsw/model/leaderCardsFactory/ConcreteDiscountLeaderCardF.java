package it.polimi.ingsw.model.leaderCardsFactory;

import it.polimi.ingsw.model.DataIn;
import it.polimi.ingsw.model.cards.LeaderCardCost;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.DiscountLeaderCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ConcreteDiscountLeaderCardF extends LeaderCardFactory{

    @Override
    public LeaderCard construct(DataIn dataIn) {
        //HashMap<CardColor, Level> activationCost = new HashMap<>();
        Set<Object> cardsToCast = dataIn.getActivationCost().keySet();
        List<CardColor> cards = new ArrayList<>();
        for(Object o : cardsToCast){
            cards.add((CardColor) o);
        }
        HashMap<LeaderCardCost,Integer> activationCost = new HashMap<>();
//        for(CardColor card : cards)
//            activationCost.put(card, dataIn.getActivationCost().get(card));

        HashMap<Resource, Integer> discountedResource = new HashMap<Resource, Integer>();
        discountedResource.put(dataIn.getResourceType(), 1);

        return null;
    }
}
