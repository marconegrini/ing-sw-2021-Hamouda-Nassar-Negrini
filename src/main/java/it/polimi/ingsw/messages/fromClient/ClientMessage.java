package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.server.handlers.ClientHandler;

public abstract class ClientMessage {

    private ClientMessageType type;

    public ClientMessage(ClientMessageType type){
        this.type = type;
    }

    public abstract void serverProcess(ClientHandler clientHandler);

}
