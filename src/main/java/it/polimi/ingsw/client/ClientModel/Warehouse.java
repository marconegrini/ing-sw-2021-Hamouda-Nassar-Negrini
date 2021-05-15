//package it.polimi.ingsw.client.ClientModel;
//
//
//
//import it.polimi.ingsw.client.ClientModel.enumerations.Resource;
//import it.polimi.ingsw.client.ClientModel.parser.WarehouseParser;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class Warehouse implements Deposit {
//
//
//    private Map<Integer, Storage> warehouse;
//
//    public Warehouse(){
//
//        warehouse = new HashMap<>();
//
//        Map<Integer, Integer> storageNumAndCapacity = new HashMap<>();
//
//        WarehouseParser parser = new WarehouseParser("src/main/java/it/polimi/ingsw/client/ClientModel/jsonFiles/CardSlotsWarehouse.json");
//
//        storageNumAndCapacity = parser.getStorageNumAndCapacity();
//
//        parser.close();
//
//        for(Integer storageNum : storageNumAndCapacity.keySet()){
//
//            Integer storageCapacity = storageNumAndCapacity.get(storageNum);
//
//            List<Resource> resources = new ArrayList<>(0);
//            warehouse.put(storageNum, new Storage(storageCapacity, resources));
//        }
//    }
//
//    /**
//     *
//     *  @param destStorage: requires an integer between 1 and 3
//     *  @param resourceIn: requires an ArrayList of the same type of resources
//     */
//    public void putResource(int destStorage, List<Resource> resourceIn) {
//
//        warehouse.get(destStorage).insert(resourceIn);
//
//    }
//
//    /**
//     *
//     * @param sourceStorage integer between 1 and 3
//     * @param destStorage integer between 1 and 3
//     */
//    public void moveResource(int sourceStorage, int destStorage) {
//
//        ArrayList<Resource> temp1 = new ArrayList<>(warehouse.get(destStorage).getResources());
//        ArrayList<Resource> temp2 = new ArrayList<>(warehouse.get(sourceStorage).getResources());
//
//        warehouse.get(sourceStorage).getResources().clear();
//        warehouse.get(sourceStorage).insert(temp1);
//        warehouse.get(destStorage).getResources().clear();
//        warehouse.get(destStorage).insert(temp2);
//
//        temp1.clear();
//        temp2.clear();
//
//    }
//
//    /**
//     * Takes resources from warehouse
//     * @param resourcesToTake
//     */
//
//    public void pullResource(List<Resource> resourcesToTake) {
//
////        Integer coinOccurr = occurrences(Resource.COIN, resourcesToTake);
////        Integer stoneOccurr = occurrences(Resource.STONE, resourcesToTake);
////        Integer servantOccurr = occurrences(Resource.SERVANT, resourcesToTake);
////        Integer shieldOccurr = occurrences(Resource.SHIELD, resourcesToTake);
////
////        if(this.checkAvailability(resourcesToTake)) {
////
////            for(Integer storage : warehouse.keySet()){
////
////                if(warehouse.get(storage).getResources().contains(Resource.COIN))
////                    for(int i = 0; i < coinOccurr; i++)
////                        warehouse.get(storage).getResources().remove(Resource.COIN);
////
////                if(warehouse.get(storage).getResources().contains(Resource.STONE))
////                    for(int i = 0; i < stoneOccurr; i++)
////                        warehouse.get(storage).getResources().remove(Resource.STONE);
////
////                if(warehouse.get(storage).getResources().contains(Resource.SHIELD))
////                    for(int i = 0; i < shieldOccurr; i++)
////                        warehouse.get(storage).getResources().remove(Resource.SHIELD);
////
////                if(warehouse.get(storage).getResources().contains(Resource.SERVANT))
////                    for(int i = 0; i < servantOccurr; i++)
////                        warehouse.get(storage).getResources().remove(Resource.SERVANT);
////
////            }
////        }
//
//    }
//
//
//
//    /**
//     * @param resourcesToTake List of resources needed
//     * @return true if resources are present, false otherwise
//     */
//
//    public boolean checkAvailability(List<Resource> resourcesToTake) {
//
//        List<Resource> totalResources = new ArrayList<>();
//
//        for(Integer storage : warehouse.keySet()){
//            totalResources.addAll(warehouse.get(storage).getResources());
//        }
//
//        if(totalResources.containsAll(resourcesToTake) ||  totalResources.equals(resourcesToTake))
//            return true;
//        else return false;
//    }
//
//    /**
//     * @return total resources in the warehouse
//     */
//    @Override
//    public List<Resource> getTotalResources(){
//        List<Resource> totalResources = new ArrayList<>();
//
//        for(Integer storage : warehouse.keySet()){
//            totalResources.addAll(warehouse.get(storage).getResources());
//        }
//
//        return totalResources;
//    }
//
//    public List<it.polimi.ingsw.client.ClientModel.enumerations.Resource> getWarehouseStorage(Integer storageNum) {
//        return warehouse.get(storageNum).getResources();
//    }
//
//
//    @Override
//    public Integer occurrences(Resource resource, List<Resource> resources){
//        return Math.toIntExact(resources.stream().filter(x -> x.equals(resource)).count());
//    }
//
//}
