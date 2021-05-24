package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;



public class InitializeCalamaio extends ServerMessage {

    String str;
    public InitializeCalamaio(String strIn) {
        super(ServerMessageType.CALAMAIO);
        this.str=strIn;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getView().initializeCalamaio(str);

    }
}
