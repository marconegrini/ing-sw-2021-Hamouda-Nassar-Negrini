package it.polimi.ingsw.server.handlers;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.messages.fromServer.update.*;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.ANSITextFormat;
import it.polimi.ingsw.model.exceptions.MaxPlayersException;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.model.multiplayer.MultiPlayerGameInstance;
import it.polimi.ingsw.model.parser.LeaderCardParser;
import it.polimi.ingsw.server.controller.TurnManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.util.*;

public class MultiPlayerGameHandler extends Thread {

    private List<ClientHandler> clientHandlers;
    private TurnManager turnManager;
    private final MultiPlayerGameInstance game;
    private boolean gameEnded;

    /**
     * Initialises game instance ad turn manager, setting it to multiplayer mode. Adds players to the game
     * @param clientHandlers list of client handlers
     */
    public MultiPlayerGameHandler(List<ClientHandler> clientHandlers) {
        this.clientHandlers = clientHandlers;
        game = new MultiPlayerGameInstance();
        turnManager = new TurnManager(game.getCardsDeck(), game.getMarketBoard());
        turnManager.setMultiplayer(true);
        turnManager.setPlayers(game.getPlayer());
        for (ClientHandler ch : clientHandlers) {
            MultiPlayer player = new MultiPlayer(ch.getNickname());
            ch.setPlayer(player);
            ch.setTurnManager(turnManager);
            try {
                game.addPlayer(player);
            } catch (MaxPlayersException ignored) {
            }
        }
        gameEnded = false;
    }

    /**
     * Administers the game, looping on players and checking at the ent of each turn of someone's reach
     * the end of faith path or bought seven development cards
     */
    @Override
    public void run() {
        System.out.println("Multiplayer game started");

        sendToClients(new StartGameMessage());

        for (ClientHandler ch : clientHandlers) {
            System.out.println("\nPlayer " + ch.getNickname());
            MultiPlayer player = (MultiPlayer) ch.getPlayer();
            player.printPlayer();
        }
        sendLeaderCards();
        turnManager.lock();
        initialiseCalamaio();
        turnManager.lock();
        sendToClients(new GameStartedMessage());
        updateClients();

        //sendToClients(new SelectActionMessage());

        while (!gameEnded){
            for (ClientHandler ch : clientHandlers) {
                sendToClient(ch, new SelectActionMessage());
                sendToClients(new OkMessage("Wait your turn..."), ch);
                turnManager.lock();
                updateClients();
                //enters last turn
                if(turnManager.reachedFaithPathEnd()){
                    gameEnded = true;
                    MultiPlayer player = turnManager.getFirstPlayerToEndFaithPath();
                    ClientHandler firstToFinish = null;
                    for(ClientHandler ch1 : clientHandlers)
                        if(ch1.getNickname().equals(player.getNickname()))
                            firstToFinish = ch1;
                    sendToClients(new OkMessage(firstToFinish.getNickname() + " reached the end of faith path!"));
                    Integer playerIndex = clientHandlers.indexOf(firstToFinish);
                    for(int i = 0; i < playerIndex +1; i++)
                        sendToClient(clientHandlers.get(i), new OkMessage("Wait for other players to finish their turn..."));
                    for(int i = (playerIndex + 1); i < clientHandlers.size(); i++){
                        sendToClient(clientHandlers.get(i), new SelectActionMessage());
                        for(int j = i + 1; j < clientHandlers.size(); j++)
                            sendToClient(clientHandlers.get(j), new OkMessage("Wait your turn..."));
                        turnManager.lock();
                        updateClients();
                        sendToClient(clientHandlers.get(i), new OkMessage("This was your last turn. Wait for the other players to finish"));
                    }
                    MultiPlayer winner = null;
                    Integer max = 0;
                    for(ClientHandler ch2 : clientHandlers){
                        if(ch2.getPlayer().getTotalVictoryPoints() > max) {
                            max = ch2.getPlayer().getTotalVictoryPoints();
                            winner = (MultiPlayer) ch2.getPlayer();
                        }
                    }
                    for(ClientHandler ch3 : clientHandlers){
                        if(ch3.getPlayer().getNickname().equals(winner.getNickname())) {
                            sendToClient(ch3, new EndGameMessage("You win! You made " + ch3.getPlayer().getTotalVictoryPoints() + " victory points."));
                        } else sendToClient(ch3, new EndGameMessage("You lost! " + winner.getNickname() + " made " + winner.getTotalVictoryPoints() + " victory points.\n You made instead " + ch3.getPlayer().getTotalVictoryPoints() + " victory points."));
                    }
                }
            }
        }
    }

