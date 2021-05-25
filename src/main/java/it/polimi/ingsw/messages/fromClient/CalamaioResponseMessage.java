package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.server.handlers.ClientHandler;

public class CalamaioResponseMessage extends ClientMessage{

    public CalamaioResponseMessage(){
        super(ClientMessageType.CALAMAIORESPONSE);

    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {

    }
}
