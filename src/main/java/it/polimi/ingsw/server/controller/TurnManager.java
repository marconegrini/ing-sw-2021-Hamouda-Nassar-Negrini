package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.messages.fromServer.activateProduction.PersonalProductionResultMessage;
import it.polimi.ingsw.messages.fromServer.activateProduction.ProductionResultMessage;
import it.polimi.ingsw.messages.fromServer.leadercard.LeaderResultMessage;
import it.polimi.ingsw.messages.fromServer.storeResources.ErrorWarehouseMessage;
import it.polimi.ingsw.messages.fromServer.storeResources.ResourcesToStoreMessage;
import it.polimi.ingsw.messages.fromServer.update.UpdateLeaderCardsMessage;
import it.polimi.ingsw.messages.fromServer.warehouse.MoveResourcesResultMessage;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.StorageLeaderCard;
import it.polimi.ingsw.enumerations.*;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller class that contains methods invoked by client message's server process methods.
 * Controller's methods modify Model classes and return Server Messages for the client.
 */
public class TurnManager {

    private boolean multiplayer;
    private List<MultiPlayer> players;
    private SinglePlayer player;
    private CardsDeck cardsDeck;
    private MarketBoard marketBoard;
    private List<Resource> resorucesToStore;
    private List<Resource> resourcesFromFirstProduction;
    private boolean obtainedFaithPointsFromProduction;
    private boolean done;
    private Player firstPlayerToEnd;
    private boolean endedFaithPath;
    private boolean sevenDevCardsBought;
    private boolean disconnected;
    private Integer accesses;

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
        this.done = false;
        this.sevenDevCardsBought = false;
        this.firstPlayerToEnd = null;
        this.endedFaithPath = false;
        this.accesses = 0;
        this.disconnected = false;
        this.resourcesFromFirstProduction = new ArrayList<>();
        this.obtainedFaithPointsFromProduction = false;
    }

    /**
     * used to set teh turn manager in multiplayer mode. Acting this way, there will be some cases when
     * it will increment all players faith path instead of just one.
     * @param isMultiplayer
     */
    public void setMultiplayer(boolean isMultiplayer){
        this.multiplayer = isMultiplayer;
    }

    /**
     * sets multiplayer arrayList to manipulate them during the game
     */
    public void setPlayers(List<MultiPlayer> players){
        if(this.multiplayer)
            this.players = players;
    }

    /**
     * Sets the single player object to manipulate it during the game
     * @param player
     */
    public void setPlayer(SinglePlayer player){
        this.player = player;
    }
    /**
     * pick resources from the market and add them to the player
     * @param player  The player is who will receive the picked resources
     * @return OkMessage if marble inserted correctly, ErrorMessage if selected row or column doesn't exists
     */
    public ServerMessage pickResources(Player player, boolean isRow, int rowOrColNum,boolean useStorageLCs) {
        List<Marble> pickedMarbles;
        ArrayList<Resource> recentlyResourcesFromWhiteMarble = new ArrayList<>();
        Resource recentlyAddedRes = null;

        List<LeaderCard> LeaderCardsPlayerOwns = new ArrayList<>();
        List<StorageLeaderCard> storageCardsPlayerOwns = new ArrayList<>();
        long howManyStorageCardAreThere = 0;

        //filtering th storage leader cards
        LeaderCardsPlayerOwns = player.getLeaderCards().stream().filter(x->x.getCardType().equals(CardType.STORAGE)).filter(x-> x.isActivated()&&!x.isDiscarded()).collect(Collectors.toList());

        //casting the cards into storage LCs
        for (LeaderCard ld:LeaderCardsPlayerOwns) {
            if (ld.isActivated())
             storageCardsPlayerOwns.add((StorageLeaderCard) ld);
        }
        howManyStorageCardAreThere = storageCardsPlayerOwns.size();

        try {
            pickedMarbles = marketBoard.insertMarble(isRow, rowOrColNum);
        } catch (IndexOutOfBoundsException e){
            return new ErrorMessage("Selected row or column doesn't exists");
        }
        System.out.println(pickedMarbles);
        boolean usedLeaderCard = false;
        List<Resource> resourcesToStore = new ArrayList<>();

        for(Marble marble : pickedMarbles) {
            Color marbleColor = marble.getColor();

            switch (marbleColor) {
                case YELLOW:
                    resourcesToStore.add(Resource.COIN);
                    recentlyAddedRes = Resource.COIN;
                    break;
                case RED:
                    player.incrementFaithPathPosition();
                    if (multiplayer) {
                        Integer newUserPos = player.getFaithPathPosition();
                        for (Player p : players)
                            p.updateFaithPath(newUserPos);
                        if (newUserPos.equals(player.faithPathEnd())) {
                            endedFaithPath = true;
                            if (firstPlayerToEnd == null)
                                firstPlayerToEnd = (MultiPlayer) player;
                        }
                    }
                    break;
                case VIOLET:
                    resourcesToStore.add(Resource.SERVANT);
                    recentlyAddedRes = Resource.SERVANT;

                    break;
                case WHITE:
                    if (player.isLeaderCardActivated(CardType.MARBLE)) {
                        HashMap<Resource, Integer> resourcesFromLeaderCard;
                        resourcesFromLeaderCard = player.getLeaderCardsPower(CardType.MARBLE);
                        Set<Resource> resourcesFromHashMap = resourcesFromLeaderCard.keySet();
                        for (Resource resource : resourcesFromHashMap) {
                            for (int i = 0; i < resourcesFromLeaderCard.get(resource); i++) {
                                resourcesToStore.add(resource);
                                recentlyResourcesFromWhiteMarble.add(resource); //copy the resources from white marble to a list
                            }
                        }
                        usedLeaderCard = true;
                    }
                    break;
                case GREY:
                    resourcesToStore.add(Resource.STONE);
                    recentlyAddedRes = Resource.STONE;

                    break;
                case BLUE:
                    resourcesToStore.add(Resource.SHIELD);
                    recentlyAddedRes = Resource.SHIELD;

                    break;
                default:
                    break;
            }

            if (useStorageLCs){
            //add resources in LeaderCard-storage type if a resource matches a empty slot in a Leader card.
            if (howManyStorageCardAreThere > 0) {
                //at least one SLC is activated..

                if (recentlyResourcesFromWhiteMarble.size() != 0) {
                    //whiteMarble is used
                    //add the resources that can be added to a Leader card's storage and remove them form the resources to restore
                    for (Resource res : recentlyResourcesFromWhiteMarble) {
                        for (StorageLeaderCard sld : storageCardsPlayerOwns) {
                            if (sld.storageType().equals(res) && sld.hasAvailableSlots()) {
                                try {
                                    sld.putResourceInCardStorage(null, res);
                                    resourcesToStore.remove(res);
                                    break; //break teh inner for to go to the next resource;
                                } catch (IllegalInsertionException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } else { //white marble isn't used
                    for (StorageLeaderCard sld : storageCardsPlayerOwns) {
                        boolean temp = sld.hasAvailableSlots();
                        System.out.println("sld.hasAvailableSlots(); ---*>"+temp);
                        if (sld.storageType().equals(recentlyAddedRes) && sld.hasAvailableSlots()) {
                            try {
                                sld.putResourceInCardStorage(null, recentlyAddedRes);
                                resourcesToStore.remove(recentlyAddedRes);
                                break; //break the for;
                            } catch (IllegalInsertionException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }
                recentlyAddedRes = null;
                recentlyResourcesFromWhiteMarble.clear();
            }
        }

        this.resorucesToStore = resourcesToStore;
        System.out.println(resourcesToStore);
        if(resourcesToStore.isEmpty()){
            this.turnDone();
            return new OkMessage("You don't have resources to store!");
        } else {
            if(!usedLeaderCard)
                return new ResourcesToStoreMessage(false, resourcesToStore, "Obtained resources from market.", player.getClonedWarehouse());
            else return new ResourcesToStoreMessage(false, resourcesToStore, "Obtained resources from market. Leader card power used.", player.getClonedWarehouse());
        }
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
                                    for (MultiPlayer mp : players) {
                                        mp.updateFaithPath(newUserPos);
                                    }
                            }
                            if(newUserPos.equals(p.faithPathEnd())){
                                endedFaithPath = true;
                                if(firstPlayerToEnd == null)
                                    firstPlayerToEnd = (MultiPlayer) player;
                            }
                        }
                    }
                } else {
                    SinglePlayer sp = (SinglePlayer) player;
                    sp.incrementLorenzoPosition();
                    Integer lorenzoPosition = sp.getLorenzoPosition();
                    sp.updateFaithPath(lorenzoPosition);
                }
            }
            if(resorucesToStore.equals(resourcesIn)) {
                turnDone();
                return new ResourcesToStoreMessage(true, null, "Resources correctly discarded!", player.getClonedWarehouse());
            } else {
                for(Resource res : resourcesIn){
                    this.resorucesToStore.remove(res);
                }
                return new ResourcesToStoreMessage(false, this.resorucesToStore, "Insert or discard remaining resources.", player.getClonedWarehouse());
            }
        }
    }

    /**
     * @param player the playing player
     * @param sourceStorage
     * @param destStorage
     * @return a MoveResourcesResult message, containing a positive outcome if everything worked fine, a
     * negative one instead.
     */
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
    public ServerMessage buyDevelopmentCard (Player player, Integer row, Integer column, Integer devCardSlot) {

        List<Resource> playerResources = player.getTotalResource();

        if (!cardsDeck.isEmptyDeck(row, column)) {
            List<Resource> devCardCost = null;
            try {
                devCardCost = cardsDeck.developmentCardCost(row, column);
            } catch (EmptyDeckException e) {
                e.printStackTrace();
                return new BuyDVCardError("The cards deck is empty please choose another one", false);
            }

//            System.out.println("playerResources: " + playerResources + "\n");
            System.out.println("devCardCost: " + devCardCost + "\n");

            boolean usedLeaderCard = false;

            if (player.isLeaderCardActivated(CardType.DISCOUNT)) {
                HashMap<Resource, Integer> resourcesFromLeaderCard = null;
                resourcesFromLeaderCard = player.getLeaderCardsPower(CardType.DISCOUNT);
                Set<Resource> discountedResource = resourcesFromLeaderCard.keySet();
                for (Resource resource : discountedResource) {
                    if (devCardCost.contains(resource))
                        for (int i = 0; i < resourcesFromLeaderCard.get(resource); i++)
                            devCardCost.remove(resource);
                }
                usedLeaderCard = true;
            }

            if (containsNeededResources(player, devCardCost)) {

                pullNeededResources(player, devCardCost);
                DevelopmentCard devCard = cardsDeck.popCard(row, column);
                try {
                    player.addCardInDevCardSlot(devCardSlot, devCard);
                } catch (IllegalInsertionException e1) {
                    return new BuyDVCardError("Slot insertion not allowed", false);
                } catch (IndexOutOfBoundsException e2) {
                    return new BuyDVCardError("Invalid slot number", false);
                }
                turnDone();

                if (player.sevenDevCardBought()) {
                    sevenDevCardsBought = true;
                    this.firstPlayerToEnd = player;
                }

                if (usedLeaderCard)
                    return new OkMessage("Bought development card and inserted in slot number " + (devCardSlot + 1) + ". Leader card power used.");
                else return new OkMessage("Bought development card and inserted in slot number " + (devCardSlot + 1));
            } else return new BuyDVCardError("Insufficient resources to buy selected development card", true);
        } else return new BuyDVCardError("Empty deck! You cannot buy requested leader card!", true);
    }

    /**
     * Method that checks if specified player contains selected resource cost in deposits
    * @param player
     * @param cost
     * @return true if needed resources are present, false otherwise.
     */
    public boolean containsNeededResources(Player player, List<Resource> cost){
        List<LeaderCard> leaderCards = new ArrayList<>();
        List<StorageLeaderCard> storageLeaderCards = new ArrayList<>();
        List<Resource> sldResource = new ArrayList<>();

        leaderCards = player.getLeaderCards();

        //hasn't discarded 'em
        if (leaderCards.stream().anyMatch(x->x.isActivated()))
            if (leaderCards.stream().anyMatch(x -> x.getCardType().equals(CardType.STORAGE))) //if any card is of type STORAGE
                for (LeaderCard ld: leaderCards)
                    if (ld.getCardType().equals(CardType.STORAGE)&&ld.isActivated())
                        storageLeaderCards.add((StorageLeaderCard) ld);


        HashMap<Resource, Integer> checkCost = new HashMap<>();
        for (Resource res : cost) {
            if (checkCost.containsKey(res)) {
                Integer newValue = checkCost.get(res);
                newValue++;
                checkCost.put(res, newValue);
            } else checkCost.put(res, 1);
        }

        List<Resource> playerResources = player.getTotalResource();

        boolean resourceContained = true;
        OuterFor:
        for (Resource res : checkCost.keySet()) {
            Integer value = checkCost.get(res);
            for (int i = 0; i < value; i++) {
                resourceContained = playerResources.remove(res);
                //if the resource isn't present in the player resources --> check if it is present in one Storage LC.
                if(!resourceContained) {
                    if(!storageLeaderCards.isEmpty()) {
                        for (StorageLeaderCard sld : storageLeaderCards)
                            if (sld.getStoredResources().contains(res)) {
                                try {
                                    sld.pullResource();
                                    break;
                                } catch (EmptySlotException e) {
                                    e.printStackTrace();
                                    break OuterFor;
                                }
                            } else {
                                break OuterFor;
                            }
                    } else {
                        break OuterFor;
                    }
                }
            }
        }
        
        return resourceContained;
    }

    /**
     * Method that takes from deposits and leader cards, first from warehouse,from StorageLeaderCards then from coffer, specified resources
     * @param player the player in the turn
     * @param cost the necessary resources needed
     */
    public boolean pullNeededResources(Player player, List<Resource> cost){

        List<Resource> warehouseResources = player.getWarehouseResource();
        List<Resource> toTakeFromWarehouse = new ArrayList<>();
        List<Resource> toTakeFromCoffer = new ArrayList<>();
        List<Resource> toTakeFromLeaderCard = new ArrayList<>();
        List<LeaderCard> leaderCards = player.getLeaderCards();
        List<StorageLeaderCard> storageLeaderCards = new ArrayList<>();
        List<Resource> sldResource = new ArrayList<>();

        //hasn't discarded 'em
        if (leaderCards.stream().anyMatch(x->x.isActivated()))
            if (leaderCards.stream().anyMatch(x -> x.getCardType().equals(CardType.STORAGE)&&x.isActivated())) //if any card is of type STORAGE
                for (LeaderCard ld: leaderCards)
                    if (ld.getCardType().equals(CardType.STORAGE)&&ld.isActivated())
                        storageLeaderCards.add((StorageLeaderCard) ld);


        boolean pulled = false;
        if(containsNeededResources(player, cost)) {

            for (Resource resource : cost) {
                //warehouse
                if (warehouseResources.contains(resource)) {
                    System.out.println("Passed here 1 + Resource: "+resource);//testing
                    warehouseResources.remove(resource);
                    toTakeFromWarehouse.add(resource);
                    pulled = true;
                }

                //leader storage cards
                if (!pulled) {
                    for (StorageLeaderCard sld : storageLeaderCards) {
                        if (pulled)
                            break; //go to the next resource
                        //if at least one deposit/slot of a StorageLeaderCard contains the searched resource
                        if (sld.getStoredResources().contains(resource)) {
                            {
                                //pull one resource from any deposit/slot of teh card
                                try {
                                    System.out.println("Passed here 1 + Resource: " + resource); //testing
                                    sld.pullResource();
                                    pulled = true;
                                } catch (EmptySlotException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }

                //coffer
                if (!pulled){
                    System.out.println("Passed here 3 + Resource: " + resource);//testing
                    toTakeFromCoffer.add(resource);
                }

                pulled = false;
            }

            player.pullWarehouseResources(toTakeFromWarehouse);
            player.pullCofferResources(toTakeFromCoffer);
        }

        return pulled;
    }

    /**
     * @param player playing player
     * @param slots List of integers between 0 and 2: they are the indexes of development card slots
     * @param leaderResource is chosen by the user as result of the activation of the production power leader card.
     *                       The chosen resource will be added to the production output, together with a faith point.
     * @return outcome message encoded as ServerMessage Object
     */
    public ServerMessage activateProduction (Player player, List<Integer> slots, List<Resource> leaderResource){
        System.out.println(slots.toString());
        System.out.println(player.getPeekCardsInDevCardSLots());
        if(slots.isEmpty()) return new ProductionResultMessage(true, "Empty development card slots!", true, player.getClonedWarehouse(), player.getClonedCoffer());
        if (slots.size() > 3) return new ProductionResultMessage(true,"From server: Already selected the maximum of 3 slots!", false, null, null);
        List<Resource> productionInCost = new ArrayList<>();
        //collects the resources needed to activate production in selected slots/slots
        for (Integer slotNum : slots) {
            try {
                productionInCost.addAll(player.devCardSlotProductionIn(slotNum));
            } catch (EmptySlotException e1) {
                return new ProductionResultMessage(true,"Selected an empty slot", false, null, null);
            } catch (IndexOutOfBoundsException e2) {
                return new ProductionResultMessage(true, "Selected invalid slot number", false, null, null);
            }
        }

        //checks if resources needed for production are satisfied by warehouse and/or coffer
        if (containsNeededResources(player, productionInCost)) {
            System.out.println(productionInCost);

            pullNeededResources(player, productionInCost);

            List<Resource> resourcesProductionOut = new ArrayList<>();
            for (Integer slotNum : slots)
                resourcesProductionOut.addAll(player.devCardSlotProductionOut(slotNum));
            //increment user's faith path position and updates other users (if multiplayer) if Resource.FAITH is/are present
            for(Resource resource : resourcesProductionOut){
                if(resource.equals(Resource.FAITH)){
                    obtainedFaithPointsFromProduction = true;
                    player.incrementFaithPathPosition();
                    if(multiplayer) {
                        Integer newPosition = player.getFaithPathPosition();
                        if(player.isRapportoInVaticano(newPosition))
                            for (Player p : players)
                                p.updateFaithPath(newPosition);
                        if(newPosition.equals(player.faithPathEnd())){
                            endedFaithPath = true;
                            if(firstPlayerToEnd == null)
                                firstPlayerToEnd = (MultiPlayer) player;
                        }
                    }
                }
            }
            //removes faith resources
            while(resourcesProductionOut.contains(Resource.FAITH))
                resourcesProductionOut.remove(Resource.FAITH);

            //player.putCofferResources(resourcesProductionOut);
            this.resourcesFromFirstProduction.addAll(resourcesProductionOut);
            //checks if production power leader card is activated
            boolean usedLC = this.activateLeaderCardProduction(player, leaderResource);
            if(usedLC) {
                return new ProductionResultMessage(false,"Activated production and resources brought from coffer. Leader card power used.", true, player.getClonedWarehouse(), player.getClonedCoffer());
            } else {
                return new ProductionResultMessage(false, "Activated production and resources brought coffer", true, player.getClonedWarehouse(), player.getClonedCoffer());
            }
        } else return new ProductionResultMessage(true, "Insufficient resources to activate production on selected slots", true, player.getClonedWarehouse(), player.getClonedCoffer());
    }

    /**
     * @param player the playing player that chose to activate the basic personal board's production
     * @param prodIn1 the first resource needed to activate production
     * @param prodIn2 the second resource needed to activate production
     * @param prodOut production result
     * @param leaderResource resource selected if a production power leader card is activated
     * @param asSecondProduction if the personal production is going to be performed after a normal one
     * @param activate if the personal production is performed as second production after the normal one, the player has
     *                 the possibility to choose if activate or not the personal production.
     * @return
     */
    public ServerMessage activatePersonalProduction(Player player, Resource prodIn1, Resource prodIn2, Resource prodOut, List<Resource> leaderResource, boolean asSecondProduction, boolean activate) {
        if(activate) {
            List<Resource> productionCost = new ArrayList();
            productionCost.add(prodIn1);
            productionCost.add(prodIn2);

            if (containsNeededResources(player, productionCost)) {
                //the player does have needed resources

                pullNeededResources(player, productionCost);
                List<Resource> resourceOut = new ArrayList<>();
                resourceOut.add(prodOut);
                if(asSecondProduction){
                    //it's the second production, add resources to the previously obtained. Terminate turn.
                    resourcesFromFirstProduction.addAll(resourceOut);
                    player.putCofferResources(resourcesFromFirstProduction);
                    resourcesFromFirstProduction.clear();
                    obtainedFaithPointsFromProduction = false;
                    turnDone();
                    return new PersonalProductionResultMessage(false, "Obtained the resource from personal production",  true);
                } else {
                    //It's the first production of the player. Try to activate leader production and terminate turn.
                    player.putCofferResources(resourceOut);
                    boolean usedLC = this.activateLeaderCardProduction(player, leaderResource);
                    turnDone();
                    if (usedLC) {
                        return new PersonalProductionResultMessage(false, "Activated personal production and resources inserted in coffer. Leader card power used.", false);
                    } else {
                        return new PersonalProductionResultMessage(false, "Activated personal production and resources inserted in coffer", false);
                    }
                }

            } else {
                //the player doesn't have needed resources

                if (asSecondProduction) {
                    //It's the second production activated by the user.
                    //Checks whether the player has previously obtained something.
                    if(!resourcesFromFirstProduction.isEmpty() || obtainedFaithPointsFromProduction){
                        //the player obtained something before. Terminate turn.
                        player.putCofferResources(resourcesFromFirstProduction);
                        resourcesFromFirstProduction.clear();
                        obtainedFaithPointsFromProduction = false;
                        turnDone();
                        return new PersonalProductionResultMessage(false, "Insufficient resources to activate personal production.\n Previously obtained resources inserted in coffer.", true);
                    } else {
                        //the player didn't obtained anything before. The turn is not done.
                        return new PersonalProductionResultMessage(true, "Insufficient resources to activate personal production. \n You haven't obtained anything previously.", false);
                    }
                } else {
                    //it's the only production activated by the user. He doesn't have needed resources. The turn is not done.
                    return new PersonalProductionResultMessage(true, "Insufficient resources to activate personal production.", false);
                }
            }

        } else {
            //It's the second production made by the user and the player decided not to activate it.
            //Checks whether the player has obtained something previously
            if(!resourcesFromFirstProduction.isEmpty() || obtainedFaithPointsFromProduction){
                //the player has obtained resources or faith points from main production. The turn is done
                player.putCofferResources(resourcesFromFirstProduction);
                resourcesFromFirstProduction.clear();
                obtainedFaithPointsFromProduction = false;
                turnDone();
                return new PersonalProductionResultMessage(false, "Personal production not activated. Added resources previously obtained.", true);
            } else {
                //the player hasn't obtained anything before.
                return new PersonalProductionResultMessage(true, "Personal production not activated. You haven't obtained resources or faith points previously.", false);
            }
        }
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
            List<Resource> productionInputCost = new ArrayList();
            for(Resource r : prodInCost.keySet()){
                for(int i = 0; i < prodInCost.get(r); i++){
                    productionInputCost.add(r);
                }
            }

            //checks if there are enough resources in warehouse and/or coffer to activate leader card production
            if (containsNeededResources(player, productionInputCost)) {

                pullNeededResources(player, productionInputCost);

                List<Resource> clientChoice = new ArrayList<>();
                clientChoice.addAll(leaderResource);
                //inserts in coffer chosen resource
                player.putCofferResources(clientChoice);
                //increments user faith position and updates other user's faith paths
                player.incrementFaithPathPosition();
                if(multiplayer) {
                    Integer newPlayerPosition = player.getFaithPathPosition();
                    if(player.isRapportoInVaticano(newPlayerPosition))
                        for (Player p : players)
                            p.updateFaithPath(player.getFaithPathPosition());
                    if(newPlayerPosition.equals(player.faithPathEnd())){
                        endedFaithPath = true;
                        if(firstPlayerToEnd == null)
                            firstPlayerToEnd = (MultiPlayer) player;
                    }
                }
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
        HashMap<String, Integer> faithPathPositions = new HashMap<>();
        if(multiplayer) {
            faithPathPositions = this.getFaithPathPositions();
            faithPathPositions.remove(player.getNickname());
        } else {
            Integer lorenzoPosition = ((SinglePlayer) player).getLorenzoPosition();
            faithPathPositions.put("Lorenzo", lorenzoPosition);
        }
        try{
            player.activateLeaderCard(indexNumber);
        } catch(IndexOutOfBoundsException e1){
            return new LeaderResultMessage(true, false, true, ANSITextFormat.BOLD_ITALIC +"->Selected index for leader card is out of bounds\n"+ANSITextFormat.RESET, indexNumber, faithPathPositions, player.getFaithPathPosition(), player.getVaticanSections());
        } catch(AlreadyActivatedLeaderCardException e2){
            return new LeaderResultMessage(true, false, true, ANSITextFormat.BOLD_ITALIC +"->Selected leader card already activated\n"+ANSITextFormat.RESET, indexNumber, faithPathPositions, player.getFaithPathPosition(), player.getVaticanSections());
        } catch(InsufficientResourcesException e3){
            return new LeaderResultMessage(true, false, true, ANSITextFormat.BOLD_ITALIC +"->Insufficient resources to activate selected leader card\n"+ANSITextFormat.RESET, indexNumber, faithPathPositions, player.getFaithPathPosition(), player.getVaticanSections());
        } catch(AlreadyDiscardedLeaderCardException e4){
            return new LeaderResultMessage(true, false, true, ANSITextFormat.BOLD_ITALIC +"->Selected leader card was discarded\n"+ANSITextFormat.RESET, indexNumber, faithPathPositions, player.getFaithPathPosition(), player.getVaticanSections());
        }
        return new LeaderResultMessage(false, false, true, ANSITextFormat.BOLD_ITALIC +"->Selected leader card activated\n"+ANSITextFormat.RESET, indexNumber, faithPathPositions, player.getFaithPathPosition(), player.getVaticanSections());
    }

    /**
     * Discards selected leader card from leadecards arraylist. OkMessage is returned ad everything worked fine, it also increments user's faith path position.
     * @param player
     * @param indexNum
     * @return a OkMessage notifying the successful discard action
     */
    public ServerMessage discardLeaderCard (Player player, Integer indexNum){
        HashMap<String, Integer> faithPathPositions = new HashMap<>();
        if(multiplayer) {
            faithPathPositions = this.getFaithPathPositions();
            faithPathPositions.remove(player.getNickname());
        } else {
            Integer lorenzoPosition = ((SinglePlayer) player).getLorenzoPosition();
            faithPathPositions.put("Lorenzo", lorenzoPosition);
        }

        try{
            player.discardLeaderCard(indexNum);
        } catch (IndexOutOfBoundsException e1){
            return new LeaderResultMessage(true, true, false, "Selected leader card index is out of bounds", indexNum, faithPathPositions, player.getFaithPathPosition(), player.getVaticanSections());
        } catch (AlreadyActivatedLeaderCardException e2){
            return new LeaderResultMessage(true, true, false, "Selected leader card is activated: you cannot discard it", indexNum, faithPathPositions, player.getFaithPathPosition(), player.getVaticanSections());
        } catch (AlreadyDiscardedLeaderCardException e3){
            return new LeaderResultMessage(true, true, false, "Selected leader card was already discarded", indexNum, faithPathPositions, player.getFaithPathPosition(), player.getVaticanSections());
        }
        player.incrementFaithPathPosition();
        if(multiplayer) {
            Integer newPlayerPos = player.getFaithPathPosition();
            if (player.isRapportoInVaticano(newPlayerPos)) {
                for (Player p : players)
                    p.updateFaithPath(newPlayerPos);
            }
            if(newPlayerPos.equals(player.faithPathEnd())){
                endedFaithPath = true;
                if(firstPlayerToEnd == null)
                    firstPlayerToEnd = (MultiPlayer) player;
            }
            faithPathPositions = this.getFaithPathPositions();
            faithPathPositions.remove(player.getNickname());
        }

        return new LeaderResultMessage(false, true, false, "Leader card correctly discarded: received 1 faith point", indexNum, faithPathPositions, player.getFaithPathPosition(), player.getVaticanSections());
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

    /**
     * @return an hashMap containing the players position in faith path. Needed to send an update version
     * of the model to clients.
     */
    public HashMap<String, Integer> getFaithPathPositions(){
        HashMap<String, Integer> faithPathPositions = new HashMap<>();
        for(Player p : players){
            String nickname = p.getNickname();
            Integer fpPos = p.getFaithPathPosition();
            faithPathPositions.put(nickname, fpPos);
        }
        return faithPathPositions;
    }

    /**
     * Method called to check wether a player bought seven development cards or not
     * @return
     */
    public boolean isSevenDevCardsBought(){
        return sevenDevCardsBought;
    }

    /**
     * The method discards a development card of the specified color from the deck
     * @param color
     * @return false if the game is not ended, true otherwise. The game ends when all cards of a colour have
     * been removed.
     */
    public boolean discardDevelopmentCards(CardColor color) {
        String cardColor = color.toString();
        DevelopmentCard toPop;
        boolean end = false;
        for (int i = 0; i < 2; i++){
            switch (cardColor) {
                case ("GREEN"):
                    toPop = cardsDeck.popCard(0, 0);
                    if (toPop.getVictoryPoints() == 0) {
                        toPop = cardsDeck.popCard(1, 0);
                        if (toPop.getVictoryPoints() == 0) {
                            toPop = cardsDeck.popCard(2, 0);
                            if(toPop.getVictoryPoints() == 0)
                                end = true;
                        }
                    }
                    break;
                case ("BLUE"):
                    toPop = cardsDeck.popCard(0, 1);
                    if (toPop.getVictoryPoints() == 0) {
                        toPop = cardsDeck.popCard(1, 1);
                        if (toPop.getVictoryPoints() == 0) {
                            toPop = cardsDeck.popCard(2, 1);
                            if(toPop.getVictoryPoints() == 0)
                                end = true;
                        }
                    }
                    break;
                case ("YELLOW"):
                    toPop = cardsDeck.popCard(0, 2);
                    if (toPop.getVictoryPoints() == 0) {
                        toPop = cardsDeck.popCard(1, 2);
                        if (toPop.getVictoryPoints() == 0) {
                            toPop = cardsDeck.popCard(1, 2);
                            if(toPop.getVictoryPoints() == 0)
                                end = true;
                        }
                    }
                    break;
                case ("VIOLET"):
                    toPop = cardsDeck.popCard(0, 3);
                    if (toPop.getVictoryPoints() == 0) {
                        toPop = cardsDeck.popCard(1, 3);
                        if (toPop.getVictoryPoints() == 0) {
                            toPop = cardsDeck.popCard(1, 3);
                            if(toPop.getVictoryPoints() == 0)
                                end = true;
                        }
                    }
                    break;
            }
        }
        return end;
    }

    /**
     * return true if a player reached the end of the faith path
     * @return
     */
    public boolean reachedFaithPathEnd(){
        return this.endedFaithPath;
    }

    /**
     * @return the first player that ends the faith path. If during a turn more than one player reach the
     * end, the first one to reach it will be returned.
     */
    public Player getFirstPlayerToEnd(){
        return this.firstPlayerToEnd;
    }

    /**
     * Method invoked to reset done variable, used with locks.
     */
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
     * called by MultiplayerGameHandler to wait one or all users to perform a certain action. Unlocked by
     * turnDone (when the game is waiting for a player only) or clientDone (when the game is waiting for all players)
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
     * Used by players to notify to the game they finished their action.
     * If all players terminated their action, the MultiplayerGameHandler will be unlocked.
     */
    public synchronized void clientDone(){
        if(multiplayer) {
            accesses++;
            if (accesses.equals(players.size())) {
                accesses = 0;
                this.done = true;
                notifyAll();
            }
        } else {
            this.turnDone();
        }
    }

//for debugging
    public Integer getAccesses() {
        return accesses;
    }

    public void setDisconnected(){
        this.disconnected = true;
    }

    public boolean getDisconnected(){
        return this.disconnected;
    }
}


