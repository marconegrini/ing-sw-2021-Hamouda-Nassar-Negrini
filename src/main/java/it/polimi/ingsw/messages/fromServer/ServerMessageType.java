package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.messages.fromClient.ClientMessageType;

/**
 * messages sent by the server
 */
public enum ServerMessageType {
    OK,
    PRINT,
    ERROR,
    PING,
    LOGIN,
    LOGIN_ERROR,
    CALAMAIO,
    PARTICIPANTS,

    CHOOSELEADERCARDS,
    UPDATELEADERCARDS,
    UPDATELEADERCARDSTATUS,
    SELECTACTION,

    UPDATEMARKETBOARD,
    UPDATEDEVCARDSDECK,
    UPDATEDEVCARDSSLOT,
    UPDATEFAITHPATH,
    UPDATEWAREHOUSECOFFER,

    RESOURCESFROMMARKET,

    END;

    public static ServerMessageType getMessageType(String value) {
        return Enum.valueOf(ServerMessageType.class, value.toUpperCase());
    }
}
