package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.Resource;

import java.util.*;

public class Coffer implements Deposit{

    private Map<Resource, Integer> coffer;

    public Coffer(){
        coffer = new HashMap<>();
        coffer.put(Resource.COIN, 0);
        coffer.put(Resource.SERVANT, 0);
        coffer.put(Resource.SHIELD, 0);
        coffer.put(Resource.STONE, 0);

/*
        //debugging
        coffer.put(Resource.COIN, 5);
        coffer.put(Resource.SERVANT, 5);
        coffer.put(Resource.SHIELD, 5);
        coffer.put(Resource.STONE, 5);


 */
    }

    /**
     * @param coffer custom constructor used to create a coffer object in the light model.
     *               The Coffer object is build client side after receiving via json file the HashMap Resource, Integer
     *               corresponding to the model's coffer content
     */
    public Coffer(Map<Resource, Integer> coffer){
        this.coffer = coffer;
    }

    /**
     * Inserts specified resources in coffer
     * @param resources to insert in coffer
     */
    public void putResource(List<Resource> resources) {
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

    /**
     * Takes specified resources from coffer
     * @param resourcesToTake resources to take from coffer
     */
    @Override
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

    /**
     * Checks wether specified resources are available or not in coffer
     * @param resourcesToTake resources to check
     * @return true if resources are available inside coffer, false otherwise
     */
    @Override
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

    /**
     * @return total resources inside coffer
     */
    @Override
    public List<Resource> getTotalResources() {
        List<Resource> totalResources = new ArrayList<>();
        for (Resource resource : coffer.keySet())
            for (int i = 0; i < coffer.get(resource); i++)
                totalResources.add(resource);

        return totalResources;
    }

    /**
     * @param resource
     * @param resources
     * @return occurrences of resource in resources list
     */
    @Override
    public Integer occurrences(Resource resource, List<Resource> resources){
        return Math.toIntExact(resources.stream().filter(x -> x.equals(resource)).count());
    }

    /**
     * @param resource
     * @return occurrences of resource in coffer
     */
    public int resourceOccurrences(Resource resource){
        return coffer.get(resource);
    }

    /**
     * @return cloned coffer's hash map needed to update client's coffer in light model
     */
    public HashMap<Resource, Integer> getClonedCoffer(){
        HashMap<Resource, Integer> clonedCoffer = new HashMap<>();
        for(Resource res : coffer.keySet())
            clonedCoffer.put(res, coffer.get(res));
        return  clonedCoffer;
    }



}
