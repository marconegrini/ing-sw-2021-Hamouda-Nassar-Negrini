package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.fromServer.ErrorStartGameMessage;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.handlers.ClientHandler;

/**
 * Sent from a client if in waiting room there are less than 4 players. Enables a player to let the game start
 * if less than 4 players are in waiting room.
 */
public class AskStartGameMessage extends ClientMessage{

    public AskStartGameMessage() {
        super(ClientMessageType.ASKSTARTGAME);
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {
        if (Server.getPlayersNumber() >= 2){
            Server.startMultiplayerGame();
        } else clientHandler.sendJson(new ErrorStartGameMessage("You are alone. Wait for other players to start the game."));
    }
}
