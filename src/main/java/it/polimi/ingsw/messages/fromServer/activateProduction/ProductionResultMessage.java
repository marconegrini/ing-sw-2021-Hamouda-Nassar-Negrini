package it.polimi.ingsw.messages.fromServer.activateProduction;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.List;

/**
 * Contains the outcome of the activate production action on certain development slots. If the action requested
 * was not possible to be perform, the flag error is set to true and a redirection to the activateProduction method is
 * performed. If the player can't proceed with the action for unsufficient resources, the flag toMenu is set to true
 * and the player is redirected to the actions main menu.
 */
public class ProductionResultMessage extends ServerMessage {

    private boolean error;
    private String resultMessage;
    private boolean toMenu;

    public ProductionResultMessage(boolean error, String resultMessage, boolean toMenu) {
        super(ServerMessageType.PRODUCTIONRESULT);
        this.error = error;
        this.resultMessage = resultMessage;
        this.toMenu = toMenu;
    }

    /**
     * toMenu redirects to the actions menu. If false, redirects the client inside Activate Production routine.
     * @param serverHandler the server handler on the client, contains info like socket,Client, readers,writers and View type.
     */
    @Override
    public void clientProcess(ServerHandler serverHandler) {
        if(error){
            serverHandler.getView().showMessage(resultMessage, true, false);
            ClientMessage message = null;
            if(toMenu) message = serverHandler.getView().selectAction("",false);
            else message = serverHandler.getView().activateProduction();
            serverHandler.sendJson(message);
        } else serverHandler.getView().showMessage(resultMessage, true, false);
    }
}

