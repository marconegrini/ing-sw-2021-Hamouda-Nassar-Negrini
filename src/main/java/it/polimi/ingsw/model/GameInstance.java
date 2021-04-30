package it.polimi.ingsw.model;

import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.exceptions.MaxPlayersException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

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
