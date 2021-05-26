package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.handlers.ClientHandler;

import java.util.List;

public class InsertResourcesInWarehouseMessage extends ClientMessage{

    private List<Resource> resources;
    private Integer shelf;

    public InsertResourcesInWarehouseMessage(List<Resource> resources, Integer shelf){
        super(ClientMessageType.INSERTRESOURCESINWAREHOUSE);
        this.resources = resources;
        this.shelf = shelf;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {
        Player player = clientHandler.getPlayer();
        TurnManager turnManager = clientHandler.getTurnManager();
        ServerMessage outcome = turnManager.insertResourcesInWarehouse(clientHandler.getPlayer(), shelf, resources);
        clientHandler.sendJson(outcome);
    }
}
