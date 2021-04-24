package it.polimi.ingsw.model;

import it.polimi.ingsw.model.devCardsDecks.CardsDeck;

public abstract class Player {

    protected Integer userId;

    protected String nickname;

    protected boolean hasCalamaio;

    protected PersonalBoard personalBoard;

    protected FaithPath userFaithPath;

    public Integer getUserId(){
        return this.userId;
    }

    public abstract void incrementFaithPathPosition();

    public abstract Integer getFaithPathPosition();

    public abstract void updateFaithPath(Integer newPlayingUserPos);

    public abstract void buyResources();

    public abstract void buyDevelopmentCard();

    public abstract void activateProduction();
}
