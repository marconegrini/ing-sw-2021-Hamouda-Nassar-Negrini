package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;

public class EndMessage extends ServerMessage{

    public EndMessage() {

        super(ServerMessageType.END);

    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        System.out.println("inside client process end message");
        serverHandler.stop();
    }
}
