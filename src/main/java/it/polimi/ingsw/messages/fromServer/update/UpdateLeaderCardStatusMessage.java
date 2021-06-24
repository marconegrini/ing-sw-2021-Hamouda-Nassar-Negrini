package it.polimi.ingsw.messages.fromServer.update;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.gui.SceneManager;
import it.polimi.ingsw.client.gui.UpdateObjects;
import it.polimi.ingsw.client.gui.controllers.ControllerGUI;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.ANSITextFormat;

import java.util.List;

/**
 * Sent from server to client each time a leader action is performed:
 * this message will update the client about the new status of leader cards, i.e. if a card has
 * been activated or discarded.
 */
public class UpdateLeaderCardStatusMessage extends ServerMessage {

    private List<LeaderCard> leaderCards;

    public UpdateLeaderCardStatusMessage(List<LeaderCard> leaderCards) {
        super(ServerMessageType.UPDATELEADERCARDSTATUS);
        this.leaderCards = leaderCards;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getLightModel().setLeaderCards(leaderCards);
        if (!serverHandler.getIsCli()){
            UpdateObjects.updateLeaderCards(leaderCards, SceneManager.getScene());
        }
    }
}
