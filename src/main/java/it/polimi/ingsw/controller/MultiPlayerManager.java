package it.polimi.ingsw.controller;

import com.google.gson.Gson;
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

        this.setLeaderCards();
        this.setCalamaio();

    }

    @Override
    public void setCalamaio() {
        Random rand = new Random();
        Integer randomPlayer = rand.nextInt(players.size());
        players.get(randomPlayer).setCalamaio();

        for (Player player: players){
            Message okInGame = new Message("You are in the game");
            okInGame.sendJson(player);

            if (player.hasCalamaio()){
                Message hasCalamaio = new Message("You have calamaio");
                hasCalamaio.sendJson(player);
            }
        }
        //TODO receive from players selected leader cards
    }

    @Override
    public void setLeaderCards(){

        LeaderCardParser parser = new LeaderCardParser("src/main/java/it/polimi/ingsw/model/jsonFiles/LeaderCardJson.json");
        Stack<LeaderCard> leaderCards = parser.getLeaderCardsDeck();
        parser.close();
        Collections.shuffle(leaderCards);

        for(MultiPlayer player : players){

            List<LeaderCard> leaderCardsDeck = new ArrayList();

            for(int i = 0; i < 4; i++)
                if(!leaderCards.isEmpty())
                    leaderCardsDeck.add(leaderCards.pop());

            Gson gson = new Gson();
            String stringJson = gson.toJson(leaderCardsDeck);

            try {
                player.getToClient().writeUTF(stringJson);
            } catch(IOException e){
                System.err.println("Exception occurred while sending json");
                e.printStackTrace();
            }
        }

        //TODO receive choosen leader cards from players
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
