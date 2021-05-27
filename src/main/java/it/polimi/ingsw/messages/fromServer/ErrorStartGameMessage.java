package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;

public class ErrorStartGameMessage extends ServerMessage{

    String message;

    public ErrorStartGameMessage(String message) {
        super(ServerMessageType.ERRORSTARTGAME);
        this.message = message;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getView().showMessage(message);
        Runnable runnable = () -> {
            serverHandler.sendJson(serverHandler.getView().waitingRoom());
        };

        Thread waitingRoom = new Thread(runnable);
        waitingRoom.start();
        //serverHandler.sendJson(new InWaitingRoomMessage());
    }
}
