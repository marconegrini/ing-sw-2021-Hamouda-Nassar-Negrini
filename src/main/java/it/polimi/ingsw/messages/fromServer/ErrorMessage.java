package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.server.Server;

public class ErrorMessage extends ServerMessage{

    private String errorMessage;

    public ErrorMessage(String errorMessage){

        this.errorMessage = errorMessage;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        System.out.println(errorMessage);
    }
}
