package it.polimi.ingsw.server.handlers;

import it.polimi.ingsw.server.controller.MultiPlayerManager;
import it.polimi.ingsw.model.multiplayer.MultiPlayerGameInstance;

public class MultiPlayerGameHandler extends Thread{

    private final MultiPlayerGameInstance gameInstance;
    private final MultiPlayerManager manager;

    public MultiPlayerGameHandler(MultiPlayerGameInstance gameInstance, MultiPlayerManager manager){
        this.gameInstance = gameInstance;
        this.manager = manager;
    }

    public void run(){
        manager.manageTurn();
        System.out.println("\n\nGameInstance: " + gameInstance);
        System.out.println("Players in the game: " + gameInstance.getPlayer().size());
        gameInstance.printGamePlayers();
    }
}
