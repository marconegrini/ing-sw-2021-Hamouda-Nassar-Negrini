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
