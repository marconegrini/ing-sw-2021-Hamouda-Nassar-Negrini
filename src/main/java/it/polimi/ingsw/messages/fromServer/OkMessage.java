package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.server.Server;

public class OkMessage extends ServerMessage{


    public OkMessage(ServerMessageType type) {
        super(type);
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {

    }
}
