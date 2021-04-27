package it.polimi.ingsw.controller;

public abstract class GameManager {

    protected TurnManager turnManager;

    public abstract Integer getGameId();

    /**
     *
     * Manage the game allowing players to do actions
     */
    public abstract void manageTurn();

    /**
     *
     * Ends the game
     */
    public abstract void endGame();


    /**
     *
     * Count the victory points of all the players
     */
    public abstract void countVictoryPoints();


}
