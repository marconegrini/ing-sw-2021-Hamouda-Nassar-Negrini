package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;

import java.net.Socket;
import java.util.List;

public abstract class Player {

    protected Integer userId;

    protected String nickname;

    protected Socket socket;

    protected boolean hasCalamaio;

    protected PersonalBoard personalBoard;

    protected List<LeaderCard> leaderCards;

    protected FaithPath userFaithPath;

    public Integer getUserId(){
        return this.userId;
    }

    public void setLeaderCards(List<LeaderCard> leaderCards){
        this.leaderCards = leaderCards;
    }

    public abstract void incrementFaithPathPosition();

    public abstract Integer getFaithPathPosition();

    public abstract void updateFaithPath(Integer newPlayingUserPos);

    public abstract void buyResources();

    public abstract void buyDevelopmentCard();

    public abstract void activateProduction();
}
