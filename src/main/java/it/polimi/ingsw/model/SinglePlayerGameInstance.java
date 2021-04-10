package it.polimi.ingsw.model;

import it.polimi.ingsw.model.devCardsDecks.CardsDeck;

public class SinglePlayerGameInstance extends GameInstance{

    private SinglePlayer player;

    public SinglePlayerGameInstance(){
        player = new SinglePlayer();
    }
}
