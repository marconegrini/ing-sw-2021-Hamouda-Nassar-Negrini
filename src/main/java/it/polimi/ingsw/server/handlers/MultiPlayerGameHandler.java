package it.polimi.ingsw.server.handlers;

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
    }

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
        updateClients();

        //sendToClients(new SelectActionMessage());

        while (true){
            for (ClientHandler ch : clientHandlers) {
                sendToClient(ch, new SelectActionMessage());
                sendToClients(new OkMessage("Wait your turn..."), ch);
                turnManager.lock();
                updateClients();
            }
        }
/*
        for(ClientHandler ch : clientHandlers)
            ch.sendJson(new EndMessage());

 */
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

    public void sendToClient(ClientHandler clientHandler, ServerMessage message) {
        clientHandler.sendJson(message);
    }

    public void sendToClients(ServerMessage message) {
        for (ClientHandler ch : clientHandlers)
            ch.sendJson(message);
    }

    public void sendToClients(ServerMessage message, ClientHandler exception) {
        for (ClientHandler ch : clientHandlers)
            if (!ch.equals(exception))
                ch.sendJson(message);
    }

    public void updateClients(){
        sendToClients(new UpdateMarketboardMessage(game.getMarketBoard()));
        sendToClients(new UpdateDevCardsDeckMessage(game.peekCardsDeck()));
        for(ClientHandler ch : clientHandlers){
            HashMap<String, Integer> faithPathPositions = this.getFaithPathPositions();
            faithPathPositions.remove(ch.getNickname());
            sendToClient(ch, new UpdateFaithPathMessage(faithPathPositions, ch.getPlayer().getFaithPathPosition()));
            sendToClient(ch, new UpdateWarehouseCofferMessage(ch.getPlayer().getClonedWarehouse(), ch.getPlayer().getClonedCoffer()));
            sendToClient(ch, new UpdateDevCardsSlotMessage(ch.getPlayer().getPeekCardsInDevCardSLots()));
            sendToClient(ch, new UpdateVaticanSectionsMessage(ch.getPlayer().getVaticanSections()));
        }
    }

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
