package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.server.handlers.ClientHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;


/**
 * messages sent from client TO SERVER. Each message will be processed independently from the message type.
 * To let the message have effect, when processing it the server will access to turn manager's methods, that
 * are controller's methods.
 */
public abstract class ClientMessage {

    protected static Logger logger = Logger.getLogger(ClientMessage.class.getName());

    protected ClientMessageType type;

    public ClientMessage(ClientMessageType type){
        this.type = type;
    }

    public abstract void serverProcess(ClientHandler clientHandler);

    public String toString(){
        return (" Received " + type.toString() + " message");
    }
}
