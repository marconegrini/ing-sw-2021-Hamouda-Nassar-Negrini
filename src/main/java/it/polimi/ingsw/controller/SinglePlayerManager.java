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

    @Override
    public Integer getGameId() {
        return this.game.getGameId();
    }

    /**
     * Manage the game allowing players to do actions
     */
    @Override
    public void manageTurn() {
    }

    @Override
    public void setUp() {

    }

    /**
     * Ends the game
     */
    @Override
    public void endGame() {

    }

    /**
     * Count the victory points of all the players
     */
    @Override
    public void countVictoryPoints() {

    }
}
