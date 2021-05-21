package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.server.Server;

public abstract class ServerMessage {

    private ServerMessageType type;

    public ServerMessage(ServerMessageType type){
        this.type = type;
    }

    public abstract void clientProcess(ServerHandler serverHandler);
}
