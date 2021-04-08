package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;

public interface Deposit {

    void pullResource(HashMap<Resource, Integer> cost);

    boolean checkAvailability(HashMap<Resource, Integer> cost);
}
