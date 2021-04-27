package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.singleplayer.SinglePlayerGameInstance;

public class SinglePlayerManager extends GameManager {

    private SinglePlayerGameInstance game;
    private Player player;

    public SinglePlayerManager(SinglePlayerGameInstance game){
        this.game = game;
        this.player = game.getPlayer();
        this.turnManager = new TurnManager(game.getCardsDeck(), game.getMarketBoard());
    }
    /**
     * Manage the game allowing players to do actions
     */
    @Override
    public void turnManager() {
    }

    /**
     * Ends the game
     */
    @Override
    public void endGame() {

    }

    /**
     * Make the final round until the player on the right of the one with the Calamaio.
     */
    @Override
    public void finalRound() {

    }

    /**
     * Count the victory points of all the players
     */
    @Override
    public void countVictoryPoints() {

    }
}
