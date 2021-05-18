package it.polimi.ingsw.messages.updateFromServer;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.model.Player;

import java.io.IOException;

public class OkMessage extends Message {
    String outcome;

    public OkMessage(String nickname, String outcome){
        super(nickname, MessageType.OK);
        this.outcome = outcome;
        System.out.println(this.toString());
    }

    @Override
    public String toString(){
        return "OkMessage{" +
                "nickname = " + getNickname() +
                ", outcome = " + this.outcome +
                '}';
    }

    @Override
    public boolean serverProcess(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        String messageToSend = gson.toJson(this);
        try {
            player.getToClient().writeUTF(messageToSend);
        } catch (IOException e){
            System.err.println("Exception occurred while sending json");
        }
        return true;
    }

    @Override
    public boolean clientProcess(){
        return false;
    }
}
