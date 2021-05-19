package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.CardType;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class StorageLeaderCard extends LeaderCard {

    private final HashMap<Resource, Integer> activationCost;
    private final HashMap<Resource, Integer> storage;

    public StorageLeaderCard(
            CardType cardType,
            int victoryPoints,
            HashMap<Resource, Integer> activationCost,
            HashMap<Resource, Integer> storage) {
        this.cardType = cardType;
        this.Vp = victoryPoints;
        this.isActivated = false;
        this.activationCost = activationCost;
        this.storage = storage;
    }

    public boolean isActivatable(List<Resource> resources) {
        boolean activatable = true;
        Integer coinOccurr = occurrences(Resource.COIN, resources);
        Integer stoneOccurr = occurrences(Resource.STONE, resources);
        Integer servantOccurr = occurrences(Resource.SERVANT, resources);
        Integer shieldOccurr = occurrences(Resource.SHIELD, resources);
        Set<Resource> resourceCost = activationCost.keySet();
        for (Resource resource : resourceCost) {
            if (resource.equals(Resource.COIN))
                if (coinOccurr >= activationCost.get(resource))
                    activatable = false;
            if (resource.equals(Resource.STONE))
                if (stoneOccurr >= activationCost.get(resource))
                    activatable = false;
            if (resource.equals(Resource.SERVANT))
                if (servantOccurr >= activationCost.get(resource))
                    activatable = false;
            if (resource.equals(Resource.SHIELD))
                if (shieldOccurr >= activationCost.get(resource))
                    activatable = false;
        }
        return activatable;
    }

    public Integer occurrences(Resource resource, List<Resource> resources) {
        return Math.toIntExact(resources.stream().filter(x -> x.equals(resource)).count());
    }

    public HashMap<Resource, Integer> getActivationCost() {
        return activationCost;
    }

    @Override
    public HashMap<Resource, Integer> getLeaderCardPower() {
        return (HashMap<Resource, Integer>) this.storage;
    }

    @Override
    public String toString() {
        return "\nCard type: " + this.cardType +
                "\nVictory points: " + this.Vp +
                "\nAvailable storage: " + storage.toString() +
                "\nActivation cost: " + activationCost.toString();
    }
}
