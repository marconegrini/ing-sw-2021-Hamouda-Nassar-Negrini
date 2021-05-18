package it.polimi.ingsw.messages;

public enum MessageType {

    //request messages
    PICKRESOURCES,
    BUYDEVELOPMENTCARD,
    ACTIVATEPRODUCTION,
    ACTIVATEPERSONALPRODUCTION,
    INSERTRESOURCESINWAREHOUSE,
    MOVEWAREHOUSERESOURCES,
    CHOOSELEADERCARD,
    ACTIVATELEADERCARD,
    DISCARDLEADERCARD,

    //update messages
    UPDATEMARKETBOARD,
    UPDATELEADERCARD,
    RESOURCESFROMMARKET,

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
