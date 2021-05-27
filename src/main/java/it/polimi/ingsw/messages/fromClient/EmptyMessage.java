package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.server.handlers.ClientHandler;

/**
 * EmptyMessage doesn't do anything
 */

public class EmptyMessage extends ClientMessage{

    public EmptyMessage() {
        super(ClientMessageType.EMPTY);
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {

    }
}
