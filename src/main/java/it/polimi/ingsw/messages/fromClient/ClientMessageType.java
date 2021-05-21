package it.polimi.ingsw.messages.fromClient;

public enum ClientMessageType {
    LOGIN,
    BUYDEVCARD,

    ACTIVATE_PRODUCTION,
    ERROR_PRODUCTION,

    ACTIVATE_LEADERCARD,
    ERROR_LEADERCARD,

    DISCARD_LEADERCARD,

    PICKRESOURCES;




    public static ClientMessageType getMessageType(String value) {
        return Enum.valueOf(ClientMessageType.class, value.toUpperCase());
    }

}
