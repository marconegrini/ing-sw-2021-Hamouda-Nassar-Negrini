package it.polimi.ingsw.model;

import java.util.ArrayList;

public class MultiPlayerGameInstance extends GameInstance{

    private ArrayList<MultiPlayer> players;

    public MultiPlayerGameInstance(Integer gameId){
        this.gameId = gameId;
        players = new ArrayList<>();
    }

    @Override
    public Integer getGameId() {
        return this.gameId;
    }
}
