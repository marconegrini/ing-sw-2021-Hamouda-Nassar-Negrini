package it.polimi.ingsw.server.controller;

public class ClientTimeOut {
    private long initializationTime;
    private Integer clientId;
    boolean valid;

    public ClientTimeOut(Integer clientId){
        this.clientId = clientId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setTime(){
        initializationTime = System.currentTimeMillis();
    }

    public boolean isValid(){
        long delta = System.currentTimeMillis() - initializationTime;
        if(delta > 5*1000) return false;
        return true;
    }

}
