package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.messages.fromServer.ResourcesFromMarketMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.update.UpdateLeaderCardsMessage;
import it.polimi.ingsw.messages.fromServer.update.UpdateMarketboardMessage;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.messages.fromServer.OkMessage;
import it.polimi.ingsw.messages.fromServer.ErrorMessage;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.enumerations.CardType;
import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TurnManager {

    private boolean multiplayer;

    private List<MultiPlayer> players;

    private CardsDeck cardsDeck;
    private MarketBoard marketBoard;

    private List<Resource> resorucesToStore;

    private boolean done = false;

    private Integer accesses = 0;

    /**
     * This constructor will be used when a game is restored. It allows
     * you to restore the turn class with old decks and market.
     *
     * @param cardsDeck  the decks is the old deck of a restored game
     * @param marketBoard  the market is the old market of a restored game
     */
    public TurnManager(CardsDeck cardsDeck, MarketBoard marketBoard){
        this.cardsDeck = cardsDeck;
        this.marketBoard = marketBoard;
        this.resorucesToStore = new ArrayList<>();
    }

    public void setMultiplayer(boolean isMultiplayer){
        this.multiplayer = isMultiplayer;
    }

    public void setPlayers(List<MultiPlayer> players){
        if(this.multiplayer)
            this.players = players;
    }

    /**
     * pick resources from the market and add them to the player
     * @param player  The player is who will receive the picked resources
     * @return OkMessage if marble inserted correctly, ErrorMessage if selected row or column doesn't exists
     */
    public ServerMessage pickResources(Player player, boolean isRow, int rowOrColNum) {
        List<Marble> pickedMarbles;
        try {
            pickedMarbles = marketBoard.insertMarble(isRow, rowOrColNum);
        } catch (IndexOutOfBoundsException e){
            return new ErrorMessage("Selected row or column doesn't exists");
        }
        List<Resource> resourcesToStore = new ArrayList<>();
        for(Marble marble : pickedMarbles){
            Color marbleColor = marble.getColor();
            switch(marbleColor){
                case YELLOW:
                    resourcesToStore.add(Resource.COIN);
                    break;
                case RED:
                    player.incrementFaithPathPosition();
                    if(multiplayer){
                        Integer newUserPos = player.getFaithPathPosition();
                        for(Player p : players)
                            p.updateFaithPath(newUserPos);
                    }
                    break;
                case VIOLET:
                    resourcesToStore.add(Resource.SERVANT);
                    break;
                case WHITE:
                    if(player.isLeaderCardActivated(CardType.MARBLE)){
                        HashMap<Resource, Integer> resourcesFromLeaderCard = null;
                        resourcesFromLeaderCard = player.getLeaderCardsPower(CardType.MARBLE);
                        Set<Resource> resourcesFromHashMap = resourcesFromLeaderCard.keySet();
                        for(Resource resource : resourcesFromHashMap){
                            for(int i = 0; i < resourcesFromLeaderCard.get(resource); i++){
                                resourcesToStore.add(resource);
                            }
                        }
                    }
                    break;
                case GREY:
                    resourcesToStore.add(Resource.STONE);
                    break;
                case BLUE:
                    resourcesToStore.add(Resource.SHIELD);
                    break;
            }
        }
        return new ResourcesFromMarketMessage(resourcesToStore);
    }

    //TODO method that returns resources taken from market
    public List<Resource> getResourcesTakenFromMarket(){
        return this.resorucesToStore;
    }


    /**
     * @param player player who performed pickresources choice
     * @param destStorage destination storage in warehouse
     * @param resourcesIn List of resources to insert
     * @return OkMessage if everything worked fine, ErrorMessage instead
     */
    public ServerMessage insertResourcesInWarehouse(Player player, Integer destStorage, List<Resource> resourcesIn){
        try {
            player.putWarehouseResources(destStorage, resourcesIn);
        } catch (StorageOutOfBoundsException e1){
            return new ErrorMessage("Selected storage doesn't exists");
        } catch (IllegalInsertionException e2){
            return new ErrorMessage("Insertion not permitted");
        }
        return new OkMessage("Resources correctly inserted");
    }

    public ServerMessage moveResourcesInWarehouse(Player player, Integer sourceStorage, Integer destStorage){
        try{
            player.moveWarehouseResources(sourceStorage, destStorage);
        } catch (IllegalMoveException e1){
            return new ErrorMessage("Warehouse move not permitted");
        } catch (StorageOutOfBoundsException e2){
            return new ErrorMessage("Selected storage doesn't exists");
        }
        return new OkMessage("Warehouse resources moved");
    }

    /**
     * @param player playing player
     * @param row row inddex of development cards deck
     * @param column column index of development cards deck
     * @param devCardSlot slot index of development cards slots
     * @return OkMessage if everything worked fine, ErrorMessage instead
     */
    public ServerMessage buyDevelopmentCard (Player player, Integer row, Integer column, Integer devCardSlot)  {

        List<Resource> playerResources = player.getTotalResource();
        List<Resource> devCardCost = cardsDeck.developmentCardCost(row, column);

        if(player.isLeaderCardActivated(CardType.DISCOUNT)){
            HashMap<Resource, Integer> resourcesFromLeaderCard = null;
            resourcesFromLeaderCard = player.getLeaderCardsPower(CardType.DISCOUNT);
            Set<Resource> discountedResource = resourcesFromLeaderCard.keySet();
            for(Resource resource : discountedResource){
                if(devCardCost.contains(resource))
                    for(int i = 0; i < resourcesFromLeaderCard.get(resource); i++)
                        discountedResource.remove(resource);
            }
        }

        if (playerResources.equals(devCardCost) || playerResources.containsAll(devCardCost)) {
            List<Resource> toTakeFromCoffer = player.getWarehouseResource();
            List<Resource> toTakeFromWarehouse = new ArrayList<>();

            for (Resource resource : devCardCost)
                if (toTakeFromCoffer.contains(resource)) {
                    toTakeFromCoffer.remove(resource);
                    toTakeFromWarehouse.add(resource);
                }
            player.pullWarehouseResources(toTakeFromWarehouse);
            player.pullCofferResources(toTakeFromCoffer);
            DevelopmentCard devCard = cardsDeck.popCard(row, column);

            try{
                player.addCardInDevCardSlot(devCardSlot, devCard);
            } catch(IllegalInsertionException e1){
                return new ErrorMessage("Slot insertion not allowed");
            } catch (IndexOutOfBoundsException e2){
                return new ErrorMessage("Invalid slot number");
            }
            return new OkMessage("Bought development card and inserted in slot number " + devCardSlot);
        } else return new ErrorMessage("Insufficient resources to buy selected development card");
    }

    /**
     * @param player playing player
     * @param slots List of integers between 0 and 2: they are the indexes of development card slots
     * @param leaderResource is chosen by the user as a result of the activation of the production power leader card.
     *                       The chosen resource will be added to the production output, together with a faith point.
     * @return outcome message encoded as Message Object
     */
    public ServerMessage activateProduction (Player player, List<Integer> slots,List<Resource> leaderResource) throws InsufficientResourcesException {

        if (slots.size() > 3) return new ErrorMessage("Selected more than 3 slots");
        List<Resource> productionInCost = new ArrayList<>();
        //collects the resources needed to activate production in selected slots/slots
        for (Integer slotNum : slots) {
            try {
                productionInCost.addAll(player.devCardSlotProductionIn(slotNum));
            } catch (EmptySlotException e1) {
                return new ErrorMessage("Selected an empty slot");
            } catch (IndexOutOfBoundsException e2) {
                return new ErrorMessage("Selected invalid slot number");
            }
        }
        List<Resource> playerResources = player.getTotalResource();
        //checks if resources needed for production are satisfied by warehouse and/or coffer
        if (playerResources.equals(productionInCost) || playerResources.containsAll(productionInCost)) {
            List<Resource> toTakeFromCoffer = player.getWarehouseResource();
            List<Resource> toTakeFromWarehouse = new ArrayList<>();
            for (Resource resource : productionInCost)
                if (toTakeFromCoffer.contains(resource)) {
                    toTakeFromCoffer.remove(resource);
                    toTakeFromWarehouse.add(resource);
                }
            //takes resources from warehouse, then from coffer
            player.pullWarehouseResources(toTakeFromWarehouse);
            player.pullCofferResources(toTakeFromCoffer);
            List<Resource> resourcesProductionOut = new ArrayList<>();
            for (Integer slotNum : slots)
                resourcesProductionOut.addAll(player.devCardSlotProductionOut(slotNum));
            //increment user's faith path position and updates other users (if multiplayer) if Resource.FAITH is/are present
            for(Resource resource : resourcesProductionOut){
                if(resource.equals(Resource.FAITH)){
                    player.incrementFaithPathPosition();
                    if(multiplayer) {
                        for (Player p : players)
                            p.updateFaithPath(player.getFaithPathPosition());
                    }
                }
            }
            //removes faith resources
            while(!resourcesProductionOut.contains(Resource.FAITH))
                resourcesProductionOut.remove(Resource.FAITH);
            //adds production out resources in coffer
            player.putCofferResources(resourcesProductionOut);
            //checks if production power leader card is activated
            boolean usedLC = this.activateLeaderCardProduction(player, leaderResource);

            if(usedLC) return new OkMessage("Activated production and resources inserted in coffer. Leader card power used.");
            else return new OkMessage("Activated production and resources inserted in coffer");
        } else return new ErrorMessage("Insufficient resources to activate production on selected slots");
    }

    /**
     * @param player the playing player that chose to activate the basic personal board's production
     * @param prodIn1 the first resource needed to activate production
     * @param prodIn2 the second resource needed to activate production
     * @param prodOut production result
     * @param leaderResource resource selected if a production power leader card is activated
     * @return
     */
    public ServerMessage activatePersonalProduction(Player player, Resource prodIn1, Resource prodIn2, Resource prodOut, List<Resource> leaderResource) {
        List<Resource> productionCost = new ArrayList();
        productionCost.add(prodIn1);
        productionCost.add(prodIn2);
        if (player.getTotalResource().equals(productionCost) || player.getTotalResource().containsAll(productionCost)) {
            List<Resource> fromCoffer = player.getWarehouseResource();
            List<Resource> fromWarehouse = new ArrayList<>();
            for (Resource r : productionCost)
                if (fromCoffer.contains(r)) {
                    fromCoffer.remove(r);
                    fromWarehouse.add(r);
                }
            //takes resources from warehouse, then from coffer
            player.pullWarehouseResources(fromWarehouse);
            player.pullCofferResources(fromCoffer);

            boolean usedLC = this.activateLeaderCardProduction(player, leaderResource);
            if(usedLC) return new OkMessage("Activated personal production and resources inserted in coffer. Leader card power used.");
            else return new OkMessage("Activated personal production and resources inserted in coffer");
        } else return new ErrorMessage("Insufficient resources to activate personal production");
    }

    /**
     * @param player the playing player
     * @param leaderResource is != null if a leader card is activated and the user selected the resource to activate production
     * @return The following method returns true if at least 1 production power leader card is activated and inside warehouse
     *      * or coffer there are sufficient resources to activate leader card production. In this case, the production is
     *      * activated, inserting the selected leaderResource inside warehouse, incrementing user's faith path and updating
     *      * other users' faith paths. Else, it returns false.
     */
    public boolean activateLeaderCardProduction(Player player, List<Resource> leaderResource) {
        if(player.isLeaderCardActivated(CardType.PRODUCTION)){
            HashMap<Resource, Integer> prodInCost = null;
            prodInCost = player.getLeaderCardsPower(CardType.PRODUCTION);
            List<Resource> pic = new ArrayList();
            for(Resource r : prodInCost.keySet()){
                for(int i = 0; i < prodInCost.get(r); i++){
                    pic.add(r);
                }
            }
            //checks if there are enough resources in warehouse and/or coffer to activate leader card production
            if (player.getTotalResource().equals(pic) || player.getTotalResource().containsAll(pic)) {
                List<Resource> fromCoffer = player.getWarehouseResource();
                List<Resource> fromWarehouse = new ArrayList<>();
                for (Resource r : pic)
                    if (fromCoffer.contains(r)) {
                        fromCoffer.remove(r);
                        fromWarehouse.add(r);
                    }
                //takes resources from warehouse, then from coffer
                player.pullWarehouseResources(fromWarehouse);
                player.pullCofferResources(fromCoffer);
                List<Resource> clientChoice = new ArrayList<>();
                clientChoice.addAll(leaderResource);
                //inserts in coffer chosen resource
                player.putCofferResources(clientChoice);
                //increments user faith position and updates other user's faith paths
                player.incrementFaithPathPosition();
                for(Player p : players)
                    p.updateFaithPath(player.getFaithPathPosition());
                return true;
            }
        }
        return false;
    }

    /**
     * activates leader card of specified index
     * @param player playing player
     * @param indexNumber leader card's index number in player's arraylist leaderCards
     * @return ErrorMessage if it is not possible to activate selected leader card, OkMessage instead.
     */
    public ServerMessage activateLeaderCard (Player player, Integer indexNumber){
        try{
            player.activateLeaderCard(indexNumber);
        } catch(IndexOutOfBoundsException e1){
            return new ErrorMessage("Selected index for leader card is out of bounds");
        } catch(AlreadyActivatedLeaderCardException e2){
            return new ErrorMessage("Selected leader card already activated");
        } catch(InsufficientResourcesException e3){
            return new ErrorMessage("Insufficient resources to activate selected leader card");
        } catch(AlreadyDiscardedLeaderCardException e4){
            return new ErrorMessage("Selected leader card was discarded");
        }
        return new OkMessage("Selected leader card activated");
    }

    /**
     * Discards selected leader card from leadecards arraylist. OkMessage is returned ad everything worked fine, it also increments user's faith path position.
     * @param player
     * @param indexNum
     * @return
     */
    public ServerMessage discardLeaderCard (Player player, Integer indexNum){
        try{
            player.discardLeaderCard(indexNum);
        } catch (IndexOutOfBoundsException e1){
            return new ErrorMessage("Selected leader card index is out of bounds");
        } catch (AlreadyActivatedLeaderCardException e2){
            return new ErrorMessage("Selected leader card is activated:you cannot discard it");
        } catch (AlreadyDiscardedLeaderCardException e3){
            return new ErrorMessage("Selected leader card was already discarded");
        }
        player.incrementFaithPathPosition();
        return new OkMessage("Leader card correctly discarded: received 1 faith point");
    }

    /**
     * method called to select 2 leader cards out of the 4 given while setting up the game
     * @param player playing player
     * @param index1 first leader card index in arraylist
     * @param index2 second leader card index in arraylist
     * @return ErrorMessage if specified indexes are out of bounds, OkMessage instead.
     */
    public ServerMessage selectLeaderCard(Player player, Integer index1, Integer index2){
        try{
            player.chooseLeaderCard(index1, index2);
        } catch(IndexOutOfBoundsException e){
            return new ErrorMessage("Selected indexes for leader cards are out of bounds");
        }
        return new UpdateLeaderCardsMessage(index1, index2);
    }

    public synchronized void resetDone(){
        this.done = false;
    }

    public synchronized void turnDone(){
        this.done = true;
        notifyAll();
    }

    public synchronized void isDone() {
        while(this.done == false)
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        resetDone();
        return;
    }

    public synchronized void clientDone(){
        accesses++;
        if(accesses.equals(players.size())){
            accesses = 0;
            this.done = true;
            notifyAll();
        }
    }
}


