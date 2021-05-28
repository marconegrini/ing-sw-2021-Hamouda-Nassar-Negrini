package it.polimi.ingsw.messages.fromServer.warehouse;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.List;

public class ResourcesToStoreMessage extends ServerMessage {

    private List<Resource> resourcesToStore;
    String okMessage;
    private boolean turnDone;

    public ResourcesToStoreMessage(boolean turnDone, List<Resource> resourcesFromMarket, String okMessage) {
        super(ServerMessageType.RESOURCESTOSTORE);
        this.turnDone = turnDone;
        this.resourcesToStore = resourcesFromMarket;
        this.okMessage = okMessage;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        if(!turnDone) {
            System.out.println(resourcesToStore);
            ClientMessage message = serverHandler.getView().storeResources(this.resourcesToStore);
            serverHandler.sendJson(message);
        } else serverHandler.getView().showMessage(this.okMessage);
    }
}
