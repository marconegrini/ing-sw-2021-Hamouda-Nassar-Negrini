package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.model.Player;

import java.io.DataOutputStream;
import java.io.IOException;

public class PingMessage extends Message {

    public PingMessage(String nickname){
        super(nickname, MessageType.PING);
    }

    @Override
    public String toString(){
        return ("PingMessage{ nickname = " + getNickname() +
                '}');
    }

    @Override
    public boolean serverProcess(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        String jsonPing = gson.toJson(this);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                    try {
                        synchronized (this) {
                            this.wait(5000);
                            player.getToClient().writeUTF(jsonPing);
                            System.out.println("sent ping to client " + player.getNickname());
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        };
        Thread t = new Thread(r);
        t.start();

        return true;
    }

    @Override
    public boolean clientProcess(DataOutputStream dos){
        Gson gson = new Gson();
        String jsonPing = gson.toJson(this);
        try {
            dos.writeUTF(jsonPing);
            System.out.println("sent ping to server");
        } catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }
}
