package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.FaithPath;
import it.polimi.ingsw.model.GameInstance;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.exceptions.MaxPlayersException;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;

import java.net.Socket;

public class SinglePlayerGameInstance extends GameInstance {

    private SinglePlayer player;

    public SinglePlayerGameInstance(Integer gameId){
        this.gameId = gameId;
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
    public void addPlayer(String nickname, Integer userId, Socket socket) throws MaxPlayersException {
        if(player==null) {
            player = new SinglePlayer(nickname, userId, socket);
        }
    }

    public Player getPlayer() {
       return this.player;
    }
}