    /**
     * Parses leader cards and puts them in a Stack. After shuffling the stack, sends an
     * ArrayList of 4 leader cards to all clients.
     */
    public void sendLeaderCards() {
        LeaderCardParser parser = new LeaderCardParser("src/main/java/it/polimi/ingsw/model/jsonFiles/LeaderCardJson.json");
        Stack<LeaderCard> deck = new Stack<>();
        deck = parser.getLeaderCardsDeck();
        Collections.shuffle(deck);
        for (ClientHandler ch : clientHandlers) {
            List<LeaderCard> leaderCards = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                if (!deck.isEmpty())
                    leaderCards.add(deck.pop());
            }
            ch.getPlayer().setLeaderCards(leaderCards);
            sendToClient(ch, new ChooseLeaderCardMessage(leaderCards));
        }
    }

    /**
     * Sends message to the specified client handler
     * @param clientHandler
     * @param message
     */
    public void sendToClient(ClientHandler clientHandler, ServerMessage message) {
        clientHandler.sendJson(message);
    }

    /**
     * Sends a broadcast message
     * @param message
     */
    public void sendToClients(ServerMessage message) {
        for (ClientHandler ch : clientHandlers)
            ch.sendJson(message);
    }

    /**
     * Sends a broadcast message except for one client
     * @param message
     * @param exception
     */
    public void sendToClients(ServerMessage message, ClientHandler exception) {
        for (ClientHandler ch : clientHandlers)
            if (!ch.equals(exception))
                ch.sendJson(message);
    }

    /**
     * Update clients' light models structures
     */
    public void updateClients(){
        sendToClients(new UpdateMarketboardMessage(game.getMarketBoard()));
        sendToClients(new UpdateDevCardsDeckMessage(game.peekCardsDeck()));
        for(ClientHandler ch : clientHandlers){
            HashMap<String, Integer> faithPathPositions = this.getFaithPathPositions();
            faithPathPositions.remove(ch.getNickname());
            sendToClient(ch, new UpdateLeaderCardStatusMessage(ch.getPlayer().getLeaderCards()));
            sendToClient(ch, new UpdateFaithPathMessage(faithPathPositions, ch.getPlayer().getFaithPathPosition()));
            sendToClient(ch, new UpdateWarehouseCofferMessage(ch.getPlayer().getClonedWarehouse(), ch.getPlayer().getClonedCoffer()));
            sendToClient(ch, new UpdateDevCardsSlotMessage(ch.getPlayer().getPeekCardsInDevCardSLots()));
            sendToClient(ch, new UpdateVaticanSectionsMessage(ch.getPlayer().getVaticanSections()));
        }
    }

    /**
     *
     * @return an HashMap containing users faith path positions
     */
    public HashMap<String, Integer> getFaithPathPositions(){
        HashMap<String, Integer> faithPathPositions = new HashMap<>();
        for(ClientHandler ch : clientHandlers){
            String nickname = ch.getNickname();
            Integer fpPos = ch.getPlayer().getFaithPathPosition();
            faithPathPositions.put(nickname, fpPos);
        }
        return faithPathPositions;
    }

    /**
     * puts the client with the calamaio at the beginning of the list
     */
    public void reOrdinateClientHandlers() {
        ArrayList<ClientHandler> tempArr = new ArrayList<>();
        ClientHandler searchedCH = clientHandlers.stream().filter(x -> x.getPlayer().hasCalamaio()).findFirst().orElseGet(null);
        if(searchedCH==null){
            System.out.println("Null pointer Exception");
            System.exit(-2);
        }
        tempArr.add(0, searchedCH);
        clientHandlers.remove(searchedCH);
        tempArr.addAll(clientHandlers);

        if (clientHandlers.size() == tempArr.size() -1 ) {
            clientHandlers = tempArr;
        } else {
            System.out.println("error while reOrdinating clientHandlers List after setting the Calamaio");
            System.exit(-2);
        }

    }


    /**
     * Takes a random user to assign calamaio and notify everyone about the status in turn order
     */
    public void initialiseCalamaio() {
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        int i = rand.nextInt(clientHandlers.size());
        clientHandlers.get(i).getPlayer().setCalamaio();

        //puts the player with the calamaio the first in the list.
        reOrdinateClientHandlers();
        String strIn="";
        int k = 0;
        while ((k < clientHandlers.size())){
            if (k==0) {
                strIn= ANSITextFormat.BOLD + "You are the player with the Calamaio and your turn is the first!" + ANSITextFormat.RESET.toString() +"\nYou have not received any additional resources\n";
                sendToClient(clientHandlers.get(k), new InitializeCalamaioMessage(strIn));
                }
            else if(k==1){
                strIn= ANSITextFormat.BOLD.toString()+"Your Turn is the second!"+ANSITextFormat.RESET.toString()+"\nYou have received "+ ANSITextFormat.UNDERLINE.toString()+"one additional resources"+ANSITextFormat.RESET.toString()+" \nPlease chose which Resource do you want:\n";
                sendToClient(clientHandlers.get(k), new InitializeCalamaioMessage(strIn));
            }
            else if(k==2){
                strIn=ANSITextFormat.BOLD.toString()+"Your Turn is the third!" +ANSITextFormat.RESET.toString() + "\nYou have received "+ANSITextFormat.UNDERLINE.toString()+"one additional resource"+ANSITextFormat.RESET.toString()+" and "+ ""+ANSITextFormat.RED_COLOR.toString()+"" + "a Faith Point"+ANSITextFormat.RESET.toString()+"\nPlease chose which Resource do you want:\n";
                clientHandlers.get(k).getPlayer().incrementFaithPathPosition();
                sendToClient(clientHandlers.get(k), new InitializeCalamaioMessage(strIn));
            }
            else if(k==3){
                strIn=ANSITextFormat.BOLD.toString() + "Your Turn is the fourth!" +ANSITextFormat.RESET.toString()+"\nYou have received "+ANSITextFormat.UNDERLINE.toString()+"two additional resources"+ANSITextFormat.RESET.toString()+" and "+""+ANSITextFormat.RED_COLOR.toString()+""+"a Faith Point"+ANSITextFormat.RESET.toString()+" \nPlease chose which Resource do you want:\n";
                clientHandlers.get(k).getPlayer().incrementFaithPathPosition();
                sendToClient(clientHandlers.get(k), new InitializeCalamaioMessage(strIn));
            }
            k++;
        }
    }



}
