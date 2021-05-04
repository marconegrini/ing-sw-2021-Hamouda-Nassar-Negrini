package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.IllegalInsertionException;
import it.polimi.ingsw.model.exceptions.UnsufficientResourcesException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TurnManager {

    private CardsDeck cardsDeck;
    private MarketBoard marketBoard;

    /**
     *
     * This constructor will be used when a game is restored. It allows
     * you to restore the turn class with the old decks and market.
     *
     * @param cardsDeck  the decks is the old deck of a restored game
     * @param marketBoard  the market is the old market of a restored game
     */

    public TurnManager(CardsDeck cardsDeck, MarketBoard marketBoard){
        this.cardsDeck = cardsDeck;
        this.marketBoard = marketBoard;
    }

    /**
     *
     * pick resources from the market and add them to the player
     * @param player  The player is who will receive the picked resources
     */
    public void pickResources (Player player, boolean row, int rowOrColNum){

        List<Marble> pickedMarbles = marketBoard.insertMarble(row, rowOrColNum);

        //TODO put the pickedResources in the player's warehouse

    }

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

                Message message = new Message("Slot insertion not allowed");
                return message;

            } catch (IndexOutOfBoundsException e2){

                Message message = new Message("Invalid slot number");
            }

            return new Message("Bought development card and inserted in slot number " + devCardSlot);

        } else return new Message("Insufficient resources to buy selected development card");

    }

    public void activateProduction (Player player, List<Integer> slots){
        //TODO activate the production of the player

    }

    public void activateLeaderCard (Player player, List<Integer> leaderCardNum){
        //TODO activate leader card of the player
    }

    public void discardLeaderCard (Player player, List<Integer> leaderCardNum){
        //TODO discard leader card of the player
    }

}










































