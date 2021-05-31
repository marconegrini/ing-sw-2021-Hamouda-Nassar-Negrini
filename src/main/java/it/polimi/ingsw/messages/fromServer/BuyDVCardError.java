package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;

public class BuyDVCardError extends ServerMessage{

    String errorCause;
    public BuyDVCardError(String errorCause){
        super(ServerMessageType.BUYDVCARDERROR);
        this.errorCause = errorCause;
    }
    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getView().showMessage(errorCause+"\n");
        ClientMessage message = serverHandler.getView().selectAction("b",true);
        serverHandler.sendJson(message);

    }
}
