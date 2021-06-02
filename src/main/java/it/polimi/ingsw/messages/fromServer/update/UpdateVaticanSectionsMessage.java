package it.polimi.ingsw.messages.fromServer.update;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.model.VaticanSection;

import java.util.List;

public class UpdateVaticanSectionsMessage extends ServerMessage {

    List<VaticanSection> vaticanSections;

    public UpdateVaticanSectionsMessage(List<VaticanSection> vaticanSections) {
        super(ServerMessageType.UPDATEVATICANSECTIONS);
        this.vaticanSections = vaticanSections;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getLightModel().setVaticanSections(this.vaticanSections);
    }
}
