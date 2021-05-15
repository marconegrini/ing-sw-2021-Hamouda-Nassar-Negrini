package it.polimi.ingsw.server.controller.messages;

public enum MessageType {

    //request messages
    PICKRESOURCES,
    BUYDEVELOPMENTCARD,
    ACTIVATEPRODUCTION,
    INSERTRESOURCESINWAREHOUSE,
    MOVEWAREHOUSERESOURCES,
    CHOOSELEADERCARD,
    ACTIVATELEADERCARD,
    DISCARDLEADERCARD,

    //update messages
    UPDATEMARKETBOARD,
    UPDATEDEVCARDSDECK,
    UPDATELEADERCARD,

    //outcomes
    ERROR,
    OK,

    //testing
    PING,

    //Scopiazzati
    MESSAGE_TYPEMESSAGE,
    EXCEPTION,
    LEADERCARD;


    public static MessageType getMessageType(String value) {
        return Enum.valueOf(MessageType.class, value.toUpperCase());
    }
}
