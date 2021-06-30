package it.polimi.ingsw.messages.fromServer.activateProduction;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.server.Server;

import java.util.List;

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
            serverHandler.getView().showMessage(resultMessage, true, false);
        } else {
            //meaning that it was the only production activated or that no previous resources or faith points were obtained.
            if(error){
                serverHandler.getView().showMessage(resultMessage, true, false);
                ClientMessage message = serverHandler.getView().selectAction("", false);
                serverHandler.sendJson(message);
            } else {
                //Production activated
                serverHandler.getView().showMessage(resultMessage, true, false);
            }
        }
    }

}
