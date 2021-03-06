package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.DiscountLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.ProdPowerLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.StorageLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.WhiteMarbleLeaderCard;
import it.polimi.ingsw.enumerations.CardType;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.exceptions.*;


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Abstract class that contains method for the player
 */
public abstract class Player {

    private static final Logger logger = Logger.getLogger(Player.class.getName());
    protected String nickname;

    protected boolean hasCalamaio;

    protected PersonalBoard personalBoard;

    protected List<LeaderCard> leaderCards;

    protected FaithPath userFaithPath;

    public abstract void incrementFaithPathPosition();

    public abstract Integer getFaithPathPosition();

    public abstract void updateFaithPath(Integer newPlayingUserPos);

    public boolean isRapportoInVaticano(Integer newUserPos){
        return userFaithPath.isRapportoInVaticano(newUserPos);
    }

    public List<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public String getNickname(){
        return this.nickname;
    }

    /**
     * @return total resources in warehouse and coffer and resources in storage leader card
     */
    public List<Resource> getTotalResource() {
        List<Resource> totalResource = personalBoard.getWarehouseResource();
        totalResource.addAll(personalBoard.getCofferResource());
        //adding stored resources in the storages of Storage card storage
        for(LeaderCard ld: leaderCards){
            if (ld.getCardType().equals(CardType.STORAGE))
                if (ld.isActivated()) {
                 StorageLeaderCard sld = (StorageLeaderCard) ld;
                    if (sld.getOccupiedSlots()>0){
                        totalResource.addAll(sld.getStoredResources());
                    }
                }
        }
        return totalResource.stream().collect(Collectors.toList());
    }

    /**
     * @return the end position of faith path
     */
    public Integer faithPathEnd(){
        return userFaithPath.getEnd();
    }

    /**
     *
     * @return total resources in warehouse
     */
    public List<Resource> getWarehouseResource() {
        return personalBoard.getWarehouseResource();
    }

    /**
     * @return a cloned warehouse to update client's warehouse in light model
     */
    public HashMap<Integer, Storage> getClonedWarehouse(){
        return personalBoard.getClonedWarehouse();
    }

    /**
     *
     * @return total resources in coffer
     */
    public List<Resource> getCofferResource() {
        return personalBoard.getCofferResource();
    }

    /**
     * @return a cloned coffer to update client's coffer in light model
     */
    public HashMap<Resource, Integer> getClonedCoffer(){
        return personalBoard.getClonedCoffer();
    }

    /**
     * @return the arraylist containing vatican sections. Needed to update client's faith path in light model
     */
    public List<VaticanSection> getVaticanSections(){
        return userFaithPath.getVaticanSections();
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
        if(lcType.equals(CardType.MARBLE) && resourcesToReturn.size()==2) {
            Random r = new Random();
            Integer randomIndex = r.nextInt(2);
            resourcesToReturn.remove(randomIndex);
        }
        return resourcesToReturn;
    }

    /**
     *
     * @return true if this player has calamaio, false otherwise
     */
    public boolean hasCalamaio (){
        return hasCalamaio;
    }

    /**
     * sets the calamaio true to this
     */
    public void setCalamaio() {
        this.hasCalamaio = true;
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
    public void pullCofferResources(List<Resource> toTake)  {
        try {
            personalBoard.pullCofferResource(toTake);
        }catch (InsufficientResourcesException e){
            System.err.println("InsufficientResourcesException");
            e.printStackTrace();
            System.exit(-3);
        }
    }

    /**
     *
     * @param slotNumber slot's index to insert development card
     * @param developmentCard card to insert in slot
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
     * @return peek cards in development card slots
     */
    public HashMap<Integer, DevelopmentCard> getPeekCardsInDevCardSLots(){
        return personalBoard.getPeekCardsInDevCardSLots();
    }

    /**
     * @param resourcesIn resources to insert in warehouse
     */
    public void putCofferResources(List<Resource> resourcesIn){
        personalBoard.putCofferResource(resourcesIn);
    }

    /**
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

    /**
     * @param indexNumber is the index representing a leader card inside leader cards list (0 or 1, since they are 2)
     * @return true if leader card is activatable given available resources in warehouse/coffer/development cards slot,
     * false otherwise
     */
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
     * @param indexNumber is the index (0 or 1) of the leader card inside leader cards arraylist
     * @throws AlreadyActivatedLeaderCardException
     * @throws AlreadyDiscardedLeaderCardException
     * @throws IndexOutOfBoundsException
     */
    public void discardLeaderCard(Integer indexNumber) throws AlreadyActivatedLeaderCardException, AlreadyDiscardedLeaderCardException, IndexOutOfBoundsException{
        if(indexNumber < 0 || indexNumber > (leaderCards.size()-1)) throw new IndexOutOfBoundsException();
        this.leaderCards.get(indexNumber).discard();
    }

    /**
     * This method is called when the user selects 2 leader cards out of the first 4 given while setting up the game
     * @param index1
     * @param index2
     * @throws IndexOutOfBoundsException
     */
    public void chooseLeaderCard(Integer index1, Integer index2) throws IndexOutOfBoundsException{
        if(index1 < 0 || index1 > (leaderCards.size()-1)) throw new IndexOutOfBoundsException();
        if(index2 < 0 || index2 > (leaderCards.size()-1)) throw new IndexOutOfBoundsException();
        LeaderCard lc1 = leaderCards.get(index1);
        LeaderCard lc2 = leaderCards.get(index2);
        leaderCards.clear();
        leaderCards.add(lc1);
        leaderCards.add(lc2);
        logger.log(Level.INFO,leaderCards.toString());
    }

    public void setLeaderCards(List<LeaderCard> leaderCards){
        this.leaderCards = leaderCards;
    }

    /**
     * @return leader card's victory points
     */
    public Integer getLeaderCardsVictoryPoint(){
        Integer vp = 0;
        for(LeaderCard lc : leaderCards)
            if(lc.isActivated() && !lc.isDiscarded())
                vp += lc.getVictoryPoints();

            return vp;
    }

    /**
     * @return total victory points of the player
     */
    public Integer getTotalVictoryPoints(){
        return personalBoard.getVictoryPoints() + getLeaderCardsVictoryPoint() + userFaithPath.getVictoryPoints();
    }

    /**
     * @return true if the user bought seven development cards, false otherwise. The method is called to check
     * wether the game will end or not.
     */
    public boolean sevenDevCardBought(){
        return personalBoard.sevenDevCardsBought();
    }

    /**
     * returns true if there is a leader card of the specified type activated, false otherwise.
     * @param lcType
     * @return
     */
    public boolean isLeaderCardActivated(CardType lcType){
        boolean activated = false;
        for(LeaderCard lc : leaderCards){
            if(lc.getCardType().equals(lcType) && lc.isActivated() && !lc.isDiscarded())
                activated = true;
        }
        return activated;
    }

}
