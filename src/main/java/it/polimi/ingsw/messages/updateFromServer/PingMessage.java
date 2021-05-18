package it.polimi.ingsw.messages.updateFromServer;

import com.google.gson.Gson;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.Player;

import java.io.DataOutputStream;
import java.io.IOException;

public class PingMessage extends Message {

    public PingMessage(String nickname){
        super(nickname, MessageType.PING);
    }

    @Override
    public String toString(){
        return "PingMessage{" +
                "nickname = " + getNickname() +
                '}';
    }

    @Override
    public boolean serverProcess(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        String jsonPing = gson.toJson(this);
        try{
            player.getToClient().writeUTF(jsonPing);
        } catch(IOException e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean clientProcess(DataOutputStream dos){
        Gson gson = new Gson();
        String jsonPing = gson.toJson(this);
        try{
            dos.writeUTF(jsonPing);
        } catch(IOException e){
            e.printStackTrace();
        }
        return true;
    }
}
