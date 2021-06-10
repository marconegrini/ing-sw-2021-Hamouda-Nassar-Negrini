package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;

/**
 * message returned if selected development card in the development cards matrix cannot be bought. Redirects
 * the client to the main menu
 */
public class BuyDVCardError extends ServerMessage{

    private String errorCause;
    private boolean toMenu;

    public BuyDVCardError(String errorCause, boolean toMenu){
        super(ServerMessageType.BUYDVCARDERROR);
        this.errorCause = errorCause;
        this.toMenu = toMenu;
    }
    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getView().showMessage(errorCause+"\n");
        if(toMenu){
            ClientMessage message = serverHandler.getView().selectAction(null, false);
            serverHandler.sendJson(message);
        } else {
            ClientMessage message = serverHandler.getView().selectAction("b", true);
            serverHandler.sendJson(message);
        }

    }
}
