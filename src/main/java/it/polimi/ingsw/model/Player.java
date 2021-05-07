package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.EmptySlotException;
import it.polimi.ingsw.model.exceptions.IllegalInsertionException;
import it.polimi.ingsw.model.exceptions.UnsufficientResourcesException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.List;

public abstract class Player {

    protected Integer userId;

    protected String nickname;

    protected DataOutputStream toClient;

    protected DataInputStream fromClient;

    protected boolean hasCalamaio;

    protected PersonalBoard personalBoard;

    protected List<LeaderCard> tempLeaderCards;

    protected List<LeaderCard> leaderCards;

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

    public void setLeaderCards(List<LeaderCard> leaderCards){
        this.leaderCards = leaderCards;
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
}
