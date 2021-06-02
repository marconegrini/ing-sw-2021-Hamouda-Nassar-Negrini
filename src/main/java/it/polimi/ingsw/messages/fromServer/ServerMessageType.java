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
    UPDATEWAREHOUSE,
    BUYDVCARDERROR,
    PARTICIPANTS,
    INWAITINGROOM,
    STARTGAME,
    ENDGAME,
    ERRORSTARTGAME,

    CHOOSELEADERCARDS,
    UPDATELEADERCARDS,
    UPDATELEADERCARDSTATUS,
    LEADERCARDRESULT,
    SELECTACTION,
    MOVERESOURCESRESULT,

    UPDATEMARKETBOARD,
    UPDATEDEVCARDSDECK,
    UPDATEDEVCARDSSLOT,
    UPDATEFAITHPATH,
    UPDATEWAREHOUSECOFFER,
    UPDATEVATICANSECTIONS,

    RESOURCESTOSTORE,
    ERRORWAREHOUSE,
    PRODUCTIONRESULT,

    END;

    public static ServerMessageType getMessageType(String value) {
        return Enum.valueOf(ServerMessageType.class, value.toUpperCase());
    }
}
