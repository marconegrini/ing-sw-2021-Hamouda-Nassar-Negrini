package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.gui.SceneManager;
import it.polimi.ingsw.client.gui.UpdateObjects;
import it.polimi.ingsw.client.gui.controllers.ControllerGUI;
import it.polimi.ingsw.enumerations.LorenzoCardType;

/**
 * Message sent from server to client to notify about a single player action event. This event occurs
 * each time the player in a single game terminate his turn's action.
 */
public class SinglePlayerActionMessage extends ServerMessage{

    private String actionPerformed;
    private LorenzoCardType lorenzoCard;

    public SinglePlayerActionMessage(String actionPerformed, LorenzoCardType lorenzoCard) {
        super(ServerMessageType.SINGLEPLAYERACTION);
        this.actionPerformed = actionPerformed;
        this.lorenzoCard = lorenzoCard;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        if (!serverHandler.getIsCli()){
            UpdateObjects.updateLorenzoCard(lorenzoCard, SceneManager.getScene());
        }
        serverHandler.getView().showMessage(actionPerformed, false, false);
    }
}
