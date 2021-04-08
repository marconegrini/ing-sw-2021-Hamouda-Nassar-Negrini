package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;

public class Coffer implements Deposit{

    @Override
    public void pullResource(HashMap<Resource, Integer> cost){

    }

    @Override
    public boolean checkAvailability(HashMap<Resource, Integer> cost) {

        return false;
    }
}
