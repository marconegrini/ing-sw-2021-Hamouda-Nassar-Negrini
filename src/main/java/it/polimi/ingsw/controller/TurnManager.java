package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.exceptions.StorageOutOfBoundsException;

import java.io.IOException;
import java.util.List;

public class TurnManager {

    private final CardsDeck cardsDeck;
    private final MarketBoard marketBoard;

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

    public void buyDevelopmentCard (Player player, Integer row, Integer column){
        //TODO add the development card to the player
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











































