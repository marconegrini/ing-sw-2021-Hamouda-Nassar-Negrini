package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.server.Server;

public class ErrorMessage extends ServerMessage{

    private String errorMessage;

    public ErrorMessage(String errorMessage){
        super(ServerMessageType.ERROR);
        this.errorMessage = errorMessage;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getView().showMessage(errorMessage);
    }

    @Override
    public String toString(){
        return (" Received " + type.toString() + " message\n" +
                "Error: " + errorMessage);
    }
}
