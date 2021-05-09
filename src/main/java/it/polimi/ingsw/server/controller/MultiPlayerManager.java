package it.polimi.ingsw.server.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.server.controller.messages.Message;
import it.polimi.ingsw.server.controller.messages.MessageFactory;
import it.polimi.ingsw.server.controller.messages.PickResourcesMessage;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.cards.LeaderCards.CardsCompositionMethods;
import it.polimi.ingsw.server.model.enumerations.CardType;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.PlayerNotExistsException;
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

        //this.setLeaderCards();
        //this.setCalamaio();

        for(Player player : players){
            try {
                player.getToClient().writeUTF("OK IN GAME");
            } catch(IOException e){
                System.err.println("Exception occurred while sending file");
            }
        }

        this.setLeaderCards();

        /*
        Gson gson = new Gson();
        MessageFactory messageFactory = new MessageFactory();
        Message message = messageFactory.messageFactory("{\"isRow\":true,\"rowOrColNum\":1,\"nickname\":\"Nome\",\"messageType\":\"PICKRESOURCES\"}");

        message.process();
         */

    }


    public static void main(String[] args) {
        MultiPlayerManager multi = new MultiPlayerManager();
        Gson gson = new Gson();

        multi.manageTurn();

    }

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

        LeaderCardParser parser = new LeaderCardParser("src/main/java/it/polimi/ingsw/server/model/jsonFiles/LeaderCardJson.json");
        Stack<LeaderCard> leaderCards = parser.getLeaderCardsDeck();
        parser.close();
        Collections.shuffle(leaderCards);

        //TODO update leader cards in order to work with json file
        for(MultiPlayer player : players){
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

        //receiving from clients selected leader cards
        /*
        for(Player player : players){
            try {
                for(int i = 0; i < players.size(); i++){
                    if(!players.get(i).equals(player)){
                        players.get(i).getToClient().writeUTF("WAIT");
                        players.get(i).getToClient().writeUTF("Wait while other clients choose leaderCards: think what to pick!");
                    }
                }
                player.getToClient().writeUTF("LEADERCARD");
                player.getToClient().writeUTF("Select 2 leader cards (with index between 1 and 4): ");
            } catch(IOException e){
                e.printStackTrace();
            }

            List<Integer> chosenIndexes = new ArrayList<>();
            int cardsSelected = 0;
            while(cardsSelected != 2) {
                String message;
                Integer index = null;
                try {
                    message = player.getFromClient().readUTF();
                    index = Integer.valueOf(message);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (NumberFormatException e2){
                    e2.printStackTrace();
                }
                chosenIndexes.add(index);
                cardsSelected++;
            }

            List<LeaderCard> selected = leaderCards.stream().filter(x -> (x.equals(leaderCards.get(chosenIndexes.get(0))) || x.equals(leaderCards.get(chosenIndexes.get(1))))).collect(Collectors.toList());


        }
         */
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


    /**
     *
     * @param player the player that wants to activate a Leader card of type STORAGE
     * @param HashMapResources the activation cost of the leader card
     * @return true if the player has sufficient resources to activate a leader card of type STORAGE
     */
    public static boolean verifyToActivateLeaderCard(Player player, HashMap<Resource, Integer> HashMapResources){

        List<Resource> playerResources = player.getTotalResource();
        ArrayList<Resource> resourcesList = resourcesFromHashMapToList(HashMapResources);

        if(playerResources.containsAll(resourcesList)){
            return true;
        }
        else return false;
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
