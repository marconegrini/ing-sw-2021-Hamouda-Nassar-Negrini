package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.messages.Message;
import it.polimi.ingsw.server.controller.messages.updateFromServer.OkMessage;
import it.polimi.ingsw.server.controller.messages.updateFromServer.ErrorMessage;
import it.polimi.ingsw.server.model.Marble;
import it.polimi.ingsw.server.model.MarketBoard;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class TurnManager {

    private CardsDeck cardsDeck;
    private MarketBoard marketBoard;

    private List<Resource> pickedResources;

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
        pickedResources = new ArrayList<>();
    }

    /**
     * pick resources from the market and add them to the player
     * @param player  The player is who will receive the picked resources
     * @return OkMessage if marble inserted correctly, ErrorMessage if selected row or column doesn't exists
     */
    public Message pickResources (Player player, boolean isRow, int rowOrColNum) {

        try {
            List<Marble> pickedMarbles = marketBoard.insertMarble(isRow, rowOrColNum);
        } catch (IndexOutOfBoundsException e){
            return new ErrorMessage(player.getNickname(), "Selected row or column doesn't exists");
        }
        return new OkMessage(player.getNickname(), "Marble inserted");
    }


    /**
     *
     * @param player player who performed pickresources choice
     * @param destStorage destination storage in warehouse
     * @param resourcesIn List of resources to insert
     * @return OkMessage if everything worked fine, ErrorMessage instead
     */
    public Message insertResourcesInWarehouse(Player player, Integer destStorage, List<Resource> resourcesIn){
        try {
            player.putWarehouseResources(destStorage, resourcesIn);
        } catch (StorageOutOfBoundsException e1){
            return new ErrorMessage(player.getNickname(), "Selected storage doesn't exists");
        } catch (IllegalInsertionException e2){
            return new ErrorMessage(player.getNickname(), "Insertion not permitted");
        }
        return new OkMessage(player.getNickname(), "Resources correctly inserted");
    }

    public Message moveResourcesInWarehouse(Player player, Integer sourceStorage, Integer destStorage){
        try{
            player.moveWarehouseResources(sourceStorage, destStorage);
        } catch (IllegalMoveException e1){
            return new ErrorMessage(player.getNickname(), "Warehouse move not permitted");
        } catch (StorageOutOfBoundsException e2){
            return new ErrorMessage(player.getNickname(), "Selected storage doesn't exists");
        }
        return new OkMessage(player.getNickname(), "Warehouse resources moved");
    }

    /**
     * @param player playing player
     * @param row row inddex of development cards deck
     * @param column column index of development cards deck
     * @param devCardSlot slot index of development cards slots
     * @return OkMessage if everything worked fine, ErrorMessage instead
     */
    public Message buyDevelopmentCard (Player player, Integer row, Integer column, Integer devCardSlot) {

        List<Resource> playerResources = player.getTotalResource();
        List<Resource> devCardCost = cardsDeck.developmentCardCost(row, column);

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
                return new ErrorMessage(player.getNickname(), "Slot insertion not allowed");
            } catch (IndexOutOfBoundsException e2){
                return new ErrorMessage(player.getNickname(),"Invalid slot number");
            }
            return new OkMessage(player.getNickname(), "Bought development card and inserted in slot number " + devCardSlot);
        } else return new ErrorMessage(player.getNickname(), "Insufficient resources to buy selected development card");
    }

    /**
     * @param player playing player
     * @param slots List of integers between 0 and 2
     * @return outcome message encoded as Message Object
     */
    public Message activateProduction (Player player, List<Integer> slots) {

        if (slots.size() > 3) return new ErrorMessage(player.getNickname(), "Selected more than 3 slots");
        List<Resource> productionInCost = new ArrayList<>();

        for (Integer slotNum : slots) {
            try {
                productionInCost.addAll(player.devCardSlotProductionIn(slotNum));
            } catch (EmptySlotException e1) {
                return new ErrorMessage(player.getNickname(), "Selected an empty slot");
            } catch (IndexOutOfBoundsException e2) {
                return new ErrorMessage(player.getNickname(), "Selected invalid slot number");
            }
        }
        List<Resource> playerResources = player.getTotalResource();

        if (playerResources.equals(productionInCost) || playerResources.containsAll(productionInCost)) {
            List<Resource> toTakeFromCoffer = player.getWarehouseResource();
            List<Resource> toTakeFromWarehouse = new ArrayList<>();
            for (Resource resource : productionInCost)
                if (toTakeFromCoffer.contains(resource)) {
                    toTakeFromCoffer.remove(resource);
                    toTakeFromWarehouse.add(resource);
                }

            player.pullWarehouseResources(toTakeFromWarehouse);
            player.pullCofferResources(toTakeFromCoffer);
            List<Resource> resourcesProductionOut = new ArrayList<>();

            for (Integer slotNum : slots)
                resourcesProductionOut.addAll(player.devCardSlotProductionOut(slotNum));

            player.putCofferResources(resourcesProductionOut);
            return new OkMessage(player.getNickname(), "Activated production and resources inserted in coffer");

        } else return new ErrorMessage(player.getNickname(), "Insufficient resources to activate production on selected slots");

    }

    /**
     * activates leader card of specified index
     * @param player playing player
     * @param indexNumber leader card's index number in arraylist
     * @return ErrorMessage if it is not possible to activate selected leader card, OkMessage instead.
     */
    public Message activateLeaderCard (Player player, Integer indexNumber){
        try{
            player.activateLeaderCard(indexNumber);
        } catch(IndexOutOfBoundsException e1){
            return new ErrorMessage(player.getNickname(), "Selected index for leader card is out of bounds");
        } catch(AlreadyActivatedLeaderCardException e2){
            return new ErrorMessage(player.getNickname(), "Selected leader card already activated");
        } catch(InsufficientResourcesException e3){
            return new ErrorMessage(player.getNickname(), "Insufficient resources to activate selected leader card");
        } catch(AlreadyDiscardedLeaderCardException e4){
            return new ErrorMessage(player.getNickname(), "Selected leader card was discarded");
        }
        return new OkMessage(player.getNickname(), "Selected leader card activated");
    }

    /**
     * Discards selected leader card from leadecards arraylist. OkMessage is returned ad everything worked fine, it also increments user's faith path position.
     * @param player
     * @param indexNum
     * @return
     */
    public Message discardLeaderCard (Player player, Integer indexNum){
        try{
            player.discardLeaderCard(indexNum);
        } catch (IndexOutOfBoundsException e1){
            return new ErrorMessage(player.getNickname(), "Selected leader card index is out of bounds");
        } catch (AlreadyActivatedLeaderCardException e2){
            return new ErrorMessage(player.getNickname(), "Selected leader card is activated:you cannot discard it");
        } catch (AlreadyDiscardedLeaderCardException e3){
            return new ErrorMessage(player.getNickname(), "Selected leader card was already discarded");
        }
        player.incrementFaithPathPosition();
        return new OkMessage(player.getNickname(), "Leader card correctly discarded: received 1 faith point");
    }

    /**
     * method called to select 2 leader cards out of the 4 given while setting up the game
     * @param player playing player
     * @param index1 first leader card index in arraylist
     * @param index2 second leader card index in arraylist
     * @return ErrorMessage if specified indexes are out of bounds, OkMessage instead.
     */
    public Message chooseLeaderCard(Player player, Integer index1, Integer index2){
        try{
            player.chooseLeaderCard(index1, index2);
        } catch(IndexOutOfBoundsException e){
            return new ErrorMessage(player.getNickname(),"Selected indexes for leader cards are out of bounds");
        }
        return new OkMessage(player.getNickname(), "Leader cards correctly chose");
    }
}


