package it.polimi.ingsw.messages.fromServer.update;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.server.Server;

import java.util.HashMap;

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
    }
}
