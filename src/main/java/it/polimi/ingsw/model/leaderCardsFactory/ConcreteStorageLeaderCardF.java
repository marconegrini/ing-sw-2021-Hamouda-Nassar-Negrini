package it.polimi.ingsw.model.leaderCardsFactory;

import it.polimi.ingsw.model.DataIn;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.DiscountLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.StorageLeaderCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ConcreteStorageLeaderCardF extends LeaderCardFactory{

    @Override
    public LeaderCard construct(DataIn dataIn) {

        Set<Object> resourcesToCast = dataIn.getActivationCost().keySet();
        List<Resource> resources = new ArrayList<>();
        for(Object o : resourcesToCast){
            resources.add((Resource) o);
        }
        HashMap<Resource, Integer> activationCost = new HashMap<>();
        for(Resource resource : resources)
            activationCost.put(resource, dataIn.getActivationCost().get(resource));

        HashMap<Resource, Integer> storage = new HashMap<Resource, Integer>();
        storage.put(dataIn.getResourceType(), 2);

//        return new StorageLeaderCard(dataIn.getVp(), activationCost, storage);
          return null;
    }
}
