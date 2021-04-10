package it.polimi.ingsw.model;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.*;

import java.util.*;

public class Warehouse implements Deposit{
    /**
     * 1 - shelf with capacity = 1
     * 2 - shelf with capacity = 2
     * 3 - shelf with capacity = 3
     */
    private HashMap<Integer, ArrayList<Resource>> warehouse;

    public Warehouse(){
        warehouse = new HashMap<>();
        for (int i = 1; i <= 3; i++) {
            warehouse.put(i, new ArrayList<>(0));
            (warehouse.get(i)).size();
        }
    }

    /**
     * @param destStorage: requires an integer between 1 and 3
     * @param resourceIn: requires an ArrayList of the same tipe of resources
     */
    public void putResource(int destStorage, ArrayList<Resource> resourceIn) throws StorageOutOfBoundsException,
            BadInputFormatException, IllegalInsertionException {

        if(destStorage < 1 || destStorage > 3) throw new StorageOutOfBoundsException();

        if(resourceIn.size() > destStorage) throw new BadInputFormatException();

        //checking that resourcesIn contains resources of the same type
        Resource check = resourceIn.get(0);
        for(Resource resource : resourceIn){
            if(!resource.equals(check))
                throw new BadInputFormatException();
        }

        //checking that resource type equal to resourcesIn is not contained in shelves other than destStorage
        for(int i = 1; (i <= 3) && (i != destStorage); i++) {
            if((warehouse.get(i)).size() != 0) {
                if ((warehouse.get(i)).get(0).equals(check))
                    throw new IllegalInsertionException();
            }
        }

        //switch case: the shelf is full
        if((warehouse.get(destStorage)).size() == destStorage)
            throw new IllegalInsertionException();

        //switch case: shelf is filled partially
        if(warehouse.get(destStorage).size() != 0){

            //checking that resource type in destStorage is the same of resourceIn
            if (((warehouse.get(destStorage)).get(0) != check))
                throw new IllegalInsertionException();

            //checking that there is enough capacity left to insert resourceIn
            if((warehouse.get(destStorage).size() + resourceIn.size()) <= destStorage)
                warehouse.get(destStorage).addAll(resourceIn);
            else throw new IllegalInsertionException();

        } else warehouse.get(destStorage).addAll(resourceIn); //switch case: the chosen shelf is empty


    }

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

    @Override
    public void pullResource(HashMap<Resource, Integer> cost){

        if(this.checkAvailability(cost)) {

            Set<Resource> resourcesToPull = cost.keySet();
            Iterator iterator = resourcesToPull.iterator();

            while (iterator.hasNext()) {
                Resource resource = (Resource) iterator.next();
                Integer resourceCost = cost.get(resource);
                for(int i = 1; i <= 3; i++)

                    if(warehouse.get(i).size() != 0)

                        //checking that it is the right shelf
                        if (warehouse.get(i).get(0).equals(resource))

                            for(int j = 0; j < resourceCost; j++)
                                warehouse.get(i).remove(resource);
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

            for(int i = 1; i <= 3; i++)
                if(warehouse.get(i).size() != 0)
                    if (warehouse.get(i).get(0).equals(resource) && warehouse.get(i).size() >= resourceCost)
                        resourcesToCheck.remove(resource);
        }

        if(resourcesToCheck.size() == 0)
            return true;
        else return false;
    }

}
