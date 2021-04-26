package it.polimi.ingsw.model;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.*;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Warehouse implements Deposit{
    /**
     * 1 - shelf with capacity = 1
     * 2 - shelf with capacity = 2
     * 3 - shelf with capacity = 3
     */
    private Map<Integer, List<Resource>> warehouse;

    public Warehouse(){
        warehouse = new HashMap<>();
        for (int storage = 1; storage <= 3; storage++) {
            warehouse.put(storage, new ArrayList<>(0));
        }
    }

    /**
     *
     *  @param destStorage: requires an integer between 1 and 3
     *  @param resourceIn: requires an ArrayList of the same type of resources
     * @throws StorageOutOfBoundsException if destStorage is not between 1 and 3
     * @throws IllegalInsertionException if the insertion doesn't satisfy warehouse's rules
     */
    public void putResource(int destStorage, List<Resource> resourceIn) throws StorageOutOfBoundsException,
             IllegalInsertionException {

        if(destStorage < 1 || destStorage > 3) throw new StorageOutOfBoundsException();

        if(!resourceIn.isEmpty()) {

            if (resourceIn.size() > destStorage) throw new IllegalInsertionException();

            //checking that resourcesIn contains resources of the same type
            if (!(resourceIn.stream().filter(x -> x.equals(resourceIn.get(0))).count() == resourceIn.size()))
                throw new IllegalInsertionException();

            //checking that resource type equal to resourcesIn is not contained in shelves other than destStorage
            for (Integer storage : warehouse.keySet()) {
                if(storage != destStorage) {
                    if (!(warehouse.get(storage).stream().filter(x -> x.equals(resourceIn.get(0))).count() == 0))
                        throw new IllegalInsertionException();
                }
            }

            //switch case: the shelf is full
            if ((warehouse.get(destStorage)).size() == destStorage)
                throw new IllegalInsertionException();

            //switch case: shelf is filled partially
            if (!warehouse.get(destStorage).isEmpty()) {

                //checking that resource type in destStorage is the same of resourceIn
            if (((warehouse.get(destStorage)).get(0) != resourceIn.get(0)))
                throw new IllegalInsertionException();


                //checking that there is enough capacity left to insert resourceIn
                if ((warehouse.get(destStorage).size() + resourceIn.size()) <= destStorage)
                    warehouse.get(destStorage).addAll(resourceIn);
                else throw new IllegalInsertionException();

            } else warehouse.get(destStorage).addAll(resourceIn); //switch case: the chosen shelf is empty

        }
    }

    /**
     *
     * @param sourceStorage integer between 1 and 3
     * @param destStorage integer between 1 and 3
     * @throws IllegalMoveException if the move doesn't satisfy warehouse's rules
     * @throws StorageOutOfBoundsException if source or destination storage are not between 1 and 3
     */
    public void moveResource(int sourceStorage, int destStorage) throws IllegalMoveException, StorageOutOfBoundsException {

        if(sourceStorage < 1 || sourceStorage > 3) throw new StorageOutOfBoundsException();
        if(destStorage < 1 || destStorage > 3) throw new StorageOutOfBoundsException();
        if((warehouse.get(sourceStorage).size() <= destStorage) &&
                (warehouse.get(destStorage).size() <= sourceStorage)){

            ArrayList<Resource> temp1 = new ArrayList<>(warehouse.get(destStorage));
            ArrayList<Resource> temp2 = new ArrayList<>(warehouse.get(sourceStorage));

            warehouse.get(sourceStorage).clear();
            warehouse.get(sourceStorage).addAll(temp1);
            warehouse.get(destStorage).clear();
            warehouse.get(destStorage).addAll(temp2);

            temp1.clear();
            temp2.clear();

        } else throw new IllegalMoveException();
    }

    /**
     * Takes resources from warehouse
     * @param resourcesToTake
     */
    @Override
    public void pullResource(List<Resource> resourcesToTake){

        Integer coinOccurr = occurrences(Resource.COIN, resourcesToTake);
        Integer stoneOccurr = occurrences(Resource.STONE, resourcesToTake);
        Integer servantOccurr = occurrences(Resource.SERVANT, resourcesToTake);
        Integer shieldOccurr = occurrences(Resource.SHIELD, resourcesToTake);

        if(this.checkAvailability(resourcesToTake)) {

            for(Integer storage : warehouse.keySet()){

                if(warehouse.get(storage).contains(Resource.COIN))
                    for(int i = 0; i < coinOccurr; i++)
                        warehouse.get(storage).remove(Resource.COIN);

                if(warehouse.get(storage).contains(Resource.STONE))
                    for(int i = 0; i < stoneOccurr; i++)
                        warehouse.get(storage).remove(Resource.STONE);

                if(warehouse.get(storage).contains(Resource.SHIELD))
                    for(int i = 0; i < shieldOccurr; i++)
                        warehouse.get(storage).remove(Resource.SHIELD);

                if(warehouse.get(storage).contains(Resource.SERVANT))
                    for(int i = 0; i < servantOccurr; i++)
                        warehouse.get(storage).remove(Resource.SERVANT);

            }
        }
    }

    /**
     * @param resourcesToTake List of resources needed
     * @return true if resources are present, false otherwise
     */
    @Override
    public boolean checkAvailability(List<Resource> resourcesToTake) {

        List<Resource> totalResources = new ArrayList<>();

        for(Integer storage : warehouse.keySet()){
            totalResources.addAll(warehouse.get(storage));
        }

        if(totalResources.containsAll(resourcesToTake) ||  totalResources.equals(resourcesToTake))
            return true;
        else return false;
    }

    /**
     * @return total resources in the warehouse
     */
    public List<Resource> getTotalResources(){
        List<Resource> totalResources = new ArrayList<>();

        for(Integer storage : warehouse.keySet()){
            totalResources.addAll(warehouse.get(storage));
        }

        return totalResources;
    }

    public Integer occurrences(Resource resource, List<Resource> resources){
        return Math.toIntExact(resources.stream().filter(x -> x.equals(resource)).count());
    }

}
