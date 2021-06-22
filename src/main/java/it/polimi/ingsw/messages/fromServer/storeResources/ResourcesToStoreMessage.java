package it.polimi.ingsw.messages.fromServer.storeResources;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;
import java.util.List;

/**
 * Message sent from server containing resources take from market first, resources remaining to be inserted then.
 * Firstly sent from pickResources method inside turnManager, is then sent from insertResourcesInWarehouse.
 */
public class ResourcesToStoreMessage extends ServerMessage {

    private List<Resource> resourcesToStore;
    String okMessage;
    HashMap<Integer, Storage> warehouse;
    private boolean turnDone;
    private boolean updateLeaderCards = false;

    public ResourcesToStoreMessage(boolean turnDone, List<Resource> resourcesFromMarket, String okMessage, HashMap<Integer, Storage> warehouse) {
        super(ServerMessageType.RESOURCESTOSTORE);
        this.turnDone = turnDone;
        this.resourcesToStore = resourcesFromMarket;
        this.okMessage = okMessage;
        this.warehouse = warehouse;
    }


    public ResourcesToStoreMessage(boolean turnDone,boolean updateLeaderCards, List<Resource> resourcesFromMarket, String okMessage, HashMap<Integer, Storage> warehouse) {
        super(ServerMessageType.RESOURCESTOSTORE);
        this.turnDone = turnDone;
        this.resourcesToStore = resourcesFromMarket;
        this.okMessage = okMessage;
        this.warehouse = warehouse;
        this.updateLeaderCards = updateLeaderCards;
    }


    @Override
    public void clientProcess(ServerHandler serverHandler) {
        if (!turnDone) {
            System.out.println(resourcesToStore); //testing
            Warehouse newWarehouse = new Warehouse(warehouse);
            serverHandler.getLightModel().setWarehouse(newWarehouse);
            serverHandler.getView().showMessage(this.okMessage);
            ClientMessage message = serverHandler.getView().storeResources(this.resourcesToStore);
            serverHandler.sendJson(message);
        } else serverHandler.getView().showMessage(this.okMessage);
        if (updateLeaderCards){
//            ClientMessage outcome =
//            clientHandler.sendJson(outcome);
        }
    }

    //needed for testing
    public String getMessage(){
        return this.okMessage;
    }
}

