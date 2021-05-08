package it.polimi.ingsw.server.handlers;

import it.polimi.ingsw.server.controller.SinglePlayerManager;
import it.polimi.ingsw.server.model.singleplayer.SinglePlayerGameInstance;

public class SinglePlayerGameHandler extends Thread{

    private final SinglePlayerGameInstance gameInstance;
    private final SinglePlayerManager manager;

    public SinglePlayerGameHandler(SinglePlayerGameInstance gameInstance, SinglePlayerManager manager){
        this.gameInstance = gameInstance;
        this.manager = manager;
    }

    public void run(){
        System.out.println("\n\nGameInstance: " + gameInstance);
        manager.manageTurn();
        gameInstance.printGamePlayer();
    }
}
