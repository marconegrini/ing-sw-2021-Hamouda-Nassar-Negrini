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
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try{
                    synchronized (this){
                        this.wait(5000);
                        initializationTime = System.currentTimeMillis();
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public boolean isValid(){
        long delta = System.currentTimeMillis() - initializationTime;
        if(delta > 10*1000) return false;
        return true;
    }

}
