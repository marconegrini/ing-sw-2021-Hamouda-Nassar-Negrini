package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.exceptions.AlreadyActivatedLeaderCardException;
import it.polimi.ingsw.model.exceptions.AlreadyDiscardedLeaderCardException;
import it.polimi.ingsw.model.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.exceptions.StorageOutOfBoundsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that contains only necessary data structures to save user's current state. There are no integrity rules since structures are
 * updated with model data. At the end of each turn, all Light Model classes associated with users will be updated through Update Messages (ServerMessageType)
 */
public class LightModel{

    private List<LeaderCard> leaderCards;
    private ArrayList<DevelopmentCard> developmentCardsDeck;
    private HashMap<Integer, DevelopmentCard> peekDevCardsInSlot;
    private MarketBoard marketBoard;
    private HashMap<String, Integer> otherPlayersFaithPathPosition;
    private Integer faithPathPosition;
    private List<VaticanSection> vaticanSections;
    private Warehouse warehouse;
    private Coffer coffer;

    public LightModel(){
        leaderCards = new ArrayList<>();
        developmentCardsDeck = new ArrayList<>();
        otherPlayersFaithPathPosition = new HashMap<>();
        faithPathPosition = 0;
    }

    public List<LeaderCard> getLeaderCards() {
        return this.leaderCards;
    }

    public void setLeaderCards(List<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }

    public void chooseLeaderCards(Integer index1, Integer index2){
        LeaderCard lc1 = leaderCards.get(index1);
        LeaderCard lc2 = leaderCards.get(index2);
        leaderCards.clear();
        leaderCards.add(lc1);
        leaderCards.add(lc2);
    }

    public void activateLeaderCard(Integer index){
        try {
            this.leaderCards.get(index).activate();
        } catch (AlreadyActivatedLeaderCardException | AlreadyDiscardedLeaderCardException e){
            System.err.println(e);
        }
    }

    public void discardLeaderCard(Integer index){
        try{
            this.leaderCards.get(index).discard();
        } catch (AlreadyActivatedLeaderCardException | AlreadyDiscardedLeaderCardException e){
            System.err.println(e);
        }
    }

    public ArrayList<DevelopmentCard> getDevelopmentCardsDeck(){
        return this.developmentCardsDeck;
    }

    public void setDevelopmentCardsDeck(ArrayList<DevelopmentCard> developmentCardsDeck){
        this.developmentCardsDeck = developmentCardsDeck;
    }

    public HashMap<Integer, DevelopmentCard> getPeekDevCardsInSlot() {
        return peekDevCardsInSlot;
    }

    public void setPeekDevCardsInSlot(HashMap<Integer, DevelopmentCard> peekDevCardsInSlot) {
        this.peekDevCardsInSlot = peekDevCardsInSlot;
    }

    public MarketBoard getMarketBoard(){
        return this.marketBoard;
    }

    public void setMarketBoard(MarketBoard marketBoard) {
        this.marketBoard = marketBoard;
    }

    public Integer getFaithPathPosition() {
        return faithPathPosition;
    }

    public void setFaithPathPosition(Integer faithPathPosition) {
        this.faithPathPosition = faithPathPosition;
    }

    public HashMap<String, Integer> getOtherPlayersFaithPathPosition() {
        return otherPlayersFaithPathPosition;
    }

    public void setOtherPlayersFaithPathPosition(HashMap<String, Integer> otherPlayersFaithPathPosition) {
        this.otherPlayersFaithPathPosition = otherPlayersFaithPathPosition;
    }

    public void setVaticanSections(List<VaticanSection> vaticanSections){
        this.vaticanSections = vaticanSections;
    }

    public List<VaticanSection> getVaticanSections(){
        return this.vaticanSections;
    }

    public Warehouse getWarehouse(){
        return this.warehouse;
    }

    public void setWarehouse(Warehouse warehouse){
        this.warehouse = warehouse;
    }

    public void moveWarehouseResources(Integer sourceStorage, Integer destStorage){
        try {
            this.warehouse.moveResource(sourceStorage, destStorage);
        } catch(IllegalMoveException | StorageOutOfBoundsException e){
            System.err.println(e);
        }
    }

    public Coffer getCoffer(){
        return coffer;
    }

    public void setCoffer(Coffer coffer){
        this.coffer = coffer;
    }


}
