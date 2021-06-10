package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.handlers.ClientHandler;

import java.util.List;

/**
 * sent from the client and specifies warehouse shelf to insert resources contained in resources ArrayList.
 * Message sent after resources have been picked from the market. The user has the possibility to insert or
 * discard picked resources.
 * Resources that the user wants to insert in different warehouse's shelves need to be packed in different
 * InsertResourcesInWarehouseMessage - s.
 */
public class InsertResourcesInWarehouseMessage extends ClientMessage{

    private boolean discard;
    private List<Resource> resources;
    private Integer shelf;

    public InsertResourcesInWarehouseMessage(boolean discard, List<Resource> resources, Integer shelf){
        super(ClientMessageType.INSERTRESOURCESINWAREHOUSE);
        this.discard = discard;
        this.resources = resources;
        this.shelf = shelf;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {
        Player player = clientHandler.getPlayer();
        TurnManager turnManager = clientHandler.getTurnManager();
        ServerMessage outcome = turnManager.insertResourcesInWarehouse(clientHandler.getPlayer(), shelf, resources, discard);
        clientHandler.sendJson(outcome);
    }
}
