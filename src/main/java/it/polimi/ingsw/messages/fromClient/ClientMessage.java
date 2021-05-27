package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.server.handlers.ClientHandler;


/**
 * message sent from client TO SERVER
 */
public abstract class ClientMessage {

    protected ClientMessageType type;

    public ClientMessage(ClientMessageType type){
        this.type = type;
    }

    public abstract void serverProcess(ClientHandler clientHandler);

    public String toString(){
        return (" Received " + type.toString() + " message");
    }
}
