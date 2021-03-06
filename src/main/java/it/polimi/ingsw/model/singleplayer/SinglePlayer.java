package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.FaithPath;
import it.polimi.ingsw.model.PersonalBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LorenzoCard;
import it.polimi.ingsw.enumerations.LorenzoCardType;

import java.util.Collections;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that extends Player main class and contains methods for the single player game
 */
public class SinglePlayer extends Player {

    private static final Logger logger = Logger.getLogger(SinglePlayer.class.getName());
    private Stack<LorenzoCard> lorenzoCardsDeck;

    //private Stack<LorenzoCard> temporaryDeck;

    private Integer croceNera;

    private Stack<LorenzoCard> poppedLorenzosCard;

    //TODO single player userFaithPath

    public SinglePlayer(String nickname){
        this.nickname = nickname;
        this.userFaithPath = new FaithPath();
        this.hasCalamaio = true;
        this.personalBoard = new PersonalBoard();
        lorenzoCardsDeck = new Stack();
        lorenzoCardsDeck.push(new LorenzoCard(LorenzoCardType.DISCARD2GREENDVCARDS));
        lorenzoCardsDeck.push(new LorenzoCard(LorenzoCardType.DISCARD2YELLOWDVCARDS));
        lorenzoCardsDeck.push(new LorenzoCard(LorenzoCardType.DISCARD2BLUEDVCARDS));
        lorenzoCardsDeck.push(new LorenzoCard(LorenzoCardType.DISCARD2VIOLETDVCARDS));
        lorenzoCardsDeck.push(new LorenzoCard(LorenzoCardType.TWOFAITHPOINTSCARD));
        lorenzoCardsDeck.push(new LorenzoCard(LorenzoCardType.FAITHANDSHUFFLECARD));
        lorenzoCardsDeck.push(new LorenzoCard(LorenzoCardType.TWOFAITHPOINTSCARD));
        Collections.shuffle(lorenzoCardsDeck);
        poppedLorenzosCard = new Stack<>();
        croceNera = 0;
    }

    @Override
    public void incrementFaithPathPosition() {
        this.userFaithPath.incrementUserPosition();
    }

    @Override
    public Integer getFaithPathPosition() {
        return this.userFaithPath.getUserPosition();
    }

    @Override
    public void updateFaithPath(Integer newLorenzoPos) {
        this.userFaithPath.update(newLorenzoPos);
    }

    /**
     * Increments black cross position in faith path
     */
    public void incrementLorenzoPosition(){
        if(croceNera < userFaithPath.getEnd())
            croceNera++;
    }

    /**
     * @return the entire action cards deck
     */
    public Stack<LorenzoCard>getActionCards(){
        return lorenzoCardsDeck;
    }

    /**
     * @return action card picked from the main deck and left apart
     */
    public Stack<LorenzoCard> getPoppedLorenzosCard() {
        return poppedLorenzosCard;
    }

    /**
     * @return black cross' position in faithpath
     */
    public Integer getLorenzoPosition(){
        return this.croceNera;
    }

    /**
     *
     * @return true if the black cross reached the end of faith path
     */
    public boolean lorenzoWins(){
        if(croceNera.equals(this.userFaithPath.getEnd())) return true;
        return false;
    }

    public void printPlayer(){
        logger.log(Level.INFO,"calamaio: " + this.hasCalamaio);
        logger.log(Level.INFO,"faithPath: " + this.userFaithPath.toString());
        logger.log(Level.INFO,"personalBoard: " + this.personalBoard.toString());
        logger.log(Level.INFO,"Lorenzo Cards deck: " + this.lorenzoCardsDeck.toString());
        logger.log(Level.INFO,"Croce nera Pos: " + croceNera.toString());
    }

}
