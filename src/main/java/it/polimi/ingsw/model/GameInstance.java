package it.polimi.ingsw.model;

import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.exceptions.MaxPlayersException;

public abstract class GameInstance {

    protected Integer gameId;

    protected CardsDeck cardsDeck;

    //protected MarketBoard marketBoard;

    public abstract Integer getGameId();

    public abstract void addPlayer(String nickname, Integer userId, boolean hasCalamaio) throws MaxPlayersException;

    public abstract void incrementFaithPathPos(Player player);

}
