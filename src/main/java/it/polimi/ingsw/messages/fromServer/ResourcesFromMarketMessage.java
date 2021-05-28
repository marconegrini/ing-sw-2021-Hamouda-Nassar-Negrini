package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.List;

public class ResourcesFromMarketMessage extends ServerMessage{

    List<Resource> resourcesFromMarket;

    public ResourcesFromMarketMessage(List<Resource> resourcesFromMarket) {
        super(ServerMessageType.RESOURCESFROMMARKET);
        this.resourcesFromMarket = resourcesFromMarket;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        ClientMessage message = serverHandler.getView().storeResources(this.resourcesFromMarket);
    }
}
