package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.model.multiplayer.MultiPlayerGameInstance;
import it.polimi.ingsw.model.parser.LeaderCardParser;

import java.io.IOException;
import java.util.*;

public class MultiPlayerManager extends GameManager {

    private MultiPlayerGameInstance game;

    private List<MultiPlayer> players;

    public MultiPlayerManager(MultiPlayerGameInstance game){
        this.game = game;
        this.players = game.getPlayer();
        this.turnManager = new TurnManager(game.getCardsDeck(), game.getMarketBoard());
        this.manageTurn();
    }

    @Override
    public Integer getGameId() {
        return this.game.getGameId();
    }

    /**
     * Manage the game allowing players to do actions
     */
    @Override
    public void manageTurn(){

        this.setUp();

        try{
            for (Player player: players){
                player.getToClient().writeUTF("OK IN GAME");
                if (player.hasCalamaio()){
                    player.getToClient().writeUTF("HASCALAMAIO");
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void setUp() {
        Random rand = new Random();
        Integer randomPlayer = rand.nextInt(players.size());
        players.get(randomPlayer).setCalamaio();

        LeaderCardParser parser = new LeaderCardParser("src/main/java/it/polimi/ingsw/model/jsonFiles/LeaderCardJson.json");
        Stack<LeaderCard> leaderCards = parser.getLeaderCardsDeck();
        parser.close();
        Collections.shuffle(leaderCards);

        for(MultiPlayer player : players){

            List<LeaderCard> leaderCardsDeck = new ArrayList();
            for(int i = 0; i < 4; i++)
                if(!leaderCards.isEmpty())
                    leaderCardsDeck.add(leaderCards.pop());

            player.setLeaderCards(leaderCardsDeck);
        }
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
