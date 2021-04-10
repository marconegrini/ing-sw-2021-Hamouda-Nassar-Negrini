package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Coffer implements Deposit{

    private HashMap<Resource, Integer> coffer;

    public Coffer(){
        coffer = new HashMap<>();
        coffer.put(Resource.COIN, 0);
        coffer.put(Resource.SERVANT, 0);
        coffer.put(Resource.SHIELD, 0);
        coffer.put(Resource.STONE, 0);
    }

    public void putResource(HashMap<Resource, Integer> resources){
        Set<Resource> resourcesToPut = resources.keySet();
        Iterator iterator = resourcesToPut.iterator();

        while (iterator.hasNext()) {
            Resource resource = (Resource) iterator.next();
            Integer resourceNumber = resources.get(resource);
            Integer cofferResourceNumber = coffer.get(resource);
            coffer.put(resource, resourceNumber + cofferResourceNumber);
        }
    }

    @Override
    public void pullResource(HashMap<Resource, Integer> cost) {
        if(this.checkAvailability(cost)){
            Set<Resource> resourcesToPull = cost.keySet();
            Iterator iterator = resourcesToPull.iterator();

            while(iterator.hasNext()) {
                Resource resource = (Resource) iterator.next();
                Integer resourceCost = cost.get(resource);
                Integer cofferResourceNumber = coffer.get(resource);

                coffer.put(resource, cofferResourceNumber - resourceCost);
            }

         System.out.println("Resources pulled");
        }
    }

    @Override
    public boolean checkAvailability(HashMap<Resource, Integer> cost) {
        Set<Resource> resourcesToCheck = cost.keySet();
        Iterator iterator = resourcesToCheck.iterator();

        while(iterator.hasNext()){
            Resource resource = (Resource) iterator.next();
            Integer resourceCost = cost.get(resource);
            Integer cofferResourceNumber = coffer.get(resource);

            if(cofferResourceNumber < resourceCost)    return false;
        }

        return true;
    }

}
