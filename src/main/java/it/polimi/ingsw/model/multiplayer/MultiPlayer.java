package it.polimi.ingsw.model.multiplayer;

import it.polimi.ingsw.model.FaithPath;
import it.polimi.ingsw.model.PersonalBoard;
import it.polimi.ingsw.model.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiPlayer extends Player {

    private static final Logger logger = Logger.getLogger(MultiPlayer.class.getName());

    public MultiPlayer(String nickname){
        this.nickname = nickname;
        this.userFaithPath = new FaithPath();
        this.hasCalamaio = false;
        this.personalBoard = new PersonalBoard();
    }


    public MultiPlayer(){
        this.nickname = "!not set nickName ON player Model!";
        this.userFaithPath = new FaithPath();
        this.hasCalamaio = false;
        this.personalBoard = new PersonalBoard();
    }

    /**
     * increments user's faith path position
     */
    @Override
    public void incrementFaithPathPosition(){
        this.userFaithPath.incrementUserPosition();
    }

    @Override
    public Integer getFaithPathPosition(){
        return this.userFaithPath.getUserPosition();
    }

    /**
     * The method is invoked when a player increments his position in faith path. It checks wether or not a
     * rapporto in vaticano has been activated in the user's board. In this case, verifies if the personal pawn
     * is before or after the vatican section, to correctly flip or not the papal favor card
     * @param newPlayingUserPos someone else's position in faith path
     */
    @Override
    public void updateFaithPath(Integer newPlayingUserPos){
        this.userFaithPath.update(newPlayingUserPos);
    }

    public void setNickName(String nickName){
        this.nickname=nickName;
    }

    /**
     * Sets the calamaio to true for the user
     */
    public void setCalamaio(){
        this.hasCalamaio = true;
    }

    public void printPlayer(){
        logger.log(Level.INFO,"Calamaio: " + this.hasCalamaio);
        //System.out.println("leader cards: " + this.leaderCards.toString());
        logger.log(Level.INFO,"faithPath: " + this.userFaithPath.toString());
        logger.log(Level.INFO,"personalBoard: " + this.personalBoard.toString());
    }
}
