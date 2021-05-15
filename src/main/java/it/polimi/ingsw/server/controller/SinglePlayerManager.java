package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.parser.LeaderCardParser;
import it.polimi.ingsw.model.singleplayer.SinglePlayerGameInstance;

import java.io.IOException;
import java.util.Collections;
import java.util.Stack;

public class SinglePlayerManager extends GameManager {

    private SinglePlayerGameInstance game;
    private Player player;

    public SinglePlayerManager(SinglePlayerGameInstance game){
        this.game = game;
        this.turnManager = new TurnManager(game.getCardsDeck(), game.getMarketBoard());
        this.player = null; //since when instantiating new game manager the player inside gameInstance is null
        this.turnManager.setMultiplayer(false);
    }

    public void setPlayer(Player player){
        this.player = player;
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

        this.welcome();

    }

    @Override
    public void manageSetUp() {

    }

    @Override
    public void setCalamaio() {
        this.player.hasCalamaio();
        try{
            this.player.getToClient().writeUTF("HASCALAMAIO");
        }catch(IOException e){
            System.out.println("Error occurred while sending file");
        }
    }

    @Override
    public void setLeaderCards() {
        this.player = game.getPlayer();

        LeaderCardParser parser = new LeaderCardParser("src/main/java/it/polimi/ingsw/server/model/jsonFiles/LeaderCardJson.json");
        Stack<LeaderCard> leaderCards = parser.getLeaderCardsDeck();
        parser.close();

        Collections.shuffle(leaderCards);
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

    @Override
    public void welcome() {
        try{
            player.getToClient().writeUTF("OK IN GAME");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
