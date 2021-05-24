package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.fromClient.ClientMessageType;

public enum ServerMessageType {
    OK,
    ERROR,
    PING,
    LOGIN,
    LOGIN_ERROR,
    PARTICIPANTS,
    CHOOSELEADERCARDS,
    UPDATELEADERCARDS,
    END;

    public static ServerMessageType getMessageType(String value) {
        return Enum.valueOf(ServerMessageType.class, value.toUpperCase());
    }
}
