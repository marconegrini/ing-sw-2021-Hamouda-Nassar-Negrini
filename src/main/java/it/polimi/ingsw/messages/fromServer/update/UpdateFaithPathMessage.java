package it.polimi.ingsw.messages.fromServer.update;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.gui.UpdateObjects;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.server.Server;

import java.util.HashMap;

/**
 * update method to update in client's light model the faith path
 */
public class UpdateFaithPathMessage extends ServerMessage {
    HashMap<String, Integer> othersPositions;
    Integer playerPosition;

    public UpdateFaithPathMessage(HashMap<String, Integer> othersPositions, Integer playerPosition) {
        super(ServerMessageType.UPDATEFAITHPATH);
        this.othersPositions = othersPositions;
        this.playerPosition = playerPosition;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getLightModel().setOtherPlayersFaithPathPosition(this.othersPositions);
        serverHandler.getLightModel().setFaithPathPosition(this.playerPosition);
        if (!serverHandler.getIsCli()){
            UpdateObjects.updateFaithPath(othersPositions, playerPosition);
        }
    }
}
