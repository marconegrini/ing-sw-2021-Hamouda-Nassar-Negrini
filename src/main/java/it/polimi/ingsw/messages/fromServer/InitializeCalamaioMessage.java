package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;

/**
 * Message sent from server to client to setting up calamaio and player's turn in game
 */
public class InitializeCalamaioMessage extends ServerMessage {

    String str;
    public InitializeCalamaioMessage(String strIn) {
        super(ServerMessageType.CALAMAIO);
        this.str=strIn;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        ClientMessage message = serverHandler.getView().initializeCalamaio(str);
        serverHandler.sendJson(message);
    }
}
