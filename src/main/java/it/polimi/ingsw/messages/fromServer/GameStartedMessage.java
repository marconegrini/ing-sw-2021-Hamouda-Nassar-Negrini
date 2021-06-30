package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;

/**
 * Sent from the server after assigning the calamaio and after every player has choosed his first resources
 */

public class GameStartedMessage extends ServerMessage{

    private boolean isMultiPlayer;

    public GameStartedMessage(boolean isMultiPlayer) {
        super(ServerMessageType.GAMESTARTED);
        this.isMultiPlayer = isMultiPlayer;
    }

    /**
     * method INVOKED ON THE CLIENT to start the game. On the CLI it shows a message. On the GUI it changes the scene.
     *
     * @param serverHandler the server handler on the client, contains info like socket,Client, readers,writers and View type.
     */
    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getView().startGame(isMultiPlayer);
    }
}
