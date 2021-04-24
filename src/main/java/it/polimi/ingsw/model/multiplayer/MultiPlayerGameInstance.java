package it.polimi.ingsw.model.multiplayer;

import it.polimi.ingsw.model.FaithPath;
import it.polimi.ingsw.model.GameInstance;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.MaxPlayersException;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;

import java.util.ArrayList;

public class MultiPlayerGameInstance extends GameInstance {

    private ArrayList<MultiPlayer> players;

    public MultiPlayerGameInstance(Integer gameId){
        this.gameId = gameId;
        players = new ArrayList<>();
    }

    @Override
    public void addPlayer(String nickname, Integer userId, boolean hasCalamaio) throws MaxPlayersException {
        if(players.size() <= 4) {
            FaithPath newUserFaithPath = new FaithPath();
            players.add(new MultiPlayer(nickname, userId, hasCalamaio, newUserFaithPath));
            //usersFaithPaths.add(newUserFaithPath);

        } else throw new MaxPlayersException();
    }

    @Override
    public void incrementFaithPathPos(Player player){
        Integer newPlayingUserPos = 0;
        //incrementing user position and storing in newPlayingUserPos the new incremented value
        for(Player p : players)
            if(player.getUserId().equals(p.getUserId())) {
                player.incrementFaithPathPosition();
                newPlayingUserPos = player.getFaithPathPosition();
            }

        //for each user, passing the new position of the playing user to check and eventually activate
        //Rapporto in Vaticano section and to flip or not PapalFavorCard
        for(Player p : players){
            p.updateFaithPath(newPlayingUserPos);
        }
    }

    @Override
    public Integer getGameId() {
        return this.gameId;
    }
}
