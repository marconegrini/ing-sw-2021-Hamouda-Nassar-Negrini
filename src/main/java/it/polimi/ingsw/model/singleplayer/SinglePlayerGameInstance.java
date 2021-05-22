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
        this.marketBoard = new MarketBoard();
    }

    /*
    public void incrementFaithPathPos(Player player){
        if(player.getUserId().equals(this.player.getUserId())) {
            Integer newPlayingUserPos;
            this.player.incrementFaithPathPosition();
            newPlayingUserPos = this.player.getFaithPathPosition();
            this.player.updateFaithPath(newPlayingUserPos);
        }
    }

    public void incrementLorenzoPosition(){
        this.player.incrementLorenzoPosition();
        this.player.updateFaithPath(this.player.getLorenzoPosition());
    }
*/

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
