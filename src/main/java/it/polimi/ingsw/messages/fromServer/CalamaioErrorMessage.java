package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;

/**
 * sent from the server if the action requested by the client during calamaio setting up cannot be performed.
 */
public class CalamaioErrorMessage extends  ServerMessage{

    String str;
    public CalamaioErrorMessage(String strIn) {
        super(ServerMessageType.CALAMAIOERR);
        this.str=strIn;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        ClientMessage message = serverHandler.getView().calamaioErrHandelr(str);
        serverHandler.sendJson(message);
    }


}
