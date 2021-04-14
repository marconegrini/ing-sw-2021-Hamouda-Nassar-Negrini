package it.polimi.ingsw.model;

public class MultiPlayer extends Player{

    private boolean hasCalamaio;

    public MultiPlayer(String nickname, Integer userId, boolean hasCalamaio, FaithPath userFaithPath){
        this.hasCalamaio = hasCalamaio;
        this.userId = userId;
        this.nickname = nickname;
        this.userFaithPath = userFaithPath;
    }


}
