package it.polimi.ingsw.messages.fromServer.update;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.gui.SceneManager;
import it.polimi.ingsw.client.gui.UpdateObjects;
import it.polimi.ingsw.client.gui.controllers.ControllerGUI;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.model.VaticanSection;
import javafx.scene.Scene;

import java.util.List;

/**
 * update message sent with UpdateFaithPathMessage to update client's Vatican Sections
 */
public class UpdateVaticanSectionsMessage extends ServerMessage {

    List<VaticanSection> vaticanSections;

    public UpdateVaticanSectionsMessage(List<VaticanSection> vaticanSections) {
        super(ServerMessageType.UPDATEVATICANSECTIONS);
        this.vaticanSections = vaticanSections;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getLightModel().setVaticanSections(this.vaticanSections);
        if (!serverHandler.getIsCli()){
            UpdateObjects.updatePopeCards(vaticanSections, SceneManager.getScene());
        }
    }
}
