package it.polimi.ingsw.server.model.singleplayer;

import it.polimi.ingsw.server.model.FaithPath;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.cards.LorenzoCard;
import it.polimi.ingsw.server.model.enumerations.LorenzoCardType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Collections;
import java.util.Stack;

public class SinglePlayer extends Player {

    private Stack<LorenzoCard> lorenzoCardsDeck;

    //private Stack<LorenzoCard> temporaryDeck;

    private Integer croceNera;

    private Stack<LorenzoCard> poppedLorenzosCard;

    //TODO single player userFaithPath

    public SinglePlayer(String nickname, Integer userId, DataOutputStream toServer, DataInputStream fromServer){
        this.nickname = nickname;
        this.userId = userId;
        this.toClient = toServer;
        this.fromClient = fromServer;
        this.userFaithPath = new FaithPath();
        this.hasCalamaio = true;
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

    @Override
    public void buyResources() {}

    @Override
    public void buyDevelopmentCard() {}

    @Override
    public void activateProduction() {}

    public void incrementLorenzoPosition(){
        this.croceNera++;
    }

    public void pickLorenzoCard(){

        LorenzoCard lorenzoCard = lorenzoCardsDeck.pop();

        switch (lorenzoCard.getType()){

            case DISCARD2BLUEDVCARDS:
                System.out.println("Picked Up a "+ LorenzoCardType.DISCARD2BLUEDVCARDS);
                break;

            case DISCARD2GREENDVCARDS:
                System.out.println("Picked Up a "+ LorenzoCardType.DISCARD2GREENDVCARDS);
                break;

            case DISCARD2VIOLETDVCARDS:
                System.out.println("Picked Up a "+ LorenzoCardType.DISCARD2VIOLETDVCARDS);
                break;

            case DISCARD2YELLOWDVCARDS:
                System.out.println("Picked Up a "+ LorenzoCardType.DISCARD2YELLOWDVCARDS);
                break;

            case TWOFAITHPOINTSCARD:
                this.incrementLorenzoPosition();
                this.updateFaithPath(this.getLorenzoPosition());
                this.incrementLorenzoPosition();
                this.updateFaithPath(this.getLorenzoPosition());
                System.out.println("Picked Up a "+ LorenzoCardType.TWOFAITHPOINTSCARD);
                break;

            case FAITHANDSHUFFLECARD:
                this.incrementLorenzoPosition();
                this.updateFaithPath(this.getLorenzoPosition());
                this.incrementLorenzoPosition();
                this.updateFaithPath(this.getLorenzoPosition());
                lorenzoCardsDeck.addAll(poppedLorenzosCard);
                poppedLorenzosCard.clear();
                Collections.shuffle(this.lorenzoCardsDeck);
                System.out.println("Picked Up a "+ LorenzoCardType.FAITHANDSHUFFLECARD);
                break;

        }

        poppedLorenzosCard.push(lorenzoCard);
    }

    public Integer getLorenzoPosition(){
        return this.croceNera;
    }

    public boolean lorenzoWins(){
        if(croceNera.equals(this.userFaithPath.getEnd())) return true;
        return false;
    }

    public void printPlayer(){
        System.out.println("calamaio: " + this.hasCalamaio);
        System.out.println("leaderCards: " + this.leaderCards);
        System.out.println("faithPath: " + this.userFaithPath);
        System.out.println("personalBoard: " + this.personalBoard);
        System.out.println("dis: " + this.fromClient);
        System.out.println("dos: " + this.toClient);
        System.out.println("Lorenzo Cards deck: " + this.lorenzoCardsDeck);
        System.out.println("Croce nera Pos: " + croceNera);
    }

}
