package it.polimi.ingsw.server.controller;

public class ClientTimeOut {
    private long initializationTime;
    private String nickname;
    boolean valid;

    public ClientTimeOut(String nickname){
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void resetTime(){
        initializationTime = System.currentTimeMillis();
    }

    public boolean isValid(){
        long delta = System.currentTimeMillis() - initializationTime;
        if(delta > 5*1000) return false;
        return true;
    }

}
