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
        gameManagers = new ArrayList<>();
        gameId = 0;
    }

    public static Game getInstance(){
        if(instance == null){
                if(instance == null)
                    instance = new Game();
        }
        return instance;
    }

    public static Integer newGame(boolean multiplayer){
        synchronized (gameInstances){
            synchronized (gameManagers) {
                gameId++;
                if (multiplayer) {
                    MultiPlayerGameInstance newGameInstance = new MultiPlayerGameInstance(gameId);
                    MultiPlayerManager newMultiPlayerManager = new MultiPlayerManager(newGameInstance);
                    gameInstances.add(newGameInstance);
                    gameManagers.add(newMultiPlayerManager);
                } else {
                    SinglePlayerGameInstance newGameInstance = new SinglePlayerGameInstance(gameId);
                    SinglePlayerManager newSinglePlayerManager = new SinglePlayerManager(newGameInstance);
                    gameInstances.add(newGameInstance);
                    gameManagers.add(newSinglePlayerManager);
                }
                return gameId;
            }
        }
    }

    public static GameInstance getGameInstance(Integer gameId){
        for(GameInstance gi : gameInstances){
            if(gi.gameId.equals(gameId))
                return gi;
        }
        return null;
    }

    public static GameManager getGameManager(Integer gameId){
        for(GameManager gm : gameManagers){
            if(gm.getGameId().equals(gameId))
                return gm;
        }
        return null;
    }

}
