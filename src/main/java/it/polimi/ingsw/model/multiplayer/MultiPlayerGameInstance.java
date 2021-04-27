package it.polimi.ingsw.model.multiplayer;

import it.polimi.ingsw.model.FaithPath;
import it.polimi.ingsw.model.GameInstance;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.MaxPlayersException;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;

import java.util.ArrayList;
import java.util.List;

public class MultiPlayerGameInstance extends GameInstance {

    private List<MultiPlayer> players;

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
    public Integer getGameId() {
        return this.gameId;
    }

    public List<MultiPlayer> getPlayers(){
        return this.players;
    }

}
