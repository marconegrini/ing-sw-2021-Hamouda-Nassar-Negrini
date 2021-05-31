package it.polimi.ingsw.messages.fromServer.leadercard;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.model.Coffer;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;
import java.util.List;

public class LeaderResultMessage extends ServerMessage {

    private boolean error;
    private boolean discarded;
    private boolean activated;
    private String result;
    private Integer leaderCardIndex;
    private HashMap<String, Integer> othersPositions;
    private Integer playerPosition;

    public LeaderResultMessage(boolean error, boolean discarded, boolean activated, String result, Integer leaderCardIndex, HashMap<String, Integer> othersPositions, Integer playerPosition) {
        super(ServerMessageType.LEADERCARDRESULT);
        this.error = error;
        this.discarded = discarded;
        this.activated = activated;
        this.result = result;
        this.leaderCardIndex = leaderCardIndex;
        this.othersPositions= othersPositions;
        this.playerPosition = playerPosition;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        String message = result;
        if(discarded && !error) {
            serverHandler.getLightModel().discardLeaderCard(leaderCardIndex);
            serverHandler.getLightModel().setFaithPathPosition(this.playerPosition);
            serverHandler.getLightModel().setOtherPlayersFaithPathPosition(this.othersPositions);
        }
        if(activated && !error) serverHandler.getLightModel().activateLeaderCard(leaderCardIndex);
        if(error) message = "Invalid action: " + result;
        serverHandler.getView().showMessage(message);
        ClientMessage toSend = serverHandler.getView().selectAction(null, false);
        serverHandler.sendJson(toSend);
    }
}
