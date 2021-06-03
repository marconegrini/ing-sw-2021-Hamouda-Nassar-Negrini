package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.*;

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


    public List<Resource> getWarehouseResource() {
        return warehouse.getTotalResources().stream().collect(Collectors.toList());
    }

    /**
     * @return a copy of the inner data of Warehouse object. Needed to serialize Warehouse in json
     */
    public HashMap<Integer, Storage> getClonedWarehouse(){
        return warehouse.getClonedWarehouse();
    }

    public List<Resource> getCofferResource() {
        return coffer.getTotalResources().stream().collect(Collectors.toList());
    }

    /**
     * @return a copy of the inner data of Coffer object. Needed to serialize coffer in json
     */
    public HashMap<Resource, Integer> getClonedCoffer(){
        return coffer.getClonedCoffer();
    }

    public List<Resource> getTotalResource(){
        List<Resource> totalResource = this.getWarehouseResource();
        totalResource.addAll(this.getCofferResource());

        return totalResource;
    }

    public void pullCofferResource(List<Resource> toTake) throws InsufficientResourcesException {
        this.coffer.pullResource(toTake);
    }

    public void pullWarehouseResource(List<Resource> toTake){
        this.warehouse.pullResource(toTake);
    }

    public void addCardInDevCardSlot(int slotNumber, DevelopmentCard developmentCard) throws IllegalInsertionException, IndexOutOfBoundsException{
        devCardSlots.addCard(slotNumber, developmentCard);
    }

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


    public void putCofferResource(List<Resource> resourcesIn){
        coffer.putResource(resourcesIn);
    }

    public void putWarehouseResource(Integer destStorage, List<Resource> resourceIn) throws StorageOutOfBoundsException,
            IllegalInsertionException{
        warehouse.putResource(destStorage, resourceIn);
    }

    public void moveWarehouseResource(Integer sourceStorage, Integer destStorage) throws IllegalMoveException, StorageOutOfBoundsException{
        warehouse.moveResource(sourceStorage, destStorage);
    }

    public List<LeaderCard> getLeaderCards() {
        return List.copyOf(leaderCards);
    }

    public Warehouse getWarehouse (){return warehouse;}

    public Integer getVictoryPoints(){
        List<Resource> totalResources = this.getTotalResource();
        Integer resourcesVictoryPoints = totalResources.size() / 5;
        return resourcesVictoryPoints + devCardSlots.getVictoryPoints();
    }

    public boolean sevenDevCardsBought(){
        return devCardSlots.sevenDevCardsBought();
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }


}
