package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;

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
