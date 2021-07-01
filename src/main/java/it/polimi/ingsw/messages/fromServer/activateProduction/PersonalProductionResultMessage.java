package it.polimi.ingsw.messages.fromServer.activateProduction;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.server.Server;

import java.util.List;

/**
 * The playing user has the possibility to:
 * 1) activate personal production instead of the normal one
 * 2) activate normal production and eventually activate the personal later.
 * In the first case this message is the only one sent: if the production fails the player is requested to select another action,
 * otherwise terminates the turn.
 * In the second case this message is sent from turn manager after a normal production has been performed:
 * - if the normal production failed and the personal one succeeds, the player terminates the turn.
 * - if the normal production failed and the personal one fails, the player is requested to select another action to perform.
 * - if the normal production succeeded, with any outcome of the personal production the player will terminate the turn.
 */
public class PersonalProductionResultMessage extends ServerMessage {
    private boolean error;
    private String resultMessage;
    private boolean asSecondProduction;

    public PersonalProductionResultMessage(boolean error, String resultMessage, boolean asSecondProduction) {
        super(ServerMessageType.PERSONALPRODUCTIONRESULT);
        this.error = error;
        this.resultMessage = resultMessage;
        this.asSecondProduction = asSecondProduction;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        if(asSecondProduction){
            //meaning that it was the second production activated after the main one and the player obtained resources.
            //Show message and terminate.
            serverHandler.getView().showMessage(resultMessage, false, false);
        } else {
            //meaning that it was the only production activated or that no previous resources or faith points were obtained.
            if(error){
                serverHandler.getView().showMessage(resultMessage, false, false);
                ClientMessage message = serverHandler.getView().selectAction("", false);
                serverHandler.sendJson(message);
            } else {
                //Production activated
                serverHandler.getView().showMessage(resultMessage, false, false);
            }
        }
    }

    //for testing
    public String getMessage(){
        return this.resultMessage;
    }

}
