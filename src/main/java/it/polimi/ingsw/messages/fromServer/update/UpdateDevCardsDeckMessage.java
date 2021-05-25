package it.polimi.ingsw.messages.fromServer.update;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;

public class UpdateDevCardsDeckMessage extends ServerMessage {

    public UpdateDevCardsDeckMessage() {
        super(ServerMessageType.UPDATEDEVCARDSDECK);
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {

    }
}
