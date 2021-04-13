package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Game {

    private ArrayList<GameInstance> gameInstances;

    private Integer gameId;

    public Game(boolean multiplayer){

        if(gameInstances == null){
            gameInstances = new ArrayList<>();
            gameId = 0;
        }

        if(multiplayer) {
            GameInstance newGame = new MultiPlayerGameInstance(gameId);
            gameInstances.add(newGame);
        } else {
            GameInstance newGame = new SinglePlayerGameInstance(gameId);
            gameInstances.add(newGame);
        }

        gameId++;
    }
}
