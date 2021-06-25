package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PersonalBoard {

    private Warehouse warehouse;
    private Coffer coffer;
    private DevCardSlots devCardSlots;
    private ArrayList<LeaderCard> leaderCards;

    public PersonalBoard(){
        warehouse = new Warehouse();
        coffer = new Coffer();
        devCardSlots = new DevCardSlots();
        leaderCards = new ArrayList<>(0);
    }

    /**
     * @return total resources of warehouse
     */
    public List<Resource> getWarehouseResource() {
        return warehouse.getTotalResources().stream().collect(Collectors.toList());
    }

    /**
     * @return a copy of the inner data of Warehouse object. Needed to serialize Warehouse in json
     */
    public HashMap<Integer, Storage> getClonedWarehouse(){
        return warehouse.getClonedWarehouse();
    }

    /**
     * total resources in coffer
     * @return
     */
    public List<Resource> getCofferResource() {
        return coffer.getTotalResources().stream().collect(Collectors.toList());
    }

    /**
     * @return a copy of the inner data of Coffer object. Needed to serialize coffer in json
     */
    public HashMap<Resource, Integer> getClonedCoffer(){
        return coffer.getClonedCoffer();
    }

    /**
     * @return total resources of warehouse and coffer
     */
    public List<Resource> getTotalResource(){
        List<Resource> totalResource = this.getWarehouseResource();
        totalResource.addAll(this.getCofferResource());

        return totalResource;
    }

    /**
     * takes specified resources from coffer
     * @param toTake
     * @throws InsufficientResourcesException if specified resources are not available inside warehouse
     */
    public void pullCofferResource(List<Resource> toTake) throws InsufficientResourcesException {
        this.coffer.pullResource(toTake);
    }

    /**
     * takes specified resources from warehouse
     * @param toTake resources to take from warehouse
     */
    public void pullWarehouseResource(List<Resource> toTake){
        this.warehouse.pullResource(toTake);
    }

    /**
     * @param resourcesIn resources to insert in coffer
     */
    public void putCofferResource(List<Resource> resourcesIn){
        coffer.putResource(resourcesIn);
    }

    /**
     * @param destStorage storage in which to insert specified resources
     * @param resourceIn resources to insert in warehouse
     * @throws StorageOutOfBoundsException if selected storage doesn't exists
     * @throws IllegalInsertionException if the insertion doesn't satisfy warehouse's integrity rules
     */
    public void putWarehouseResource(Integer destStorage, List<Resource> resourceIn) throws StorageOutOfBoundsException,
            IllegalInsertionException{
        warehouse.putResource(destStorage, resourceIn);
    }

    /**
     *
     * @param slotNumber slot where adding the new development card
     * @param developmentCard
     * @throws IllegalInsertionException if the insertion doesn't satisfy development cards slots integrity rules
     * @throws IndexOutOfBoundsException if specified slotNumber is not an index of development cards slot
     */
    public void addCardInDevCardSlot(int slotNumber, DevelopmentCard developmentCard) throws IllegalInsertionException, IndexOutOfBoundsException{
        devCardSlots.addCard(slotNumber, developmentCard);
    }

    /**
     * @param devCardSlotNumber the slot where to activate production
     * @return the list of resources needed to activate producion in specified development card slots number
     * @throws EmptySlotException if there is not a development card inside the slot
     * @throws IndexOutOfBoundsException if specified devCardSlotNumber doesn't exists
     */
    public List<Resource> devCardSlotProductionIn(Integer devCardSlotNumber) throws EmptySlotException, IndexOutOfBoundsException{
        HashMap<Resource, Integer> productionIn = devCardSlots.resourcesProductionIn(devCardSlotNumber);
        List<Resource> result = new ArrayList<>();
        for(Resource resource : productionIn.keySet()){
            Integer cost = productionIn.get(resource);
            for(int i = 0; i < cost; i++){
                result.add(resource);
            }
        }
        return result;
    }

    /**
     * @param devCardSlotNumber specified development card slot in which to activate production
     * @return the list of resources given by a production activated on selected devCard slot
     */
    public List<Resource> devCardSlotProductionOut(Integer devCardSlotNumber) {
        Map<Resource, Integer> productionOut = devCardSlots.resourcesProductionOut(devCardSlotNumber);
        List<Resource> result = new ArrayList<>();
        for(Resource resource : productionOut.keySet()){
            Integer cost = productionOut.get(resource);
            for(int i = 0; i < cost; i++){
                result.add(resource);
            }
        }
        return result;
    }

    /**
     * @return a List containing a copy of peek cards of Development card Slots
     */
    public HashMap<Integer, DevelopmentCard> getPeekCardsInDevCardSLots(){
        return devCardSlots.peekCards();
    }

    /**
     * @return a List containing a copy of all the development cards contained in Development Card Slot
     */
    public List<DevelopmentCard> getCardsInDevCardSlots(){
        return devCardSlots.getCardsInSlots();
    }

    /**
     * swaps resources between source storage and destination storage
     * @param sourceStorage
     * @param destStorage
     * @throws IllegalMoveException if the move doesn't satisfy warehouse's integrity rules
     * @throws StorageOutOfBoundsException if specified storage doesn't exists
     */
    public void moveWarehouseResource(Integer sourceStorage, Integer destStorage) throws IllegalMoveException, StorageOutOfBoundsException{
        warehouse.moveResource(sourceStorage, destStorage);
    }

    /**
     * @return a copy of leader cards array list
     */
    public List<LeaderCard> getLeaderCards() {
        return List.copyOf(leaderCards);
    }

    public Warehouse getWarehouse (){return warehouse;}

    /**
     * @return total victory points in marketboard
     */
    public Integer getVictoryPoints(){
        List<Resource> totalResources = this.getTotalResource();
        Integer resourcesVictoryPoints = totalResources.size() / 5;
        return resourcesVictoryPoints + devCardSlots.getVictoryPoints();
    }

    /**
     * @return true if the user bought seven development cards, false otherwise.
     * The method is invoked to check wether the game will end or not
     */
    public boolean sevenDevCardsBought(){
        return devCardSlots.sevenDevCardsBought();
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

}
