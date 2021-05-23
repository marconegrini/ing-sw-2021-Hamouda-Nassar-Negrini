package it.polimi.ingsw.server.handlers;

import it.polimi.ingsw.model.singleplayer.SinglePlayer;
import it.polimi.ingsw.model.singleplayer.SinglePlayerGameInstance;
import it.polimi.ingsw.server.controller.TurnManager;

public class SinglePlayerGameHandler extends Thread{

    private ClientHandler clientHandler;
    private TurnManager turnManager;
    private final SinglePlayerGameInstance game;

    public SinglePlayerGameHandler(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
        game = new SinglePlayerGameInstance();
        SinglePlayer player = new SinglePlayer(clientHandler.getNickname());
        game.addPlayer(player);
        clientHandler.setPlayer(player);
        turnManager = new TurnManager(game.getCardsDeck(), game.getMarketBoard());
        turnManager.setMultiplayer(false);
    }

    @Override
    public void run(){}
}
