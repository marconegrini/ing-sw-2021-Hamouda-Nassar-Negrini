package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;

/**
 * message sent from server TO CLIENT
 */

public abstract class   ServerMessage {

    protected ServerMessageType type;

    public ServerMessage(ServerMessageType type){
        this.type = type;
    }

    /**
     * method INVOKED ON THE CLIENT
     * @param serverHandler the server handler on the client, contains info like socket,Client, readers,writers and View type.
     */
    public abstract void clientProcess(ServerHandler serverHandler);

    public String toString(){
        return (" Received " + type.toString() + " message");
    }
}
