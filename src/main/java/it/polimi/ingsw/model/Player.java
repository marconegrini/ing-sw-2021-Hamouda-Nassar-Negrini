package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.DiscountLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.ProdPowerLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.StorageLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.WhiteMarbleLeaderCard;
import it.polimi.ingsw.model.enumerations.CardType;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.*;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public abstract class Player {

    protected String nickname;

    protected DataOutputStream toClient;

    protected DataInputStream fromClient;

    protected boolean hasCalamaio;

    protected PersonalBoard personalBoard;

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

    public String getNickname(){
        return this.nickname;
    }


    /**
     * @return total resources in warehouse and coffer
     */
    public List<Resource> getTotalResource() {
        List<Resource> totalResource = personalBoard.getWarehouseResource();
        totalResource.addAll(personalBoard.getCofferResource());

        return totalResource;
    }

    public PersonalBoard getPersonalBoard(){return this.personalBoard;}

    public void setCalamaio() {
        this.hasCalamaio = true;
    }

    /**
     *
     * @return total resources in warehouse
     */
    public List<Resource> getWarehouseResource() {
        return personalBoard.getWarehouseResource();
    }

    /**
     *
     * @return total resources in coffer
     */
    public List<Resource> getCofferResource() {
        return personalBoard.getCofferResource();
    }

    /**
     *
     * @return true if this player has calamaio, false otherwise
     */
    public boolean hasCalamaio (){
        return hasCalamaio;
    }

    /**
     *
     * @param toTake list of resources to take from warehouse
     */
    public void pullWarehouseResources(List<Resource> toTake){
        personalBoard.pullWarehouseResource(toTake);
    }

    /**
     *
     * @param toTake list of resources to take from coffer
     */
    public void pullCofferResources(List<Resource> toTake) {
        personalBoard.pullCofferResource(toTake);
    }

    /**
     *
     * @param slotNumber slot's index to insert development card
     * @param developmentCard
     * @throws IllegalInsertionException if insertion doesn't satisfy integrity rules of development card slots
     * @throws IndexOutOfBoundsException if slotNum is not and index of development card slots
     */
    public void addCardInDevCardSlot(int slotNumber, DevelopmentCard developmentCard) throws IllegalInsertionException, IndexOutOfBoundsException{
        personalBoard.addCardInDevCardSlot(slotNumber, developmentCard);
    }

    /**
     *
     * @param devCardSlotNum index inside development card slot
     * @return the list of resources required to activate production in selected slot
     * @throws EmptySlotException if the slot doesn't contains development cards
     * @throws IndexOutOfBoundsException if slotNum is not and index of development card slots
     */
    public List<Resource> devCardSlotProductionIn(Integer devCardSlotNum) throws EmptySlotException, IndexOutOfBoundsException {
        return personalBoard.devCardSlotProductionIn(devCardSlotNum);
    }

    /**
     *
     * @param devCardSlotNum index inside development card slot
     * @return the list of resources produced by activating production in selected slot
     */
    public List<Resource> devCardSlotProductionOut(Integer devCardSlotNum) {
        return personalBoard.devCardSlotProductionOut(devCardSlotNum);
    }

    /**
     * @param resourcesIn resources to insert in warehouse
     */
    public void putCofferResources(List<Resource> resourcesIn){
        personalBoard.putCofferResource(resourcesIn);
    }

    /**
     *
     * @param destStorage index of the destination storage, is an integer between 1 and 3
     * @param resourceIn list of resources to insert in the selcted storage of warehouse
     * @throws StorageOutOfBoundsException if selected index is not an index of warehouse
     * @throws IllegalInsertionException if insertion doesn't satisfy integrity rules of warehouse
     */
    //destStorage is an integer between 1 and 3
    public void putWarehouseResources(Integer destStorage, List<Resource> resourceIn) throws StorageOutOfBoundsException,
            IllegalInsertionException{
        personalBoard.putWarehouseResource(destStorage, resourceIn);
    }

    /**
     * The method swaps resources in warehouse from the source storage to the destination storage
     * @param sourceStorage
     * @param destStorage
     * @throws IllegalMoveException if the swap doesn't satisfy integrity rules of warehouse
     * @throws StorageOutOfBoundsException if specified index is not an index of warehouse
     */
    public void moveWarehouseResources(Integer sourceStorage, Integer destStorage) throws IllegalMoveException, StorageOutOfBoundsException{
        personalBoard.moveWarehouseResource(sourceStorage, destStorage);
    }

    /**
     * @param indexNumber is the index of the leader card inside leader cards arraylist
     * @throws AlreadyActivatedLeaderCardException
     * @throws AlreadyDiscardedLeaderCardException
     * @throws IndexOutOfBoundsException
     * @throws InsufficientResourcesException
     */
    public void activateLeaderCard(Integer indexNumber) throws AlreadyActivatedLeaderCardException, AlreadyDiscardedLeaderCardException, IndexOutOfBoundsException, InsufficientResourcesException {
        if(indexNumber < 0 || indexNumber > (leaderCards.size()-1)) throw new IndexOutOfBoundsException();
        if(isLeaderCardActivatable(indexNumber))
            this.leaderCards.get(indexNumber).activate();
        else throw new InsufficientResourcesException();
    }

    public boolean isLeaderCardActivatable(Integer indexNumber){
        if(indexNumber < 0 || indexNumber > (leaderCards.size()-1)) throw new IndexOutOfBoundsException();
        CardType cardType = leaderCards.get(indexNumber).getCardType();
        boolean activatable = false;
        switch(cardType) {
            case STORAGE:
                List<Resource> totalResources = new ArrayList<>();
                totalResources.addAll(this.getTotalResource());
                StorageLeaderCard slc = (StorageLeaderCard) leaderCards.get(indexNumber);
                activatable = slc.isActivatable(totalResources);
                break;
            case DISCOUNT:
                List<DevelopmentCard> cardsToActivateDiscount = personalBoard.getCardsInDevCardSlots();
                DiscountLeaderCard dlc = (DiscountLeaderCard) leaderCards.get(indexNumber);
                activatable = dlc.isActivatable(cardsToActivateDiscount);
                break;
            case PRODUCTION:
                List<DevelopmentCard> cardsToActivateProduction = personalBoard.getCardsInDevCardSlots();
                ProdPowerLeaderCard pplc = (ProdPowerLeaderCard) leaderCards.get(indexNumber);
                activatable = pplc.isActivatable(cardsToActivateProduction);
                break;
            case MARBLE:
                List<DevelopmentCard> cardsToActivateMarble = personalBoard.getCardsInDevCardSlots();
                WhiteMarbleLeaderCard wmlc = (WhiteMarbleLeaderCard) leaderCards.get(indexNumber);
                activatable = wmlc.isActivatable(cardsToActivateMarble);
                break;
        }
        return activatable;
    }

    /**
     * @param indexNumber is the index of the leader card inside leadercards arraylist
     * @throws AlreadyActivatedLeaderCardException
     * @throws AlreadyDiscardedLeaderCardException
     * @throws IndexOutOfBoundsException
     */
    public void discardLeaderCard(Integer indexNumber) throws AlreadyActivatedLeaderCardException, AlreadyDiscardedLeaderCardException, IndexOutOfBoundsException{
        if(indexNumber < 0 || indexNumber > (leaderCards.size()-1)) throw new IndexOutOfBoundsException();
        this.leaderCards.get(indexNumber).discard();
    }

    /**
     * This method is called when the user select 2 leader cards out of the first 4 given while setting up the game
     * @param index1
     * @param index2
     * @throws IndexOutOfBoundsException
     */
    public void chooseLeaderCard(Integer index1, Integer index2) throws IndexOutOfBoundsException{
        if(index1 < 0 || index1 > (leaderCards.size()-1)) throw new IndexOutOfBoundsException();
        if(index2 < 0 || index2 > (leaderCards.size()-1)) throw new IndexOutOfBoundsException();
        for(int i = 0; i < leaderCards.size(); i++){
            if(i != index1 || i != index2){
                leaderCards.remove(i);
            }
        }
        System.out.println(leaderCards);
    }

    public void setLeaderCards(List<LeaderCard> leaderCards){
        this.leaderCards = leaderCards;
    }

    public Integer getLeaderCardsVictoryPoint(){
        Integer vp = 0;
        for(LeaderCard lc : leaderCards)
            if(lc.isActivated() && !lc.isDiscarded())
                vp += lc.getVictoryPoints();

            return vp;
    }

    public boolean isLeaderCardActivated(CardType lcType){
        boolean activated = false;
        for(LeaderCard lc : leaderCards){
            if(lc.getCardType().equals(lcType) && lc.isActivated() && !lc.isDiscarded())
                activated = true;
        }
        return activated;
    }

    /**
     * The method returns the power of a leader card. A leader card power is automatically applied in the respective turn if
     * the leader card has been activated. If the user has two leader card of the same type, they will be used
     * both every time there is the need.
     * @param lcType the leader card type
     * @return the resources given or discounted by the leader card power
     */
    public HashMap<Resource, Integer> getLeaderCardsPower(CardType lcType) {
        HashMap<Resource, Integer> resourcesToReturn = new HashMap<>();
        Integer value;
        for(LeaderCard lc : leaderCards) {
            if (lc.isActivated() && lc.getCardType().equals(lcType)) {
                HashMap<Resource, Integer> leaderCardPower = lc.getLeaderCardPower();
                Set<Resource> keyResources = leaderCardPower.keySet();
                for (Resource resource : keyResources) {
                    if (leaderCardPower.containsKey(resource)) {
                        if (resourcesToReturn.containsKey(resource)) {
                            value = resourcesToReturn.get(resource) + leaderCardPower.get(resource);
                            resourcesToReturn.put(resource, value);
                        } else resourcesToReturn.put(resource, leaderCardPower.get(resource));
                    }
                }
            }
        }
        return resourcesToReturn;
    }
}
