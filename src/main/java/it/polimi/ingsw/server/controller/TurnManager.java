package it.polimi.ingsw.server.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.server.controller.messages.ErrorMessage;
import it.polimi.ingsw.server.controller.messages.Message;
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
    }

    /**
     * pick resources from the market and add them to the player
     * @param player  The player is who will receive the picked resources
     */
    public void pickResources (Player player, boolean isRow, int rowOrColNum){

        List<Marble> pickedMarbles = marketBoard.insertMarble(isRow, rowOrColNum);

        Gson gson = new Gson();

        //Sending to the client the picked marbles
        try {
            player.getToClient().writeUTF(gson.toJson(pickedMarbles));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String actionSelected = "";

        try {

            //Asking the client for the action. ORDER means that he wants to swap two shelfs.
            //MARBLE means that he wants to put a marble in a shelf
            player.getToClient().writeUTF(gson.toJson("SELECT ACTION"));

            while (!(player.getFromClient().available() > 0));
            actionSelected = gson.fromJson(player.getFromClient().readUTF() ,String.class);

            if (actionSelected.equalsIgnoreCase("ORDER")){
                player.getToClient().writeUTF(gson.toJson("SELECT ROWS"));

                //Asking the user to insert a right data
                boolean moveResourceError = true;
                while (moveResourceError) {
                    //Receiving the first shelf that the client wants to swap
                    while (!(player.getFromClient().available() > 0)) ;
                    int firstShelf = gson.fromJson(player.getFromClient().readUTF(), int.class);

                    //Receiving the second shelf that the client wants to swap
                    while (!(player.getFromClient().available() > 0)) ;
                    int secondShelf = gson.fromJson(player.getFromClient().readUTF(), int.class);

                    try {
                        player.getPersonalBoard().getWarehouse().moveResource(firstShelf, secondShelf);
                        moveResourceError = false;
                    } catch (IllegalMoveException e) {
                        //Communicate to the client that he can't make this move
                        player.getToClient().writeUTF(gson.toJson("ILLEGALMOVE"));
                        moveResourceError = true;
                    } catch (StorageOutOfBoundsException e) {
                        //Communicate to the client that he put a wrong nomber of the shelf
                        player.getToClient().writeUTF(gson.toJson("OUTOFBOUNDS"));
                        moveResourceError = true;
                    }
                }


            }
        } catch (IOException e) {
            e.printStackTrace();

        }


    }


    /**
     * @param player playing player
     * @param row row inddex of development cards deck
     * @param column column index of development cards deck
     * @param devCardSlot slot index of development cards slots
     * @return outcome message encoded as Message Object
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

                Message message = new ErrorMessage(player.getNickname(), "Slot insertion not allowed");
                return message;

            } catch (IndexOutOfBoundsException e2){

                Message message = new ErrorMessage(player.getNickname(),"Invalid slot number");
            }

            return new Message("Bought development card and inserted in slot number " + devCardSlot);

        } else return new Message("Insufficient resources to buy selected development card");

    }

    /**
     * @param player playing player
     * @param slots List of integers between 0 and 2
     * @return outcome message encoded as Message Object
     */
    public Message activateProduction (Player player, List<Integer> slots) {

        if (slots.size() > 3) return new Message("Selected more than 3 slots");

        List<Resource> productionInCost = new ArrayList<>();

        for (Integer slotNum : slots) {
            try {
                productionInCost.addAll(player.devCardSlotProductionIn(slotNum));
            } catch (EmptySlotException e1) {
                return new Message("Selected an empty slot");
            } catch (IndexOutOfBoundsException e2) {
                return new Message("Invalid slot number");
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

            return new Message("Activated production and resources inserted in coffer");

        } else return new Message("Insufficient resources to activate production on selected slots");

    }

    public void activateLeaderCard (Player player, List<Integer> leaderCardNum){
        //TODO activate leader card of the player
    }

    public void discardLeaderCard (Player player, List<Integer> leaderCardNum){
        //TODO discard leader card of the player
    }

}











































