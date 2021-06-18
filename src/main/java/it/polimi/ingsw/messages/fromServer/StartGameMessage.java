package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;

/**
 * Message sent to clients from server to notify about the start of the game. The message is sent in multiplayer
 * only after four players joined the game, or when a player types START before the number of four players
 * in waiting room is reached.
 */
public class StartGameMessage extends ServerMessage{

    public StartGameMessage() {
        super(ServerMessageType.STARTGAME);
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getView().endWaitingRoom();
    }
}
