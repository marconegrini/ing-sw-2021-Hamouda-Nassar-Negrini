package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;

/**
 * Sent from the server if requested start from the client in waiting room cannot be performed
 */
public class ErrorStartGameMessage extends ServerMessage{

    String message;

    public ErrorStartGameMessage(String message) {
        super(ServerMessageType.ERRORSTARTGAME);
        this.message = message;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getView().showMessage(message);

        if (serverHandler.getIsCli()) {
            Runnable runnable = () -> {
                serverHandler.sendJson(serverHandler.getView().waitingRoom());
            };

            Thread waitingRoom = new Thread(runnable);
            waitingRoom.start();
        }
    }
}
