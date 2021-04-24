package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.FaithPath;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.LorenzoCard;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.enumerations.LorenzoCardsTypes;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

public class SinglePlayer extends Player {

    private Stack<LorenzoCard> lorenzoCardsDeck;

    private Stack<LorenzoCard> temporaryDeck;

    private Integer croceNera;

    //TODO single player userFaithPath
    public SinglePlayer(String nickname, Integer userId, FaithPath userFaithPath){
        this.nickname = nickname;
        this.userId = userId;
        this.userFaithPath = userFaithPath;
        this.hasCalamaio = true;
        lorenzoCardsDeck = new Stack();
        lorenzoCardsDeck.push(new LorenzoCard(LorenzoCardsTypes.DISCARD2GREENDVCARDS));
        lorenzoCardsDeck.push(new LorenzoCard(LorenzoCardsTypes.DISCARD2YELLOWDVCARDS));
        lorenzoCardsDeck.push(new LorenzoCard(LorenzoCardsTypes.DISCARD2BLUEDVCARDS));
        lorenzoCardsDeck.push(new LorenzoCard(LorenzoCardsTypes.DISCARD2VIOLETDVCARDS));
        lorenzoCardsDeck.push(new LorenzoCard(LorenzoCardsTypes.TWOFAITHPOINTSCARD));
        lorenzoCardsDeck.push(new LorenzoCard(LorenzoCardsTypes.FAITHANDSHUFFLECARD));
        lorenzoCardsDeck.push(new LorenzoCard(LorenzoCardsTypes.TWOFAITHPOINTSCARD));
        Collections.shuffle(lorenzoCardsDeck);
        temporaryDeck = new Stack<>();
        croceNera = 0;
        //TODO initialize lorenzoCardsDeck from json file
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
                System.out.println("Picked Up a "+ LorenzoCardsTypes.DISCARD2BLUEDVCARDS);
                break;

            case DISCARD2GREENDVCARDS:
                System.out.println("Picked Up a "+ LorenzoCardsTypes.DISCARD2GREENDVCARDS);
                break;

            case DISCARD2VIOLETDVCARDS:
                System.out.println("Picked Up a "+ LorenzoCardsTypes.DISCARD2VIOLETDVCARDS);
                break;

            case DISCARD2YELLOWDVCARDS:
                System.out.println("Picked Up a "+ LorenzoCardsTypes.DISCARD2YELLOWDVCARDS);
                break;

            case TWOFAITHPOINTSCARD:
                this.incrementLorenzoPosition();
                this.updateFaithPath(this.getLorenzoPosition());
                this.incrementLorenzoPosition();
                this.updateFaithPath(this.getLorenzoPosition());
                System.out.println("Picked Up a "+ LorenzoCardsTypes.TWOFAITHPOINTSCARD);
                break;

            case FAITHANDSHUFFLECARD:
                this.incrementLorenzoPosition();
                this.updateFaithPath(this.getLorenzoPosition());
                this.incrementLorenzoPosition();
                this.updateFaithPath(this.getLorenzoPosition());
                Collections.shuffle(this.lorenzoCardsDeck);
                System.out.println("Picked Up a "+ LorenzoCardsTypes.FAITHANDSHUFFLECARD);
                break;

        }
    }

    public Integer getLorenzoPosition(){
        return this.croceNera;
    }

    public boolean lorenzoWins(){
        if(croceNera.equals(this.userFaithPath.getEnd())) return true;
        return false;
    }

}
