package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.EmptySlotException;
import it.polimi.ingsw.server.model.exceptions.IllegalInsertionException;
import it.polimi.ingsw.server.model.exceptions.IllegalMoveException;
import it.polimi.ingsw.server.model.exceptions.StorageOutOfBoundsException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.List;

public abstract class Player {

    protected Integer userId;

    protected String nickname;

    protected DataOutputStream toClient;

    protected DataInputStream fromClient;

    protected boolean hasCalamaio;

    protected PersonalBoard personalBoard;

    protected List<LeaderCard> tempLeaderCards;

    protected LeaderCard firstLeaderCard;

    protected LeaderCard secondLeaderCard;

    protected FaithPath userFaithPath;

    public abstract void incrementFaithPathPosition();

    public abstract Integer getFaithPathPosition();

    public abstract void updateFaithPath(Integer newPlayingUserPos);

    public abstract void buyResources();

    public abstract void buyDevelopmentCard();

    public abstract void activateProduction();

    public DataInputStream getFromClient(){
        return this.fromClient;
    }

    public DataOutputStream getToClient() {
        return this.toClient;
    }

    public Integer getUserId(){
        return this.userId;
    }

    public String getNickname(){
        return this.nickname;
    }


    public List<Resource> getTotalResource() {
        List<Resource> totalResource = personalBoard.getWarehouseResource();
        totalResource.addAll(personalBoard.getCofferResource());

        return totalResource;
    }

    public PersonalBoard getPersonalBoard(){return this.personalBoard;}

    public List<Resource> getWarehouseResource() {
        return personalBoard.getWarehouseResource();
    }

    public List<Resource> getCofferResource() {
        return personalBoard.getCofferResource();
    }

    public boolean hasCalamaio (){
        return hasCalamaio;
    }

    public void pullWarehouseResources(List<Resource> toTake){
        personalBoard.pullWarehouseResource(toTake);
    }

    public void pullCofferResources(List<Resource> toTake) {
        personalBoard.pullCofferResource(toTake);
    }

    public void addCardInDevCardSlot(int slotNumber, DevelopmentCard developmentCard) throws IllegalInsertionException, IndexOutOfBoundsException{
        personalBoard.addCardInDevCardSlot(slotNumber, developmentCard);
    }

    public List<Resource> devCardSlotProductionIn(Integer devCardSlotNum) throws EmptySlotException, IndexOutOfBoundsException {
        return personalBoard.devCardSlotProductionIn(devCardSlotNum);
    }

    public List<Resource> devCardSlotProductionOut(Integer devCardSlotNum) {
        return personalBoard.devCardSlotProductionOut(devCardSlotNum);
    }

    public void putCofferResources(List<Resource> resourcesIn){
        personalBoard.putCofferResource(resourcesIn);
    }

    public void putWarehouseResources(Integer destStorage, List<Resource> resourceIn) throws StorageOutOfBoundsException,
            IllegalInsertionException{
        personalBoard.putWarehouseResource(destStorage, resourceIn);
    }

    public void moveWarehouseResources(Integer sourceStorage, Integer destStorage) throws IllegalMoveException, StorageOutOfBoundsException{
        personalBoard.moveWarehouseResource(sourceStorage, destStorage);
    }

}
