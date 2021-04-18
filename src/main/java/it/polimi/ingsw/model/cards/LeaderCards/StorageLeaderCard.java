package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;


public class StorageLeaderCard extends LeaderCard {
    private final HashMap<Resource,Integer> activationCost;
    private final HashMap<Resource,Integer> storageResource;

    private int occupationNumber;

    public StorageLeaderCard(int vp, HashMap<Resource, Integer> activationCost, HashMap <Resource, Integer> storageResource) {
        this.Vp = vp;
        this.isFlipped = false;

        this.activationCost=activationCost;
        this.storageResource=storageResource;

    }





    //getters
    public HashMap<Resource, Integer> getActivationCost() {
        return activationCost;
    }

    public HashMap<Resource, Integer> getStorageResource() {
        return storageResource;
    }




}
