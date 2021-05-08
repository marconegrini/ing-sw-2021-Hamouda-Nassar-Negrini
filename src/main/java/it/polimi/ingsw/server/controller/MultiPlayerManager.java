package it.polimi.ingsw.server.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.server.controller.messages.Message;
import it.polimi.ingsw.server.controller.messages.PickResourcesMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.server.model.multiplayer.MultiPlayerGameInstance;
import it.polimi.ingsw.server.model.parser.LeaderCardParser;

import java.io.IOException;
import java.util.*;

public class MultiPlayerManager extends GameManager {

    private MultiPlayerGameInstance game;

    private List<MultiPlayer> players;

    /**
     * Initializes game and players instances. Creates a new turnManager that will performs turns between players
     * @param game the game instance
     */
    public MultiPlayerManager(MultiPlayerGameInstance game){
        this.game = game;
        this.players = game.getPlayer();
        this.turnManager = new TurnManager(game.getCardsDeck(), game.getMarketBoard());
    }
//
//    public MultiPlayerManager(){
//        this.game = null;
//        this.players = null;
//        this.turnManager = null;
//  }

    @Override
    public Integer getGameId() {
        return this.game.getGameId();
    }

    /**
     * Manages the game allowing players to do actions
     */
    @Override
    public void manageTurn(){

        //this.setLeaderCards();
        //this.setCalamaio();

        Gson gson = new Gson();

        Message message = gson.fromJson("{\"isRow\":true,\"rowOrColNum\":1,\"nickname\":\"Nome\",\"messageType\":\"PICKRESOURCES\"}", PickResourcesMessage.class);

        message.process();

    }

//
//    public static void main(String[] args) {
//        MultiPlayerManager multi = new MultiPlayerManager();
//        Gson gson = new Gson();
//        PickResourcesMessage message = new PickResourcesMessage("Nome", true, 1);
//
//        System.out.println(gson.toJson(message));
//        multi.manageTurn();
//
//    }

////    public static Player retrievePlayerFromNickname(String nickname){
////        return players.stream().findAny();
//    }

    /**
     * Assigns calamaio randomly
     */
    @Override
    public void setCalamaio() {
        Random rand = new Random();
        Integer randomPlayer = rand.nextInt(players.size());
        players.get(randomPlayer).setCalamaio();

        for (Player player: players){

            try {
                if (player.hasCalamaio()) {
                    player.getToClient().writeUTF("CALAMAIO");
                }
            } catch (IOException e){
                System.err.println("Exception occurred while sending file");
            }
        }
        //TODO receive from players selected leader cards
    }

    /**
     * Sends arraylist of 4 leader cards to each playing player
     */
    @Override
    public void setLeaderCards(){

        LeaderCardParser parser = new LeaderCardParser("src/main/java/it/polimi/ingsw/model/jsonFiles/LeaderCardJson.json");
        Stack<LeaderCard> leaderCards = parser.getLeaderCardsDeck();
        parser.close();
        Collections.shuffle(leaderCards);

        for(MultiPlayer player : players){
     //transferring the cards from the stack to the arrayList of 4 cards(the initial four cards to choose between them)
            List<LeaderCard> leaderCardsDeck = new ArrayList();

            for(int i = 0; i < 4; i++) {
                if (!leaderCards.isEmpty()) {
                    Gson gson = new Gson();
                    LeaderCard leaderCard = leaderCards.pop();
                    String stringJson = gson.toJson(leaderCard);
                    try {
                        player.getToClient().writeUTF(stringJson);
                    } catch (IOException e) {
                        System.err.println("Exception occurred while sending json");
                        e.printStackTrace();
                    }
                }
            }
            try {
                player.getToClient().writeUTF("OK");
            } catch (IOException e) {
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
     * Counts victory points of all players
     */
    @Override
    public void countVictoryPoints() {

    }
}
