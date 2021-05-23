package it.polimi.ingsw.messages;

import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.model.Player;

import java.io.DataOutputStream;

public abstract class Message {

    private final String nickname;
    private final MessageType messageType;

    public Message(String nickname, MessageType messageType){
        this.nickname = nickname;
        this.messageType = messageType;
    }

    public String toString() {
        return ("Message{ nickname = " + nickname +
                ", messageType = " + messageType +
                '}');
    }

    public MessageType getMessageType() {
        return this.messageType;
    }

    public String getNickname() {
        return this.nickname;
    }

    /**
     * A method invoked on server to do what should be done ON SERVER, like (SEND MESSAGE, UPDATE STRUCTURE)
     * @param player the object player of the player that has sent the message
     * @param turnManager the turn manager of the game  on the server
     * @return true if there are no error
     */
    public abstract boolean serverProcess(Player player, TurnManager turnManager);

    /**
     * A method invoked on server to do what should be done ON CLIENT, like (SEND MESSAGE, UPDATE STRUCTURE)
     * @return true if the are no errors
     */
    public abstract boolean clientProcess(DataOutputStream getToServer);

}
