package it.polimi.ingsw.server.controller.messages;

import it.polimi.ingsw.server.model.enumerations.ASCII_Shapes;

public enum MessageType {

    PICKRESOURCES,
    ERROR,
    PING,

    //Scopiazzati
    MESSAGE_TYPEESSAGE,
    EXCEPTION,
    LEADERCARD;


    public static MessageType getMessageType(String value) {
        if (value.toUpperCase().equals("PICKRESOURCES"))
            return MessageType.PICKRESOURCES;
        else return MessageType.ERROR;


    }
}
