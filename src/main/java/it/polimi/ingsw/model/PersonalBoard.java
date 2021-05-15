package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.EmptySlotException;
import it.polimi.ingsw.model.exceptions.IllegalInsertionException;
import it.polimi.ingsw.model.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.exceptions.StorageOutOfBoundsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        return warehouse.getTotalResources();
    }

    public List<Resource> getCofferResource() {
        return coffer.getTotalResources();
    }

    public List<Resource> getTotalResource(){
        List<Resource> totalResource = this.getWarehouseResource();
        totalResource.addAll(this.getCofferResource());

        return totalResource;
    }

    public void pullCofferResource(List<Resource> toTake){
        this.coffer.pullResource(toTake);
    }

    public void pullWarehouseResource(List<Resource> toTake){
        this.warehouse.pullResource(toTake);
    }

    public void addCardInDevCardSlot(int slotNumber, DevelopmentCard developmentCard) throws IllegalInsertionException, IndexOutOfBoundsException{
        devCardSlots.addCard(slotNumber, developmentCard);
    }

    public List<Resource> devCardSlotProductionIn(Integer devCardSlotNumber) throws EmptySlotException, IndexOutOfBoundsException{
        Map<Resource, Integer> productionIn = devCardSlots.resourcesProductionIn(devCardSlotNumber);
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


        public DevCardSlots getDevCardSlots() {
        return devCardSlots;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return (ArrayList<LeaderCard>) leaderCards.clone();
    }

    public Warehouse getWarehouse (){return warehouse;}

    public Integer getVictoryPoints(){
        //TODO to return total victory points
        return 0;
    }

    public List<DevelopmentCard> getCardsInDevCardSlots(){
        return devCardSlots.getCardsInSlots();
    }

}
