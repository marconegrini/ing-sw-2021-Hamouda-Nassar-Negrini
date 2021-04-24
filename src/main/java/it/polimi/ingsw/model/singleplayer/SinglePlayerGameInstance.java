package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.FaithPath;
import it.polimi.ingsw.model.GameInstance;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.exceptions.MaxPlayersException;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;

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
    public void addPlayer(String nickname, Integer userId, boolean hasCalamaio) throws MaxPlayersException {
        if(player==null) {
            FaithPath newUserFaithPath = new FaithPath();
            player = new SinglePlayer(nickname, userId, newUserFaithPath);
        }
    }

    @Override
    public Player getPlayer(Integer playerId) {
        if(player.getUserId().equals(playerId)) return player;
        else throw new IllegalArgumentException();
    }
}
