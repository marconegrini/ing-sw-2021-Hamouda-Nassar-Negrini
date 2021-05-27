package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.server.handlers.ClientHandler;

/**
 * ExitFromGameMessage is used to send a message to the server and remove
 * the clientHandler from the server.
 */


public class ExitFromGameMessage extends ClientMessage{

    public ExitFromGameMessage() {
        super(ClientMessageType.EXITFROMGAME);
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {
        clientHandler.exitFromGame();
    }
}
