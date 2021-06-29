package it.polimi.ingsw.messages.fromServer.activateProduction;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.model.Coffer;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.model.Warehouse;

import java.util.HashMap;

/**
 * Contains the outcome of the activate production action on certain development slots. If the action requested
 * was not possible to be perform, the flag error is set to true and a redirection to the activateProduction method is
 * performed. If the player can't proceed with the action for unsufficient resources, the flag toMenu is set to true
 * and the player is redirected to the actions main menu.
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
                serverHandler.getLightModel().setWarehouse(new Warehouse(this.warehouse));
                serverHandler.getLightModel().setCoffer(new Coffer(this.coffer));
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
}

