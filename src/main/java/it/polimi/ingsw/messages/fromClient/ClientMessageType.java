package it.polimi.ingsw.messages.fromClient;

public enum ClientMessageType {
    PING,
    LOGIN,
    BUYDEVELOPMENTCARD,

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
