package it.polimi.ingsw.model;

import it.polimi.ingsw.model.devCardsDecks.CardsDeck;

public class SinglePlayerGameInstance extends GameInstance{

    private SinglePlayer player;

    public SinglePlayerGameInstance(Integer gameId){
        this.gameId = gameId;
        player = new SinglePlayer();
    }

    @Override
    public Integer getGameId() {
        return this.gameId;
    }
}
