package it.polimi.ingsw.server.model.multiplayer;

import it.polimi.ingsw.server.model.FaithPath;
import it.polimi.ingsw.server.model.PersonalBoard;
import it.polimi.ingsw.server.model.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class MultiPlayer extends Player {

    public MultiPlayer(String nickname, Integer userId, DataOutputStream toClient, DataInputStream fromClient){
        this.userId = userId;
        this.nickname = nickname;
        this.toClient = toClient;
        this.fromClient = fromClient;
        this.userFaithPath = new FaithPath();
        this.hasCalamaio = false;
        this.personalBoard = new PersonalBoard();
    }

    //Added only for testing
    public MultiPlayer() {
        this.userId = null;
        this.nickname = null;
        this.toClient = null;
        this.fromClient = null;
        this.userFaithPath = null;
        this.hasCalamaio = false;
        this.personalBoard = null;
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
        System.out.println("Calamaio: " + this.hasCalamaio);
        System.out.println("leader cards: " + this.leaderCards.toString());
        System.out.println("faithPath: " + this.userFaithPath);
        System.out.println("personalBoard: " + this.personalBoard);
        System.out.println("dis: " + this.fromClient);
        System.out.println("dos: " + this.toClient);
    }


}
