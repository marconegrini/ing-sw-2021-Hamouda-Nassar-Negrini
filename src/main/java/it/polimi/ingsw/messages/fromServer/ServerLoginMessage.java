package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.LoginMessage;

import java.util.Scanner;

public class ServerLoginMessage extends ServerMessage{

    public ServerLoginMessage() {
        super(ServerMessageType.LOGIN);
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {

        serverHandler.sendJson(serverHandler.getViewHandler().logClient());
    }
}
