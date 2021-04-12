package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Game {

    private ArrayList<GameInstance> gameInstances;

    public Game(boolean multiplayer){
        if(multiplayer) new MultiPlayerGameInstance();
        if(!multiplayer) new SinglePlayerGameInstance();
    }
}
