package it.polimi.ingsw.server.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.parser.LeaderCardParser;
import it.polimi.ingsw.server.model.singleplayer.SinglePlayerGameInstance;

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

        this.setLeaderCards();
        this.setCalamaio();

        try{
            player.getToClient().writeUTF("OK IN GAME");
        } catch (IOException e){
            e.printStackTrace();
        }


    }

    @Override
    public void setCalamaio() {
        this.player.hasCalamaio();
    }

    @Override
    public void setLeaderCards() {

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

        Gson gson = new Gson();
        String stringJson = gson.toJson(leaderCardsDeck);

        try {
            player.getToClient().writeUTF(stringJson);
        } catch(IOException e){
            System.err.println("Exception occurred while sending json");
            e.printStackTrace();
        }

        //TODO receiving the choosen leader cards

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
