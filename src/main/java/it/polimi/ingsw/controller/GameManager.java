package it.polimi.ingsw.controller;

public abstract class GameManager {

    protected TurnManager turnManager;

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
