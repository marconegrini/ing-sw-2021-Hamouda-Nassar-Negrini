package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;

public class StorageLeaderCard extends LeaderCard {

    private final HashMap<Resource,Integer> activationCost;
    private final HashMap<Resource, Integer> storage;

    public StorageLeaderCard(Integer victoryPoints, HashMap<Resource,Integer> activationCost, HashMap<Resource, Integer> storage){
        this.Vp = victoryPoints;
        this.isFlipped = false;
        this.activationCost = activationCost;
        this.storage = storage;
    }

}
