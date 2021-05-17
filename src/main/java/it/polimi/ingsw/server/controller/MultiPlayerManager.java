package it.polimi.ingsw.server.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageFactory;
import it.polimi.ingsw.messages.updateFromServer.ErrorMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.PlayerNotExistsException;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.model.multiplayer.MultiPlayerGameInstance;
import it.polimi.ingsw.model.parser.LeaderCardParser;

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
        this.turnManager.setMultiplayer(true);
        this.turnManager.setPlayers(this.players);
    }

    public MultiPlayerManager() {
    }



    @Override
    public Integer getGameId() {
        return this.game.getGameId();
    }

    /**
     * Manages the game allowing players to do actions
     */
    @Override
    public void manageTurn(){

        //TODO switch case to handle methods that increment faith path

        this.welcome();

        for(Player player : players) {
            boolean turnEnded = false;

            while(!turnEnded) {

                String clientRequest = "";
                try {
                    if(player.getFromClient().available() > 0)
                        clientRequest = player.getFromClient().readUTF();
                } catch (IOException e) {
                    System.err.println("Exception occurred while sending file");
                }
                if (clientRequest.isEmpty()) {
                    Message toSend = new ErrorMessage(player.getNickname(), "Exception occurred while receiving json");
                    toSend.process(player, this.turnManager);
                }

                MessageFactory messageFactory = new MessageFactory();
                Message receivedMessage = messageFactory.returnMessage(clientRequest);

                if (player.getNickname().equals(receivedMessage.getNickname())) {
                    turnEnded = receivedMessage.process(player, this.turnManager);
                } else {
                    Message outcome = new ErrorMessage(player.getNickname(), "Wait: it is not you turn");
                    outcome.process(player, this.turnManager);
                }
            }
        }
    }

    @Override
    public void manageSetUp() {
    }

    /**
     * if there is a Message request from a client that, if successfully handled, requires incrementing the user faith path. The method is called to
     * update the other users about the new user faith path position.
     * CAUTION: the methods needs to be called for each increment of the user's faith path in order to
     * correctly update the other player's faith paths.
     * @param player the player incrementing his position
     */
    public void updateUsersFaithPath(Player player){
        Integer newPlayingUserFaithPathPosition = player.getFaithPathPosition();
        for(Player p : players)
            p.updateFaithPath(newPlayingUserFaithPathPosition);
    }


    public static void main(String[] args) {
        MultiPlayerManager multi = new MultiPlayerManager();
        Gson gson = new Gson();

        multi.manageTurn();

    }

    /**
     * Assigns calamaio randomly
     */
    @Override
    public void setCalamaio() {
        Random rand = new Random();
        int randomPlayer = rand.nextInt(players.size());
        players.get(randomPlayer).setCalamaio();

        for (Player player: players){
            if(player.hasCalamaio()) {
                try {
                    player.getToClient().writeUTF("HASCALAMAIO");
                } catch (IOException e) {
                    System.err.println("Exception occurred while sending file");
                }
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

        //TODO update leader cards in order to work with json file
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

    @Override
    public void welcome() {
        for(Player player : players){
            try {
                player.getToClient().writeUTF("OK IN GAME");
            } catch(IOException e){
                System.err.println("Exception occurred while sending file");
            }
        }
    }

    /**
     * @param player the player that wants to activate a Leader card of type STORAGE
     * @param HashMapResources the activation cost of the leader card
     * @return true if the player has sufficient resources to activate a leader card of type STORAGE
     */
    public static boolean verifyToActivateLeaderCard(Player player, HashMap<Resource, Integer> HashMapResources){

        List<Resource> playerResources = player.getTotalResource();
        ArrayList<Resource> resourcesList = resourcesFromHashMapToList(HashMapResources);

        return playerResources.containsAll(resourcesList);
    }

    /**
     * Ausiliar method of the method verifyToActivateLeaderCard, converts a hashMap of resources into a list of
     * resources converting the value number of each resource into a real resource objects in the List
     * @param HashMapResources a hashMap of resources
     * @return a List of resources
     */
    public static ArrayList<Resource> resourcesFromHashMapToList(HashMap<Resource, Integer> HashMapResources){
        ArrayList<Resource> returnList = new ArrayList<>();
        for (Resource resource : HashMapResources.keySet()){
            for (int i=0 ; i<HashMapResources.get(resource) ; i++){
                returnList.add(resource);
            }

        }
        return returnList;
    }


    public Player getPlayerFromNickname(String nickname) throws PlayerNotExistsException {
        for (Player player: players){
            if (player.getNickname().equals(nickname))  return player;
        }
        throw new PlayerNotExistsException();
    }



}
