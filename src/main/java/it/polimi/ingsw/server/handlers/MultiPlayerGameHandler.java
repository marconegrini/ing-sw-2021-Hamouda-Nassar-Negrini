package it.polimi.ingsw.server.handlers;

import it.polimi.ingsw.messages.fromServer.EndMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.model.exceptions.MaxPlayersException;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.model.multiplayer.MultiPlayerGameInstance;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;
import it.polimi.ingsw.server.controller.TurnManager;

import java.util.List;
import java.util.Random;

public class MultiPlayerGameHandler extends Thread {

    private List<ClientHandler> clientHandlers;
    private TurnManager turnManager;
    private final MultiPlayerGameInstance game;

    public MultiPlayerGameHandler(List<ClientHandler> clientHandlers) {
        this.clientHandlers = clientHandlers;
        game = new MultiPlayerGameInstance();
        for (ClientHandler ch : clientHandlers) {
            MultiPlayer player = new MultiPlayer(ch.getNickname());
            ch.setPlayer(player);
            ch.setTurnManager(turnManager);
            try {
                game.addPlayer(player);
            } catch (MaxPlayersException e) {}
        }
        turnManager = new TurnManager(game.getCardsDeck(), game.getMarketBoard());
        turnManager.setMultiplayer(true);
        turnManager.setPlayers(game.getPlayer());
    }

    @Override
    public void run() {
        System.out.println("Multiplayer game started");
        for(ClientHandler ch : clientHandlers){
            System.out.println("\nPlayer " + ch.getNickname());
            MultiPlayer player = (MultiPlayer) ch.getPlayer();
            player.printPlayer();
        }






        for(ClientHandler ch : clientHandlers)
            ch.sendJson(new EndMessage());
    }



    public void sendToPlayers(ServerMessage message){
        for(ClientHandler ch : clientHandlers)
            ch.sendJson(message);
    }

    public void sendToPlayers(ServerMessage message, ClientHandler exception){
        for(ClientHandler ch : clientHandlers)
            if(!ch.equals(exception))
                ch.sendJson(message);
    }

    public void sendToPlayer(ClientHandler clientHandler, ServerMessage message){
        clientHandler.sendJson(message);
    }


}
