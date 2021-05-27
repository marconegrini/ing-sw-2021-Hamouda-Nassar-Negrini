package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;

/**
 * EndGameMessage is used from the server to communicate to the client to end the game from his side.
 */

public class EndGameMessage extends ServerMessage{

    public EndGameMessage() {
        super(ServerMessageType.ENDGAME);
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.stop();
    }
}
