package it.polimi.ingsw.server.model.singleplayer;

import it.polimi.ingsw.server.model.GameInstance;
import it.polimi.ingsw.server.model.MarketBoard;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.devCardsDecks.CardsDeck;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class SinglePlayerGameInstance extends GameInstance {

    private SinglePlayer player;

    public SinglePlayerGameInstance(Integer gameId){
        this.gameId = gameId;
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
    public Integer getGameId() {
        return this.gameId;
    }

    @Override
    public void addPlayer(String nickname, Integer userId, DataOutputStream dos, DataInputStream dis){
            this.player = new SinglePlayer(nickname, userId, dos, dis);
    }

    public Player getPlayer() {
       return this.player;
    }

    public void printGamePlayer(){
        System.out.println("\nPlayer: " + player.getNickname() + "\nUserId: " + player.getUserId());
        player.printPlayer();
    }
}
