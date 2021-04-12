package it.polimi.ingsw.model;

import java.util.ArrayList;

public class MultiPlayerGameInstance extends GameInstance{

    private ArrayList<MultiPlayer> players;

    public MultiPlayerGameInstance(){
        players = new ArrayList<>();
    }
}
