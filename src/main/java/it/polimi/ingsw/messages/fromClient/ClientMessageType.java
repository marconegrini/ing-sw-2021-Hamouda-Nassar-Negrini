package it.polimi.ingsw.messages.fromClient;


/**
 * messages that the client sends
 */
public enum ClientMessageType {
    EMPTY,

    PING,
    LOGIN,
    EXITFROMGAME,
    ASKSTARTGAME,

    BUYDEVELOPMENTCARD,
    CALAMAIORESPONSE,

    ACTIVATEPRODUCTION,
    ACTIVATEPERSONALPRODUCTION,
    PICKRESOURCES,

    SELECTLEADERCARDS,
    DISCARDLEADERCARD,
    ACTIVATELEADERCARD,
    ERRORLEADERCARD,


    INSERTRESOURCESINWAREHOUSE,
    MOVEWAREHOUSERESOURCES;

    public static ClientMessageType getMessageType(String value) {
        return Enum.valueOf(ClientMessageType.class, value.toUpperCase());
    }

}
