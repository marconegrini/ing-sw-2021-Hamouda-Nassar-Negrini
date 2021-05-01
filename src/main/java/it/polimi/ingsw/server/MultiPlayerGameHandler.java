package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.MultiPlayerManager;
import it.polimi.ingsw.model.multiplayer.MultiPlayerGameInstance;

public class MultiPlayerGameHandler extends Thread{

    private final MultiPlayerGameInstance gameInstance;
    private final MultiPlayerManager manager;

    public MultiPlayerGameHandler(MultiPlayerGameInstance gameInstance, MultiPlayerManager manager){
        this.gameInstance = gameInstance;
        this.manager = manager;
    }
}
