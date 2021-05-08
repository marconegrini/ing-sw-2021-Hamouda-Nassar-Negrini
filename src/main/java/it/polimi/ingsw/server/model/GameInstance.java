package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.server.model.exceptions.MaxPlayersException;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public abstract class GameInstance {

    protected Integer gameId;

    protected CardsDeck cardsDeck;

    protected MarketBoard marketBoard;

    public abstract Integer getGameId();

    public abstract void addPlayer(String nickname, Integer userId, DataOutputStream toServer, DataInputStream fromServer) throws MaxPlayersException;

    public MarketBoard getMarketBoard(){
        return this.marketBoard;
    }

    public CardsDeck getCardsDeck(){
        return this.cardsDeck;
    }



}
