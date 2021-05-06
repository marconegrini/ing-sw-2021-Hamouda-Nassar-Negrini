package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.model.Player;

import java.io.DataOutputStream;
import java.io.IOException;

public class Message {

    private final String message;

    public Message(String message){
        this.message = message;
    }

    public String toString(){
        return this.message;
    }

    public void sendJson(Player player){
        Gson gson = new Gson();
        String jsonMessage = gson.toJson(this);
        try {
            player.getToClient().writeUTF(jsonMessage);
        } catch (IOException e){
            System.err.println("Exception occurred while sending json");
            e.printStackTrace();
        }
    }

    public String fromJsonToMessage(){
        Gson gson = new Gson();
        Message received = gson.fromJson(this.message, Message.class);
        return received.toString();
    }

}
