package it.polimi.ingsw.messages.fromClient;


/**
 * messages that the client sends
 */
public enum ClientMessageType {
    PING,
    LOGIN,
    BUYDEVELOPMENTCARD,
    CALAMAIORESPONSE,

    ACTIVATEPRODUCTION,
    ERRORPRODUCTION,

    ACTIVATELEADERCARD,
    ERRORLEADERCARD,

    DISCARDLEADERCARD,

    PICKRESOURCES;



    public static ClientMessageType getMessageType(String value) {
        return Enum.valueOf(ClientMessageType.class, value.toUpperCase());
    }

}
