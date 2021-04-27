package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.MultiPlayerManager;
import it.polimi.ingsw.controller.SinglePlayerManager;
import it.polimi.ingsw.model.multiplayer.MultiPlayerGameInstance;
import it.polimi.ingsw.model.singleplayer.SinglePlayerGameInstance;

import java.util.ArrayList;
import java.util.List;

//classe singleton
public class Game {

    private static Game instance;

    private static List<GameInstance> gameInstances;

    private static List<GameManager> gameManagers;

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

    public static void newGame(boolean multiplayer){
        synchronized (gameInstances){
            if(multiplayer) {
                MultiPlayerGameInstance newGameInstance;
                MultiPlayerManager newMultiPlayerManager;
                newGameInstance = new MultiPlayerGameInstance(gameId);
                newMultiPlayerManager = new MultiPlayerManager(newGameInstance);
                gameInstances.add(newGameInstance);
            } else {
                SinglePlayerGameInstance newGameInstance;
                SinglePlayerManager newSinglePlayerManager;
                newGameInstance = new SinglePlayerGameInstance(gameId);
                newSinglePlayerManager = new SinglePlayerManager(newGameInstance);
                gameInstances.add(newGameInstance);
            }
            gameId++;
        }
    }

}
