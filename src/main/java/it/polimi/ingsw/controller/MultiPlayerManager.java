package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameInstance;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.model.multiplayer.MultiPlayerGameInstance;
import java.util.List;

public class MultiPlayerManager extends GameManager {

    private MultiPlayerGameInstance game;

    private List<MultiPlayer> players;

    public MultiPlayerManager(MultiPlayerGameInstance game){
        this.game = game;
        this.players = game.getPlayers();
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
