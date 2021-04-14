package it.polimi.ingsw.model;

import java.util.ArrayList;

//classe singleton
public class Game {

    private static Game instance;

    private static ArrayList<GameInstance> gameInstances;

    private static Integer gameId;

    private Game() {
        gameInstances = new ArrayList<>();
        gameId = 0;
    }

    public static Game getInstance(){
        if(instance == null){
            synchronized (instance){
                if(instance == null)
                    instance = new Game();
            }
        }
        return instance;
    }

    public static GameInstance newGame(boolean multiplayer){
        synchronized (gameInstances){
            GameInstance newGameInstance;
            if(multiplayer) {
                newGameInstance = new MultiPlayerGameInstance(gameId);
            } else {
                newGameInstance = new SinglePlayerGameInstance(gameId);
            }
            gameInstances.add(newGameInstance);
            gameId++;
            return newGameInstance;
        }
    }

}
