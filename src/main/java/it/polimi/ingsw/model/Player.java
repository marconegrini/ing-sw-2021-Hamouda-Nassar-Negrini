package it.polimi.ingsw.model;

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

}
