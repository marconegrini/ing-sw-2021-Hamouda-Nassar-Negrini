package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.fromClient.ClientMessageType;

public enum ServerMessageType {
    OK, ERROR, PING, PARTICIPANTS;

    public static ServerMessageType getMessageType(String value) {
        return Enum.valueOf(ServerMessageType.class, value.toUpperCase());
    }
}
