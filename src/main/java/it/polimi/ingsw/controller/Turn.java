package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;

import java.util.List;

public class Turn {

    private CardsDeck decks;
    private MarketBoard market;


    /**
     *
     * This constructor will be used when a game is restored. It allows
     * you to restore the turn class with the old decks and market.
     *
     * @param decks  the decks is the old deck of a restored game
     * @param market  the market is the old market of a restored game
     */

    public Turn (CardsDeck decks, MarketBoard market){
        this.decks = decks;
        this.market = market;
    }


    /**
     *
     * This constructor allows you to create a new Turn with new decks
     * and new market. It is used when a new game is created.
     *
     */

    public Turn (){
        decks = new CardsDeck();
        market = new MarketBoard();
    }


    /**
     *
     * pick resources from the market and add them to the player
     * @param player  The player is who will receive the picked resources
     */
    public void pickResources (Player player){
        //TODO write this class
    }

    public void pickResources (Player player, boolean row, int rowOrColNum){

        List<Marble> pickedResources = market.insertMarble(row, rowOrColNum);

        //TODO put the pickedResources in the player's warehouse

    }

    public void buyDevelopmentCard (Player player){
        //TODO add the development card to the player
    }

    public void activateProduction (Player player){
        //TODO activate the production of the player
    }

    public void activateLeaderCard (Player player){
        //TODO activate leader card of the player
    }

    public void discardLeaderCard (Player player){
        //TODO discard leader card of the player
    }

}











































