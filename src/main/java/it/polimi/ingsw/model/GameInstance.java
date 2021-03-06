package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.exceptions.MaxPlayersException;

import java.util.ArrayList;

/**
 * Abstract class inherited by MultiplayerGameInstance
 */
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

    public ArrayList<DevelopmentCard> peekCardsDeck(){
        return cardsDeck.peekDecks();
    }
}
