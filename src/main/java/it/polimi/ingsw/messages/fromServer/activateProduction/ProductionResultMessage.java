package it.polimi.ingsw.messages.fromServer.activateProduction;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.gui.UpdateObjects;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.model.Coffer;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.model.Warehouse;

import java.util.HashMap;

/**
 * The playing user has the possibility to:
 * 1) activate normal production and eventually activate the personal later.
 * 2) activate personal production instead of the normal one
 * In the first case this message is the first to be sent: if the production fails due to parameters integrity errors,
 * the player is requested to perform again the normal production. If the production fails due to insufficient resources or
 * if the production succeed, the player will be requested from turn manager to perform the personal production.
 * The production routine can have three different endings:
 * - if the normal production fails and the personal one succeeds, the player terminates the turn.
 * - if the normal production fails and the personal one fails, the player is requested to select another action to perform.
 * - if the normal production succeeds, with any outcome of the personal production the player will terminate the turn.
 */
public class ProductionResultMessage extends ServerMessage {

    private boolean error;
    private String resultMessage;
    private boolean goToPersonalProduction;
    private HashMap<Integer, Storage> warehouse;
    private HashMap<Resource, Integer> coffer;

    public ProductionResultMessage(boolean error, String resultMessage, boolean goToPersonalProduction,HashMap<Integer, Storage> warehouse, HashMap<Resource, Integer> coffer) {
        super(ServerMessageType.PRODUCTIONRESULT);
        this.error = error;
        this.resultMessage = resultMessage;
        this.goToPersonalProduction = goToPersonalProduction;
        this.warehouse = warehouse;
        this.coffer = coffer;
    }

    /**
     * toMenu redirects to the actions menu. If false, redirects the client inside Activate Production routine.
     * @param serverHandler the server handler on the client, contains info like socket,Client, readers,writers and View type.
     */
    @Override
    public void clientProcess(ServerHandler serverHandler) {
        ClientMessage message = null;

        if(error){
            serverHandler.getView().showMessage(resultMessage, true, false);
            if(goToPersonalProduction){
                //the player couldn't activate production due to insufficient resources. Move on and ask for the
                //the activation of personal production
                Warehouse newWarehouse = new Warehouse(this.warehouse);
                Coffer newCoffer = new Coffer(this.coffer);
                serverHandler.getLightModel().setWarehouse(newWarehouse);
                serverHandler.getLightModel().setCoffer(newCoffer);
                if (!serverHandler.getIsCli()){
                    UpdateObjects.updateWarehouse(newWarehouse);
                    UpdateObjects.updateCoffer(newCoffer);
                }
                message = serverHandler.getView().activatePersonalProduction();
            } else {
                //ask again to activate normal production because specified parameters are incorrect
                message = serverHandler.getView().activateProduction();
            }
        } else {
            //the player was able to activate normal production. Ask to it if he wants to activate
            //also the personal one.
            serverHandler.getView().showMessage(resultMessage, true, false);
            serverHandler.getLightModel().setWarehouse(new Warehouse(this.warehouse));
            serverHandler.getLightModel().setCoffer(new Coffer(this.coffer));
            message = serverHandler.getView().activatePersonalProduction();
        }
        serverHandler.sendJson(message);
    }

    //for debugging
    public boolean getError(){
        return this.error;
    }
}

