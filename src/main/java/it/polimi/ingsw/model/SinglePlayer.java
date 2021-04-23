package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Card;

import java.util.ArrayDeque;

public class SinglePlayer extends Player{

    private ArrayDeque<Card> lorenzoCardsDeck;

    private Integer croceNera;

    //TODO single player userFaithPath
    public SinglePlayer(String nickname, Integer userId, FaithPath userFaithPath){
        this.nickname = nickname;
        this.userId = userId;
        this.userFaithPath = userFaithPath;
        this.hasCalamaio = true;
        lorenzoCardsDeck = new ArrayDeque<>();
        croceNera = 0;
        //TODO initialize lorenzoCardsDeck from json file
    }

    @Override
    public void incrementFaithPathPosition() {
        this.userFaithPath.incrementUserPosition();
    }

    public void incrementLorenzoPosition(){
        this.croceNera++;
    }

    @Override
    public Integer getFaithPathPosition() {
        return this.userFaithPath.getUserPosition();
    }

    public Integer getLorenzoPosition(){
        return this.croceNera;
    }
    
    public boolean lorenzoWins(){
        if(croceNera.equals(this.userFaithPath.getEnd())) return true;
        return false;
    }

    @Override
    public void updateFaithPath(Integer newPlayingUserPos) {
        this.userFaithPath.update(newPlayingUserPos);
    }
}
