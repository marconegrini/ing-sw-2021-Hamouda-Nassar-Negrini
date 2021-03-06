package it.polimi.ingsw.model;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.exceptions.IllegalInsertionException;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.exceptions.StorageOutOfBoundsException;
import it.polimi.ingsw.model.parser.WarehouseParser;

import java.util.*;

/**
 * Class that implements deposits interface. Needed to store player's resources with limitations
 * on storages capacity and rules on insertions.
 */
public class Warehouse implements Deposit{

    private Map<Integer, Storage> warehouse;

    public Warehouse(){

        warehouse = new HashMap<>();
        Map<Integer, Integer> storageNumAndCapacity = new HashMap();
        WarehouseParser parser = new WarehouseParser();
        storageNumAndCapacity = parser.getStorageNumAndCapacity();
        parser.close();

        for(Integer storageNum : storageNumAndCapacity.keySet()){
            Integer storageCapacity = storageNumAndCapacity.get(storageNum);
            List<Resource> resources = new ArrayList<>(0);
            warehouse.put(storageNum, new Storage(storageCapacity, resources));
        }
    }

    /**
     * @param wh custom constructor used to create a warehouse object in the light model.
     *               The Warehouse object is build client side after receiving via json file the HashMap Integer, Storage
     *               corresponding to the model's warehouse content
     */
    public Warehouse(HashMap<Integer, Storage> wh){
        this.warehouse = wh;
    }

    /**
     * Needed to clone the warehouse in order to send it to the client through UpdateWarehouse message
     * @return
     */
    public HashMap<Integer, Storage> getClonedWarehouse(){
        HashMap<Integer, Storage> clonedWarehouse = new HashMap<>();
        for(Integer i : warehouse.keySet())
            clonedWarehouse.put(i, warehouse.get(i));
        return clonedWarehouse;
    }

    /**
     *
     *  @param destStorage: requires an integer between 1 and 3
     *  @param resourceIn: requires an ArrayList of the same type of resources (throws exception if not satisfied)
     * @throws StorageOutOfBoundsException if destStorage is not between 1 and 3
     * @throws IllegalInsertionException if the insertion doesn't satisfy warehouse's rules
     */
    public void putResource(int destStorage, List<Resource> resourceIn) throws StorageOutOfBoundsException,
             IllegalInsertionException {

        if(!warehouse.containsKey(destStorage)) throw new StorageOutOfBoundsException();

        if(!resourceIn.isEmpty()) {

            if (resourceIn.size() > warehouse.get(destStorage).getCapacity()) throw new IllegalInsertionException();

            //checking that resourcesIn contains resources of the same type
            if (!(resourceIn.stream().filter(x -> x.equals(resourceIn.get(0))).count() == resourceIn.size()))
                throw new IllegalInsertionException();

            //checking that resource type equal to resourcesIn is not contained in shelves other than destStorage
            for (Integer storage : warehouse.keySet()) {
                if(storage != destStorage) {
                    if (!(warehouse.get(storage).getResources().stream().filter(x -> x.equals(resourceIn.get(0))).count() == 0))
                        throw new IllegalInsertionException();
                }
            }

            //switch case: the shelf is full
            if ((warehouse.get(destStorage).getResources()).size() == warehouse.get(destStorage).getCapacity())
                throw new IllegalInsertionException();

            //switch case: shelf is filled partially
            if (!warehouse.get(destStorage).getResources().isEmpty()) {

                //checking that resource type in destStorage is the same of resourceIn
                if (!warehouse.get(destStorage).getResourceType().equals(resourceIn.get(0)))
                throw new IllegalInsertionException();


                //checking that there is enough capacity left to insert resourceIn
                if ((warehouse.get(destStorage).getResources().size() + resourceIn.size()) <= warehouse.get(destStorage).getCapacity())
                    warehouse.get(destStorage).insert(resourceIn);
                else throw new IllegalInsertionException();

            } else warehouse.get(destStorage).insert(resourceIn); //switch case: the chosen shelf is empty

        }
    }

