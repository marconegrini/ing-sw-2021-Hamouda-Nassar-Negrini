package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameInstance;
import it.polimi.ingsw.model.Player;

import java.util.HashMap;
import java.util.List;

public abstract class TurnManagment {

    protected Turn turn;
    protected GameInstance game;
    protected HashMap<Player, Integer> playersPoints;
    protected List<Player> players;

    /**
     *
     * Manage the game allowing players to do actions
     */
    public abstract void turnManager();

    /**
     *
     * Ends the game
     */
    public abstract void endGame();

    /**
     *
     * Make the final round until the player on the right of the one with the Calamaio.
     */
    public abstract void finalRound();

    /**
     *
     * Count the victory points of all the players
     */
    public abstract void countVictoryPoints();


}
