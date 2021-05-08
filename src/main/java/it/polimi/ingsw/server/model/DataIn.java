package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.HashMap;

public class DataIn {
    protected int Vp;
    private HashMap<Object, Integer> activationCost;
    private Resource resourceType;

    public DataIn(Integer victoryPoints, HashMap<Object, Integer> activationCost, Resource resourceType){
        this.Vp = Vp;
        this.activationCost = activationCost;
        this.resourceType= resourceType;
    }

    public int getVp() {
        return Vp;
    }

    public HashMap<Object, Integer> getActivationCost() {
        return activationCost;
    }

    public Resource getResourceType() {
        return resourceType;
    }
}
