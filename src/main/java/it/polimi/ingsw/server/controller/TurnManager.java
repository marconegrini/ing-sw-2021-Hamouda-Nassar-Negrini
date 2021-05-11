package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.messages.ErrorMessage;
import it.polimi.ingsw.server.controller.messages.Message;
import it.polimi.ingsw.server.controller.messages.OkMessage;
import it.polimi.ingsw.server.model.Marble;
import it.polimi.ingsw.server.model.MarketBoard;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.EmptySlotException;
import it.polimi.ingsw.server.model.exceptions.IllegalInsertionException;
import it.polimi.ingsw.server.model.exceptions.IllegalMoveException;
import it.polimi.ingsw.server.model.exceptions.StorageOutOfBoundsException;

import java.io.IOException;
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
                return new ErrorMessage(player.getNickname(), "Invalid slot number");
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

    public void activateLeaderCard (Player player, Integer leaderCardSlotNum){

    }

    public void discardLeaderCard (Player player, List<Integer> leaderCardNum){
        //TODO discard leader card of the player
    }

}


