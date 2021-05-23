package it.polimi.ingsw.messages.updateFromServer;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.model.Player;

import java.io.DataOutputStream;
import java.io.IOException;

public class ErrorMessage extends Message {
    private final String error;

    public ErrorMessage(String nickname, String error) {
        super(nickname, MessageType.ERROR);
        this.error = error;
        System.out.println(this.toString());
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "nickname=" + getNickname() +
                ", error=" + error +
                '}';
    }

    @Override
    public boolean serverProcess(Player player, TurnManager turnManager){
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
    public boolean clientProcess(DataOutputStream getToServer) {
        return false;
    }



}
