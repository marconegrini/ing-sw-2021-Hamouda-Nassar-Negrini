package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;

import java.util.List;

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











































