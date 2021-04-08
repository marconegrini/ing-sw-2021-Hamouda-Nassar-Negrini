package it.polimi.ingsw.model;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Warehouse implements Deposit{
    /**
     * 1 - scaffale con capacità 1
     * 2 - scaffale con capacità 2
     * 3 - scaffale con capacità 3
     */
    private HashMap<Integer, ArrayList<Resource>> warehouse;

    public Warehouse(){
        warehouse = new HashMap<>();
        for (int i = 1; i <= 3; i++) {
            warehouse.put(i, new ArrayList<>(0));
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

        //verifico che l'arraylist contenga risorse dello stesso tipo
        Resource check = resourceIn.get(0);
        for(Resource resource : resourceIn){
            if(!resource.equals(check))
                throw new BadInputFormatException();
        }

        //verifico che non siano presenti in scaffali diversi da destStorage risorse del tipo che voglio inserire in destStorage
        for(int i = 1; (i <= 3) && (i != destStorage); i++){
            if(warehouse.get(i).get(0).equals(check))
                throw new IllegalInsertionException();
        }

        //verifico che il tipo di risorse presenti in destStorage sia lo stesso di resourceIn
        if((warehouse.get(destStorage).get(0) != check) && (warehouse.get(destStorage).size() != 0))
            throw new IllegalInsertionException();

        //caso in cui lo scaffale è totalmente vuoto
        if(warehouse.get(destStorage).size() == 0)
            warehouse.get(destStorage).addAll(resourceIn);

        //caso in cui lo scaffale è pieno
        if(warehouse.get(destStorage).size() == destStorage)
            throw new IllegalInsertionException();

        //caso in cui lo scaffale è riempito parzialmente
        if((warehouse.get(destStorage).size() + resourceIn.size()) <= destStorage){
            warehouse.get(destStorage).addAll(resourceIn);
        } else throw new IllegalInsertionException();

    }

    public void moveResource(int sourceStorage, int destStorage) throws IllegalMoveException, StorageOutOfBoundsException {
        if(sourceStorage < 1 || sourceStorage > 3) throw new StorageOutOfBoundsException();
        if(destStorage < 1 || destStorage > 3) throw new StorageOutOfBoundsException();
        if((warehouse.get(sourceStorage).size() <= destStorage) && (warehouse.get(destStorage).size() <= sourceStorage)){
            /*
            int tempIndex = 0;
            if(destStorage > sourceStorage)
                tempIndex = sourceStorage;
            else tempIndex = destStorage;

            for(int i = 0; i < tempIndex; i++) {
                Resource tempSource = warehouse.get(destStorage).get(i);
            }


            ArrayList<Resource> temp = new ArrayList<>();

            for(Resource resource : warehouse.get(destStorage)){
                temp.add(resource);
            }

            for(Resource resource : warehouse.get(destStorage)){
                warehouse.get(destStorage).remove(resource);
            }

            for(Resource resource : warehouse.get(sourceStorage)){
                warehouse.get(destStorage).add(resource);
            }

            for(Resource resource : warehouse.get(sourceStorage)){
                warehouse.get(sourceStorage).remove(resource);
            }

            for(Resource resource : temp){
                warehouse.get(sourceStorage).add(resource);
            }
*/
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

    }

    @Override
    public boolean checkAvailability(HashMap<Resource, Integer> cost) {

        return false;
    }
}
