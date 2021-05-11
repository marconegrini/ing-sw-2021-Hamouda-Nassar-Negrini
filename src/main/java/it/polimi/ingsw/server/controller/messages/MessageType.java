package it.polimi.ingsw.server.controller.messages;

import it.polimi.ingsw.server.model.enumerations.ASCII_Shapes;

import java.util.Locale;

public enum MessageType {

    //actions
    PICKRESOURCES,
    BUYDEVELOPMENTCARD,
    ACTIVATEPRODUCTION,
    INSERTRESOURCESINWAREHOUSE,
    MOVEWAREHOUSERESOURCES,
    INSERTMARBLEMESSAGE,

    //outcomes
    ERROR,
    OK,

    //testing
    PING,

    //Scopiazzati
    MESSAGE_TYPEESSAGE,
    EXCEPTION,
    LEADERCARD;


    public static MessageType getMessageType(String value) {
        return Enum.valueOf(MessageType.class, value.toUpperCase());
    }
}
