package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;

public class InWaitingRoomMessage extends ServerMessage{

    public InWaitingRoomMessage() {
        super(ServerMessageType.INWAITINGROOM);
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {

        if (serverHandler.getIsCli()){
            serverHandler.getView().showMessage("You are in waiting room. ",true, false);

            Runnable runnable = () -> {
                serverHandler.sendJson(serverHandler.getView().waitingRoom());
            };

            Thread waitingRoom = new Thread(runnable);
            waitingRoom.start();
        } else{
            serverHandler.getView().waitingRoom();
        }



    }
}
