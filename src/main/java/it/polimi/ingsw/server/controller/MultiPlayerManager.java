package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageFactory;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.PingMessage;
import it.polimi.ingsw.messages.updateFromServer.UpdateLeaderCardMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.model.multiplayer.MultiPlayerGameInstance;
import it.polimi.ingsw.model.parser.LeaderCardParser;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MultiPlayerManager extends GameManager {

    private MultiPlayerGameInstance game;
    private List<MultiPlayer> players;
    private Queue<Message> serverBuffer;
    private List<ClientTimeOut> timers;

    /**
     * Initializes game and players instances. Creates a new turnManager that will performs turns between players
     * @param game the game instance
     */
    public MultiPlayerManager(MultiPlayerGameInstance game){
        this.game = game;
        this.players = game.getPlayer();
        this.serverBuffer = new ConcurrentLinkedQueue();
        this.turnManager = new TurnManager(game.getCardsDeck(), game.getMarketBoard());
        this.turnManager.setMultiplayer(true);
        this.turnManager.setPlayers(this.players);
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
        MessageFactory factory = new MessageFactory();
        this.welcome();
        /*
        this.firstPing();

        Runnable dequeue = new Runnable() {

            @Override
            public void run() {
                Message fromClient;
                while(true) {
                    if(!serverBuffer.isEmpty()) {
                        fromClient = serverBuffer.poll();
                        System.out.println(fromClient.toString());
                        MessageType mt = fromClient.getMessageType();
                        if(mt.equals(MessageType.PING)){
                            Player player = getPlayerByNickname(fromClient.getNickname());
                            Message ping = new PingMessage(player.getNickname());
                            ping.serverProcess(player, turnManager);
                            resetClientTimeOut(player.getNickname());
                        }
                    }

                    //checks if there are clientTimeOut ended
                    for (ClientTimeOut cto : timers) {
                        if (!cto.isValid()) {
                            for (Message message : serverBuffer)
                                if (message.getNickname() == cto.getNickname())
                                    serverBuffer.remove(message);
                            Player player = getPlayerByNickname(cto.getNickname());
                            players.remove(player);
                            System.out.println("Connection lost with player " + cto.getNickname());
                        }
                    }
                }
            }
        };

        Runnable enqueue = new Runnable() {

            @Override
            public void run() {
                String clientMessage = null;
                while (true) {
                    for(Player player : players) {
                        try {
                            if(player.getFromClient().available() > 0) {
                                clientMessage = player.getFromClient().readUTF();
                                Message message = factory.returnMessage(clientMessage);
                                serverBuffer.add(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Thread t1 = new Thread(dequeue);
        Thread t2 = new Thread(enqueue);
        t1.start();
        t2.start();

         */

    }

    @Override
    public void firstPing(){
        for(Player p : players){
            ClientTimeOut timeOut = new ClientTimeOut(p.getNickname());
            timers.add(timeOut);
            Message ping = new PingMessage(p.getNickname());
            ping.serverProcess(p, turnManager);
            timeOut.resetTime();
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
        Stack<LeaderCard> leaderCardsStack = parser.getLeaderCardsDeck();
        parser.close();
        Collections.shuffle(leaderCardsStack);
        //setting up player's leader card in model
        for(Player player : players){
            List<LeaderCard> leaderCardList = new ArrayList<>();
            for(int i = 0; i < 4; i++){
                if(!leaderCardsStack.isEmpty())
                    leaderCardList.add(leaderCardsStack.pop());
            }
            player.setLeaderCards(leaderCardList);
            UpdateLeaderCardMessage sendCards = new UpdateLeaderCardMessage(player.getNickname(), leaderCardList);
            sendCards.serverProcess(player, turnManager);
        }

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


    public Player getPlayerByNickname(String nickname){
        Player p = null;
        for (Player player: players){
            if (player.getNickname().equals(nickname))  p = player;
        }
        return p;
    }

    public void resetClientTimeOut(String nickname){
        ClientTimeOut cto = null;
        for(ClientTimeOut c : timers){
            if(c.getNickname().equals(nickname)) {
                c.resetTime();
            }
        }
    }



}
