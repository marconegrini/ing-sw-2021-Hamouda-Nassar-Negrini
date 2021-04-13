package it.polimi.ingsw.model;

import it.polimi.ingsw.model.devCardsDecks.CardsDeck;

public abstract class GameInstance {

    protected Integer gameId;

    protected CardsDeck cardsDeck;

    //protected MarketBoard marketBoard;

    //protected FaithPath faithPath;

    public abstract Integer getGameId();

}
