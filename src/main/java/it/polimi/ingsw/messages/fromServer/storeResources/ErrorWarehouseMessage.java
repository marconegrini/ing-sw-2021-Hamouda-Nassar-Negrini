package it.polimi.ingsw.messages.fromServer.storeResources;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.List;

/**
 * Error message if the specified insertion cannot be performed. The user is redirected to storeResources method to change
 * the request.
 */
public class ErrorWarehouseMessage extends ServerMessage {

    private String error;
    private List<Resource> resourcesToStore;
    public ErrorWarehouseMessage(String error, List<Resource> resourcesToStore) {
        super(ServerMessageType.ERRORWAREHOUSE);
        this.error = error;
        this.resourcesToStore = resourcesToStore;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getView().showMessage(error);
        ClientMessage message = serverHandler.getView().storeResources(this.resourcesToStore);
        serverHandler.sendJson(message);
    }
}
