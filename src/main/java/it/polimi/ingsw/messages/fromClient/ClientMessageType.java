package it.polimi.ingsw.messages.fromClient;

public enum ClientMessageType {
    PING,
    LOGIN,

    BUYDEVELOPMENTCARD,
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
