package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.messages.fromServer.activateProduction.ProductionResultMessage;
import it.polimi.ingsw.messages.fromServer.leadercard.LeaderResultMessage;
import it.polimi.ingsw.messages.fromServer.storeResources.ErrorWarehouseMessage;
import it.polimi.ingsw.messages.fromServer.storeResources.ResourcesToStoreMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.update.UpdateLeaderCardsMessage;
import it.polimi.ingsw.messages.fromServer.warehouse.MoveResourcesResultMessage;
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
import it.polimi.ingsw.server.handlers.ClientHandler;

import java.util.*;
import java.util.stream.Collectors;

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
        System.out.println(pickedMarbles);
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
                default:
                    break;
            }
        }
        this.resorucesToStore = resourcesToStore;
        System.out.println(resourcesToStore);
        //TODO modifiy return message if no resource is present after the market
        if(resourcesToStore.isEmpty()){
            turnDone();
            return new OkMessage("You don't have resources to store!");
        } else return new ResourcesToStoreMessage(false, resourcesToStore, null, player.getClonedWarehouse());
    }

    //TODO method that returns resources taken from market
    public List<Resource> getResourcesTakenFromMarket(){
        return this.resorucesToStore;
    }


    /**
     * @param player player who performed pick resources choice
     * @param destStorage destination storage in warehouse
     * @param resourcesIn List of resources to insert
     * @return OkMessage if everything worked fine, ErrorMessage instead
     */
    public ServerMessage insertResourcesInWarehouse(Player player, Integer destStorage, List<Resource> resourcesIn, boolean discard) {
        if (!discard) {
            try {
                player.putWarehouseResources(destStorage, resourcesIn);
                if(this.resorucesToStore.equals(resourcesIn)){
                    turnDone();
                    return new ResourcesToStoreMessage(true, null, "Resources correctly inserted!", player.getClonedWarehouse());
                } else {
                    for(Resource res : resourcesIn)
                        this.resorucesToStore.remove(res);
                    //previous line wasn't correct. It removed all the occurrences of specified resource
                    //this.resorucesToStore.removeAll(resourcesIn);
                    System.out.println(this.resorucesToStore.toString());
                    return new ResourcesToStoreMessage(false, this.resorucesToStore, "Insert or discard remaining resources.", player.getClonedWarehouse());
                }
            } catch (StorageOutOfBoundsException e1) {
                return new ErrorWarehouseMessage("Selected slot doesn't exists", this.resorucesToStore);
            } catch (IllegalInsertionException e2) {
                return new ErrorWarehouseMessage("\nInsertion not permitted. Insert again resources in warehouse.\n", this.resorucesToStore);
            }
        } else {
            //if the player chose to discard resources, others faith paths are updated and
            // a check to see if rapporto in vaticano is activated starts. If activated, updates
            //other users' faith paths.
            for (Resource res : resourcesIn) {
                if(multiplayer){
                    for(Player p : players){
                        if(!p.equals(player)){
                            p.incrementFaithPathPosition();
                            Integer newUserPos = p.getFaithPathPosition();
                            if (p.isRapportoInVaticano(newUserPos)) {
                                    for (Player temp : players) {
                                        MultiPlayer mp = (MultiPlayer) temp;
                                        mp.updateFaithPath(newUserPos);
                                    }
                            }
                        }
                    }
                } else {
                    //TODO increment lorenzo position
                }
            }
            if(resorucesToStore.equals(resourcesIn)) {
                turnDone();
                return new ResourcesToStoreMessage(true, null, "Resources correctly discarded!", player.getClonedWarehouse());
            } else {
                this.resorucesToStore.removeAll(resourcesIn);
                return new ResourcesToStoreMessage(false, this.resorucesToStore, "Insert or discard remaining resources.", player.getClonedWarehouse());
            }
        }


    }

    public ServerMessage moveResourcesInWarehouse(Player player, Integer sourceStorage, Integer destStorage){
        try{
            player.moveWarehouseResources(sourceStorage, destStorage);
        } catch (IllegalMoveException e1){
            return new MoveResourcesResultMessage(true, sourceStorage, destStorage, "Warehouse move not permitted");
        } catch (StorageOutOfBoundsException e2){
            return new MoveResourcesResultMessage(true, sourceStorage, destStorage, "Selected storage doesn't exists");
        }
        return new MoveResourcesResultMessage(false, sourceStorage, destStorage, "Warehouse resources moved");
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
     * @return outcome message encoded as ServerMessage Object
     */
    public ServerMessage activateProduction (Player player, List<Integer> slots,List<Resource> leaderResource){
        System.out.println(slots.toString());
        System.out.println(player.getPeekCardsInDevCardSLots());
        if(slots.isEmpty()) return new ProductionResultMessage(true, "Empty development card slots!", true);
        if (slots.size() > 3) return new ProductionResultMessage(true,"From server: Already selected the maximum of 3 slots!", false);
        List<Resource> productionInCost = new ArrayList<>();
        //collects the resources needed to activate production in selected slots/slots
        for (Integer slotNum : slots) {
            try {
                productionInCost.addAll(player.devCardSlotProductionIn(slotNum));
            } catch (EmptySlotException e1) {
                return new ProductionResultMessage(true,"Selected an empty slot", false);
            } catch (IndexOutOfBoundsException e2) {
                return new ProductionResultMessage(true, "Selected invalid slot number", false);
            }
        }
        List<Resource> playerResources = player.getTotalResource();
        HashMap<Resource, Integer> checkCost = new HashMap<>();
        for(Resource res : productionInCost){
            if(checkCost.containsKey(res)){
                Integer newValue = checkCost.get(res);
                newValue++;
                checkCost.put(res, newValue);
            } else checkCost.put(res, 1);
        }
        List<Resource> playerCopiedResources = playerResources.stream().collect(Collectors.toList());
        for(Resource res : checkCost.keySet()){
            Integer value = checkCost.get(res);
            for(int i = 0; i < value; i++)
                playerCopiedResources.remove(res);
        }
        //checks if resources needed for production are satisfied by warehouse and/or coffer
        if (playerResources.equals(productionInCost) || !playerCopiedResources.isEmpty()) {
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
                    Integer newPosition = player.getFaithPathPosition();
                    if(multiplayer) {
                        if(player.isRapportoInVaticano(newPosition))
                            for (Player p : players)
                                p.updateFaithPath(newPosition);
                    } else {
                        //TODO increment lorenzo position
                    }
                }
            }
            //removes faith resources
            while(resourcesProductionOut.contains(Resource.FAITH))
                resourcesProductionOut.remove(Resource.FAITH);
            //adds production out resources in coffer
            player.putCofferResources(resourcesProductionOut);
            //checks if production power leader card is activated
            boolean usedLC = this.activateLeaderCardProduction(player, leaderResource);
            turnDone();
            if(usedLC) {
                return new ProductionResultMessage(false,"Activated production and resources inserted in coffer. Leader card power used.", true);
            } else {
                return new ProductionResultMessage(false, "Activated production and resources inserted in coffer", true);
            }
        } else return new ProductionResultMessage(true, "Insufficient resources to activate production on selected slots", true);
    }

    /**
     * @param player the playing player that chose to activate the basic personal board's production
     * @param prodIn1 the first resource needed to activate production
     * @param prodIn2 the second resource needed to activate production
     * @param prodOut production result
     * @param leaderResource resource selected if a production power leader card is activated
     * @return
     */

    //TODO sending and update of the market after last insertion on personal and normal production
    //TODO changing client side personal production requests with INPUT and OUTPUT
    public ServerMessage activatePersonalProduction(Player player, Resource prodIn1, Resource prodIn2, Resource prodOut, List<Resource> leaderResource) {
        List<Resource> productionCost = new ArrayList();
        productionCost.add(prodIn1);
        productionCost.add(prodIn2);

        HashMap<Resource, Integer> checkCost = new HashMap<>();
        for(Resource res : productionCost){
            if(checkCost.containsKey(res)){
                Integer newValue = checkCost.get(res);
                newValue++;
                checkCost.put(res, newValue);
            } else checkCost.put(res, 1);
        }

        List<Resource> playerCopiedResources = player.getTotalResource().stream().collect(Collectors.toList());
        for(Resource res : checkCost.keySet()){
            Integer value = checkCost.get(res);
            for(int i = 0; i < value; i++)
                playerCopiedResources.remove(res);
        }

        if (player.getTotalResource().equals(productionCost) || !playerCopiedResources.isEmpty()) {
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
            List<Resource> resourceOut = new ArrayList<>();
            resourceOut.add(prodOut);
            player.putCofferResources(resourceOut);

            boolean usedLC = this.activateLeaderCardProduction(player, leaderResource);
            turnDone();
            if(usedLC) {
                return new ProductionResultMessage(false, "Activated personal production and resources inserted in coffer. Leader card power used.", true);
            } else {
                return new ProductionResultMessage(false, "Activated personal production and resources inserted in coffer", true);
            }
        } else return new ProductionResultMessage(true,"Insufficient resources to activate personal production", true);
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
        HashMap<String, Integer> faithPathPositions = this.getFaithPathPositions();
        faithPathPositions.remove(player.getNickname());
        try{
            player.activateLeaderCard(indexNumber);
        } catch(IndexOutOfBoundsException e1){
            return new LeaderResultMessage(true, false, true, "Selected index for leader card is out of bounds", indexNumber, faithPathPositions, player.getFaithPathPosition());
        } catch(AlreadyActivatedLeaderCardException e2){
            return new LeaderResultMessage(true, false, true, "Selected leader card already activated", indexNumber, faithPathPositions, player.getFaithPathPosition());
        } catch(InsufficientResourcesException e3){
            return new LeaderResultMessage(true, false, true, "Insufficient resources to activate selected leader card", indexNumber, faithPathPositions, player.getFaithPathPosition());
        } catch(AlreadyDiscardedLeaderCardException e4){
            return new LeaderResultMessage(true, false, true, "Selected leader card was discarded", indexNumber, faithPathPositions, player.getFaithPathPosition());
        }
        return new LeaderResultMessage(false, false, true, "Selected leader card activated", indexNumber, faithPathPositions, player.getFaithPathPosition());
    }

    /**
     * Discards selected leader card from leadecards arraylist. OkMessage is returned ad everything worked fine, it also increments user's faith path position.
     * @param player
     * @param indexNum
     * @return a OkMessage notifying the successful discard action
     */
    public ServerMessage discardLeaderCard (Player player, Integer indexNum){
        HashMap<String, Integer> faithPathPositions = this.getFaithPathPositions();
        faithPathPositions.remove(player.getNickname());
        try{
            player.discardLeaderCard(indexNum);
        } catch (IndexOutOfBoundsException e1){
            return new LeaderResultMessage(true, true, false, "Selected leader card index is out of bounds", indexNum, faithPathPositions, player.getFaithPathPosition());
        } catch (AlreadyActivatedLeaderCardException e2){
            return new LeaderResultMessage(true, true, false, "Selected leader card is activated: you cannot discard it", indexNum, faithPathPositions, player.getFaithPathPosition());
        } catch (AlreadyDiscardedLeaderCardException e3){
            return new LeaderResultMessage(true, true, false, "Selected leader card was already discarded", indexNum, faithPathPositions, player.getFaithPathPosition());
        }
        player.incrementFaithPathPosition();
        Integer newPlayerPos = player.getFaithPathPosition();
        if(multiplayer) {
            if (player.isRapportoInVaticano(newPlayerPos)) {
                for (Player p : players)
                    p.updateFaithPath(newPlayerPos);
            }
        } else {
            //TODO singleplayer option
        }
        faithPathPositions = this.getFaithPathPositions();
        faithPathPositions.remove(player.getNickname());
        return new LeaderResultMessage(false, true, false, "Leader card correctly discarded: received 1 faith point", indexNum, faithPathPositions, player.getFaithPathPosition());
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

    public HashMap<String, Integer> getFaithPathPositions(){
        HashMap<String, Integer> faithPathPositions = new HashMap<>();
        for(Player p : players){
            String nickname = p.getNickname();
            Integer fpPos = p.getFaithPathPosition();
            faithPathPositions.put(nickname, fpPos);
        }
        return faithPathPositions;
    }

    public synchronized void resetDone(){
        this.done = false;
    }

    /**
     * this method enables to unlock the multiplayer handler while
     * is waiting for a user to finish his turn.
     */
    public synchronized void turnDone(){
        this.done = true;
        notifyAll();
    }

    /**
     * called by MultiplayerGameHandler to wait one or all the users to perform a certain action. Unlocked by
     * turnDone (when the game is waiting for a player only) or clientDone (when the game is waiting for all player)
     */
    public synchronized void lock() {
        while(this.done == false)
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        resetDone();
        return;
    }

    /**
     * used by players to notify to the game they finished their action.
     */
    public synchronized void clientDone(){
        accesses++;
        if(accesses.equals(players.size())){
            accesses = 0;
            this.done = true;
            notifyAll();
        }
    }
}


