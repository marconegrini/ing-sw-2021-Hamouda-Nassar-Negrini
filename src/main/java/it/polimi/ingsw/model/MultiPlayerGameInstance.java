package it.polimi.ingsw.model;

import java.util.ArrayList;

public class MultiPlayerGameInstance extends GameInstance{

    private ArrayList<MultiPlayer> players;

    private ArrayList<FaithPath> usersFaithPaths;

    public MultiPlayerGameInstance(Integer gameId){
        this.gameId = gameId;
        players = new ArrayList<>();
        usersFaithPaths = new ArrayList<>();
    }

    public void addPlayer(String nickname, Integer userId, boolean hasCalamaio) throws Exception {
        if(players.size() <= 4) {
            FaithPath newUserFaithPath = new FaithPath(userId);
            players.add(new MultiPlayer(nickname, userId, hasCalamaio, newUserFaithPath));
            usersFaithPaths.add(newUserFaithPath);

        } else throw new Exception();
    }

    public void incrementFaithPathPos(Player player){
        Integer newUserPosition = 0;
        //incrementing user position
        for(FaithPath FP : usersFaithPaths){
            if(FP.getPathId().equals(player.getUserId())){
                FP.incrementUserPosition();
                newUserPosition = FP.getUserPosition();
            }
        }

        //notifying other users
        for(FaithPath FP : usersFaithPaths){
            FP.update(player, newUserPosition);
        }

    }

    @Override
    public Integer getGameId() {
        return this.gameId;
    }
}
