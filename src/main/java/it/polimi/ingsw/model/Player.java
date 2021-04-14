package it.polimi.ingsw.model;

public abstract class Player {

    protected Integer userId;

    protected String nickname;

    protected PersonalBoard personalBoard;

    protected FaithPath userFaithPath;

    public Integer getUserId(){
        return this.userId;
    }

}
