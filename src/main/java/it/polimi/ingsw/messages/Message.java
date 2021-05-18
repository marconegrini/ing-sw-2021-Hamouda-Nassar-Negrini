package it.polimi.ingsw.messages;

import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.model.Player;

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
        return this.messageType;
    }

    public String getNickname() {
        return this.nickname;
    }

    public abstract boolean serverProcess(Player player, TurnManager turnManager);

    public abstract boolean clientProcess();

}
