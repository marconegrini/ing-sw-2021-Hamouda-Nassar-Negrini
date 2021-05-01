package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.model.parser.LeaderCardParser;
import it.polimi.ingsw.model.singleplayer.SinglePlayerGameInstance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class SinglePlayerManager extends GameManager {

    private SinglePlayerGameInstance game;
    private Player player;

    public SinglePlayerManager(SinglePlayerGameInstance game){
        this.game = game;
        //this.player = game.getPlayer();
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

        this.setUp();

        try{
            player.getToClient().writeUTF("OK IN GAME");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void setUp() {

        this.player = game.getPlayer();

        LeaderCardParser parser = new LeaderCardParser("src/main/java/it/polimi/ingsw/model/jsonFiles/LeaderCardJson.json");
        Stack<LeaderCard> leaderCards = parser.getLeaderCardsDeck();
        parser.close();

        Collections.shuffle(leaderCards);

        List<LeaderCard> leaderCardsDeck = new ArrayList();
        for (int i = 0; i < 4; i++) {
            if (!leaderCards.isEmpty())
                leaderCardsDeck.add(leaderCards.pop());
        }

        player.setLeaderCards(leaderCardsDeck);
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
