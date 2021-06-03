package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.GameInstance;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class SinglePlayerGameInstance extends GameInstance {

    private SinglePlayer player;

    public SinglePlayerGameInstance(){
        this.cardsDeck = new CardsDeck();
        this.cardsDeck.initializeCardsDeck();
        this.marketBoard = new MarketBoard();
    }

    @Override
    public void addPlayer(Player player){
            this.player = (SinglePlayer) player;
    }

    public Player getPlayer() {
       return this.player;
    }

    public void printGamePlayer(){
        //System.out.println("\nPlayer: " + player.getNickname() + "\nUserId: " + player.getUserId());
        player.printPlayer();
    }
}
