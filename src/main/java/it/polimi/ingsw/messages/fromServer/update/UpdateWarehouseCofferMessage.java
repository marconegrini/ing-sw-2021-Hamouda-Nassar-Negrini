package it.polimi.ingsw.messages.fromServer.update;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.gui.UpdateObjects;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.model.Coffer;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;

/**
 * Update message to update client's warehouse and coffer in light model
 */
public class UpdateWarehouseCofferMessage extends ServerMessage {

    private HashMap<Integer, Storage> warehouse;
    private HashMap<Resource, Integer> coffer;

    public UpdateWarehouseCofferMessage(HashMap<Integer, Storage> warehouse, HashMap<Resource, Integer> coffer) {
        super(ServerMessageType.UPDATEWAREHOUSECOFFER);
        this.warehouse = warehouse;
        this.coffer = coffer;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        Warehouse warehouse = new Warehouse(this.warehouse);
        Coffer coffer = new Coffer(this.coffer);
        serverHandler.getLightModel().setWarehouse(warehouse);
        serverHandler.getLightModel().setCoffer(coffer);
        if (!serverHandler.getIsCli()){
            UpdateObjects.updateWarehouse(warehouse);
            UpdateObjects.updateCoffer(coffer);
        }
    }
}
