package it.polimi.ingsw.model.multiplayer;

import it.polimi.ingsw.model.FaithPath;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class MultiPlayer extends Player {

    public MultiPlayer(String nickname, Integer userId, DataOutputStream toServer, DataInputStream fromServer){
        this.userId = userId;
        this.nickname = nickname;
        this.toServer = toServer;
        this.fromServer = fromServer;
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

    public void printPlayer(){
        System.out.println(this.hasCalamaio);
        System.out.println(this.leaderCards);
        System.out.println(this.nickname);
        System.out.println(this.userFaithPath);
        System.out.println(this.personalBoard);
        System.out.println(this.fromServer);
        System.out.println(this.toServer);
    }
}
