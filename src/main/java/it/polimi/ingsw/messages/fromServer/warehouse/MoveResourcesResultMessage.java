package it.polimi.ingsw.messages.fromServer.warehouse;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.gui.SceneManager;
import it.polimi.ingsw.client.gui.UpdateObjects;
import it.polimi.ingsw.client.gui.controllers.ControllerGUI;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.messages.fromServer.update.UpdateWarehouseCofferMessage;

/**
 * update message to update client's light model warehouse. Sent after a MoveWarehouseResourcesMessage
 * has been sent from the client
 */
public class MoveResourcesResultMessage extends ServerMessage {

    private boolean error;
    private Integer sourceStorage;
    private Integer destStorage;
    private String message;

    public MoveResourcesResultMessage(boolean error, Integer sourceStorage, Integer destStorage, String message) {
        super(ServerMessageType.MOVERESOURCESRESULT);
        this.error = error;
        this.sourceStorage = sourceStorage;
        this.destStorage = destStorage;
        this.message = message;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        String message = this.message;
        if(!error)
            serverHandler.getLightModel().moveWarehouseResources(sourceStorage, destStorage);
        else message = "Invalid action: " + message;
        System.out.println(message);
        if (serverHandler.getIsCli()) {
            ClientMessage toSend = serverHandler.getView().selectAction(null, false);
            serverHandler.sendJson(toSend);
        } else{
            UpdateObjects.updateWarehouse(serverHandler.getLightModel().getWarehouse(), SceneManager.getPopUpScene());
        }
    }

    //needed for testing
    public boolean getError(){
        return this.error;
    }
}
