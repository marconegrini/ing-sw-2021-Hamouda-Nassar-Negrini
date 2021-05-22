package it.polimi.ingsw.model;

import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.exceptions.MaxPlayersException;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public abstract class GameInstance {

    protected CardsDeck cardsDeck;

    protected MarketBoard marketBoard;

    public abstract void addPlayer(Player player) throws MaxPlayersException;

    public MarketBoard getMarketBoard(){
        return this.marketBoard;
    }

    public CardsDeck getCardsDeck(){
        return this.cardsDeck;
    }
}
