package it.polimi.ingsw.model.multiplayer;

import it.polimi.ingsw.model.FaithPath;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;

import java.net.Socket;

public class MultiPlayer extends Player {

    public MultiPlayer(String nickname, Integer userId, Socket socket){
        this.userId = userId;
        this.nickname = nickname;
        this.socket = socket;
        this.userFaithPath = new FaithPath();
        this.hasCalamaio = false;
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

    @Override
    public void buyResources() {

    }

    @Override
    public void buyDevelopmentCard() {

    }

    @Override
    public void activateProduction() {

    }

    public void setCalamaio(){
        this.hasCalamaio = true;
    }
}
