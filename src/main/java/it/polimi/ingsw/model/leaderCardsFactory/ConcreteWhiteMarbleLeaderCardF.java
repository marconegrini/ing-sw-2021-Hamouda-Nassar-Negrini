package it.polimi.ingsw.model.leaderCardsFactory;

import it.polimi.ingsw.model.DataIn;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.StorageLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.WhiteMarbleLeaderCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ConcreteWhiteMarbleLeaderCardF extends LeaderCardFactory{

    @Override
    public LeaderCard construct(DataIn dataIn) {
        Set<Object> cardsToCast = dataIn.getActivationCost().keySet();
        List<CardColor> cards = new ArrayList<>();
        for(Object o : cardsToCast){
            cards.add((CardColor) o);
        }
        HashMap<CardColor, Integer> activationCost = new HashMap<>();
        for(CardColor card : cards)
            activationCost.put(card, dataIn.getActivationCost().get(card));

        HashMap<Resource, Integer> productionResource = new HashMap<>();
        productionResource.put(dataIn.getResourceType(), 1);

        //return new WhiteMarbleLeaderCard(dataIn.getVp(), activationCost, productionResource);
        return null;
    }
}
