package it.polimi.ingsw.server.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.enumerations.CardType;
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
        this.turnManager = new TurnManager(game.getCardsDeck(), game.getMarketBoard());
        this.player = null; //since when instantiating new game manager the player inside gameInstance is null
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

        try{
            player.getToClient().writeUTF("OK IN GAME");
        } catch (IOException e){
            e.printStackTrace();
        }

        this.setLeaderCards();
        //this.setCalamaio();

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

        for(int i = 0; i < 4; i++) {
            if (!leaderCards.isEmpty()) {
                LeaderCard lc = leaderCards.pop();
                Gson gson = new Gson();
                String stringJson = gson.toJson(lc);
                if(lc.getCardType().equals(CardType.DISCOUNT)){
                    try {
                        player.getToClient().writeUTF("DISCOUNT");
                        player.getToClient().writeUTF(stringJson);
                    } catch(IOException e){
                        System.err.println("Exception occurred while sending json");
                    }
                }
                if(lc.getCardType().equals(CardType.MARBLE)){
                    try {
                        player.getToClient().writeUTF("MARBLE");
                        player.getToClient().writeUTF(stringJson);
                    } catch(IOException e){
                        System.err.println("Exception occurred while sending json");
                    }
                }
                if(lc.getCardType().equals(CardType.PRODUCTION)){
                    try {
                        player.getToClient().writeUTF("PRODUCTION");

                        player.getToClient().writeUTF(stringJson);
                    } catch(IOException e){
                        System.err.println("Exception occurred while sending json");
                    }
                }
                if(lc.getCardType().equals(CardType.STORAGE)){
                    try {
                        player.getToClient().writeUTF("STORAGE");
                        player.getToClient().writeUTF(stringJson);
                    } catch(IOException e){
                        System.err.println("Exception occurred while sending json");
                    }
                }


            }
        }
        try {
            player.getToClient().writeUTF("OK");
        } catch(IOException e){
            System.err.println("Exception occurred while sending message");
            e.printStackTrace();
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
