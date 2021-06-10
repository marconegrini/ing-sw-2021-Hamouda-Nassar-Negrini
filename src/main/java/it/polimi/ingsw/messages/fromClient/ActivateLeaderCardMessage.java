package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.handlers.ClientHandler;

/**
 * contains leader cards' arraylist's index to activate a leader card.
 * It will be processed by the server and will modify the model.
 */
public class ActivateLeaderCardMessage extends ClientMessage{

    private Integer slot;

    public ActivateLeaderCardMessage(Integer slot) {
        super(ClientMessageType.ACTIVATELEADERCARD);
        this.slot = slot;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {
        Player player = clientHandler.getPlayer();
        TurnManager turnManager = clientHandler.getTurnManager();
        ServerMessage outcome = turnManager.activateLeaderCard(clientHandler.getPlayer(), slot);
        clientHandler.sendJson(outcome);
    }
}
