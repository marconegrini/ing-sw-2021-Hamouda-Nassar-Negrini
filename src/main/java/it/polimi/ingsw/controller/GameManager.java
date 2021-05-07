package it.polimi.ingsw.controller;

import java.io.IOException;

public abstract class GameManager {

    protected TurnManager turnManager;

    public abstract Integer getGameId();

    /**
     *
     * Manage the game allowing players to do actions
     */
    public abstract void manageTurn() throws IOException;

    /**
     * initializes players with Calamaio and leaderCards
     */
    public abstract void setCalamaio();

    public abstract void setLeaderCards();

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


    /**
     *
     */

    public void activateLeaderCard(){

    }
}
