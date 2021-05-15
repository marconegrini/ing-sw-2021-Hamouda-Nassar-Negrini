package it.polimi.ingsw.client.ClientModel;


import it.polimi.ingsw.client.ClientModel.enumerations.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Coffer implements Deposit {

    private Map<Resource, Integer> coffer;

    public Coffer(){
        coffer = new HashMap<>();
        coffer.put(Resource.COIN, 0);
        coffer.put(Resource.SERVANT, 0);
        coffer.put(Resource.SHIELD, 0);
        coffer.put(Resource.STONE, 0);
    }

    public void putResource(List<Resource> resources){
        Integer coinOccurr = occurrences(Resource.COIN, resources);
        Integer stoneOccurr = occurrences(Resource.STONE, resources);
        Integer servantOccurr = occurrences(Resource.SERVANT, resources);
        Integer shieldOccurr = occurrences(Resource.SHIELD, resources);

        coinOccurr += coffer.get(Resource.COIN);
        coffer.put(Resource.COIN, coinOccurr);

        stoneOccurr += coffer.get(Resource.STONE);
        coffer.put(Resource.STONE, stoneOccurr);

        servantOccurr += coffer.get(Resource.SERVANT);
        coffer.put(Resource.SERVANT, servantOccurr);

        shieldOccurr += coffer.get(Resource.SHIELD);
        coffer.put(Resource.SHIELD, shieldOccurr);

    }

    public void pullResource(List<Resource> resourcesToTake){

        Integer coinOccurr = occurrences(Resource.COIN, resourcesToTake);
        Integer stoneOccurr = occurrences(Resource.STONE, resourcesToTake);
        Integer servantOccurr = occurrences(Resource.SERVANT, resourcesToTake);
        Integer shieldOccurr = occurrences(Resource.SHIELD, resourcesToTake);

        if(this.checkAvailability(resourcesToTake)){

            coinOccurr = coffer.get(Resource.COIN) - coinOccurr;
            coffer.put(Resource.COIN, coinOccurr);

            stoneOccurr = coffer.get(Resource.STONE) - stoneOccurr;
            coffer.put(Resource.STONE, stoneOccurr);

            servantOccurr = coffer.get(Resource.SERVANT) - servantOccurr;
            coffer.put(Resource.SERVANT, servantOccurr);

            shieldOccurr = coffer.get(Resource.SHIELD) - shieldOccurr;
            coffer.put(Resource.SHIELD, shieldOccurr);

        }
    }


    public boolean checkAvailability(List<Resource> resourcesToTake) {

        Integer coinOccurr = occurrences(Resource.COIN, resourcesToTake);
        Integer stoneOccurr = occurrences(Resource.STONE, resourcesToTake);
        Integer servantOccurr = occurrences(Resource.SERVANT, resourcesToTake);
        Integer shieldOccurr = occurrences(Resource.SHIELD, resourcesToTake);

        if(coinOccurr > coffer.get(Resource.COIN)) return false;
        if(stoneOccurr > coffer.get(Resource.STONE)) return false;
        if(servantOccurr > coffer.get(Resource.SERVANT)) return false;
        if(shieldOccurr > coffer.get(Resource.SHIELD)) return false;

        return true;
    }

    @Override
    public List<Resource> getTotalResources() {
        List<Resource> totalResources = new ArrayList<>();
        for (Resource resource : coffer.keySet())
            for (int i = 0; i < coffer.get(resource); i++)
                totalResources.add(resource);

        return totalResources;
    }

    @Override
    public Integer occurrences(Resource resource, List<Resource> resources){
        return Math.toIntExact(resources.stream().filter(x -> x.equals(resource)).count());
    }

}
