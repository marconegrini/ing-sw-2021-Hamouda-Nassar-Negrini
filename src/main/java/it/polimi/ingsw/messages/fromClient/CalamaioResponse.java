package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.server.handlers.ClientHandler;

public class CalamaioResponse  extends ClientMessage{

    public CalamaioResponse(){
        super(ClientMessageType.CALAMAIORESPONSE);

    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {

    }
}
