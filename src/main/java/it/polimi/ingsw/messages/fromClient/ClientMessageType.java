package it.polimi.ingsw.messages.fromClient;

public enum ClientMessageType {
    LOGIN,
    BUYDEVCARD;


    public static ClientMessageType getMessageType(String value) {
        return Enum.valueOf(ClientMessageType.class, value.toUpperCase());
    }

}
