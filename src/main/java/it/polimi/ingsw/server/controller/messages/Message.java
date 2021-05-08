package it.polimi.ingsw.server.controller.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.Player;

import java.io.IOException;

public abstract class Message {

    private final String nickname;
    private final MessageType messageType;

    public Message(String nickname, MessageType messageType){
        this.nickname = nickname;
        this.messageType = messageType;
    }

        public String toString() {
            return "Message{" +
                    "nickname=" + nickname +
                    ", messageType=" + messageType +
                    '}';
        }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getNickname() {
        return nickname;
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

    public abstract String fromJsonToMessage();

    //    {
//        Gson gson = new Gson();
//        Message received = gson.fromJson(this.message, Message.class);
//        return received.toString();
//    }

}
