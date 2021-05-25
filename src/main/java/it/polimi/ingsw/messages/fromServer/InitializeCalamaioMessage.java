package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;



public class InitializeCalamaioMessage extends ServerMessage {

    String str;
    public InitializeCalamaioMessage(String strIn) {
        super(ServerMessageType.CALAMAIO);
        this.str=strIn;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getView().initializeCalamaio(str);

    }
}