    /**
     *
     * @param sourceStorage integer between 1 and 3
     * @param destStorage integer between 1 and 3
     * @throws IllegalMoveException if the move doesn't satisfy warehouse's integrity rules
     * @throws StorageOutOfBoundsException if source or destination storage are not between 1 and 3
     */
    public void moveResource(int sourceStorage, int destStorage) throws IllegalMoveException, StorageOutOfBoundsException {

        if(!warehouse.containsKey(destStorage)) throw new StorageOutOfBoundsException();
        if(!warehouse.containsKey(sourceStorage)) throw new StorageOutOfBoundsException();
        if((warehouse.get(sourceStorage).getResources().size() <= warehouse.get(destStorage).getCapacity()) &&
                (warehouse.get(destStorage).getResources().size() <= warehouse.get(sourceStorage).getCapacity())){

            ArrayList<Resource> temp1 = new ArrayList<>(warehouse.get(destStorage).getResources());
            ArrayList<Resource> temp2 = new ArrayList<>(warehouse.get(sourceStorage).getResources());

            warehouse.get(sourceStorage).getResources().clear();
            warehouse.get(sourceStorage).insert(temp1);
            warehouse.get(destStorage).getResources().clear();
            warehouse.get(destStorage).insert(temp2);

            temp1.clear();
            temp2.clear();

        } else throw new IllegalMoveException();
    }

    /**
     * Takes resources from warehouse
     * @param resourcesToTake needed resources to take from warehouse
     */
    @Override
    public void pullResource(List<Resource> resourcesToTake) {

        Integer coinOccurr = occurrences(Resource.COIN, resourcesToTake);
        Integer stoneOccurr = occurrences(Resource.STONE, resourcesToTake);
        Integer servantOccurr = occurrences(Resource.SERVANT, resourcesToTake);
        Integer shieldOccurr = occurrences(Resource.SHIELD, resourcesToTake);

    if(this.checkAvailability(resourcesToTake)) {

            for(Integer storage : warehouse.keySet()){

                if(warehouse.get(storage).getResources().contains(Resource.COIN))
                    for(int i = 0; i < coinOccurr; i++)
                        warehouse.get(storage).getResources().remove(Resource.COIN);

                if(warehouse.get(storage).getResources().contains(Resource.STONE))
                    for(int i = 0; i < stoneOccurr; i++)
                        warehouse.get(storage).getResources().remove(Resource.STONE);

                if(warehouse.get(storage).getResources().contains(Resource.SHIELD))
                    for(int i = 0; i < shieldOccurr; i++)
                        warehouse.get(storage).getResources().remove(Resource.SHIELD);

                if(warehouse.get(storage).getResources().contains(Resource.SERVANT))
                    for(int i = 0; i < servantOccurr; i++)
                        warehouse.get(storage).getResources().remove(Resource.SERVANT);

            }
        }
    }

    /**
     * @param resourcesToTake List of resources needed
     * @return true if resources are present in warehouse, false otherwise
     */
    @Override
    public boolean checkAvailability(List<Resource> resourcesToTake) {

        List<Resource> totalResources = new ArrayList<>();

        for(Integer storage : warehouse.keySet()){
            totalResources.addAll(warehouse.get(storage).getResources());
        }

        if(totalResources.containsAll(resourcesToTake) ||  totalResources.equals(resourcesToTake))
            return true;
        else return false;
    }

    /**
     * @return total resources in the warehouse
     */
    @Override
    public List<Resource> getTotalResources(){
        List<Resource> totalResources = new ArrayList<>();

        for(Integer storage : warehouse.keySet()){
            totalResources.addAll(warehouse.get(storage).getResources());
        }

        return totalResources;
    }

    /**
     *
     * @param storageNum
     * @return the list of resources contained in selected storage number
     */
    public List<Resource> getWarehouseStorage(Integer storageNum) {
        if(!warehouse.containsKey(storageNum)) throw new IndexOutOfBoundsException();
        return warehouse.get(storageNum).getResources();
    }

    /**
     * @param resource
     * @param resources
     * @return the occurrences of resource in resources list
     */
    @Override
    public Integer occurrences(Resource resource, List<Resource> resources){
        return Math.toIntExact(resources.stream().filter(x -> x.equals(resource)).count());
    }

}
