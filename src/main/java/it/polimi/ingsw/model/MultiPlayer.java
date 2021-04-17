package it.polimi.ingsw.model;

public class MultiPlayer extends Player{

    public MultiPlayer(String nickname, Integer userId, boolean hasCalamaio, FaithPath userFaithPath){
        this.hasCalamaio = hasCalamaio;
        this.userId = userId;
        this.nickname = nickname;
        this.userFaithPath = userFaithPath;
    }

    @Override
    public void incrementFaithPathPosition(){
        this.userFaithPath.incrementUserPosition();
    }

    @Override
    public Integer getFaithPathPosition(){
        return this.userFaithPath.getUserPosition();
    }

    //checking new playing user position to eventually flip papalFavorCard and to activate VaticanSection
    @Override
    public void updateFaithPath(Integer newPlayingUserPos){
        this.userFaithPath.update(newPlayingUserPos);
    }
}
