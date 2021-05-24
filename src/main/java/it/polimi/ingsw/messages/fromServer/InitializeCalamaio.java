package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;


public class InitializeCalamaio extends ServerMessage {

    String str;
    public InitializeCalamaio(String strIn) {
        super(ServerMessageType.CALAMAIO);
        this.str=strIn;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        ClientMessage message = serverHandler.getView().initializeCalamaio(str);
        serverHandler.sendJson(message);
    }
}
