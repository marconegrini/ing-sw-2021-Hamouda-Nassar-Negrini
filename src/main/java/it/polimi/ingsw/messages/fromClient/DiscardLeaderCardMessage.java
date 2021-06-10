package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.handlers.ClientHandler;

/**
 * Sent from the client to discard a leader card. Specifies index of leader cards' arraylist of
 * the leader card to discard.
 */
public class DiscardLeaderCardMessage extends ClientMessage{

    Integer slot;

    /**
     * @param slot  slot is the position of the leader card that the player wants to discard
     */
    public DiscardLeaderCardMessage(Integer slot) {
        super(ClientMessageType.DISCARDLEADERCARD);
        this.slot = slot;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {
        TurnManager turnManager = clientHandler.getTurnManager();
        ServerMessage outcome = turnManager.discardLeaderCard(clientHandler.getPlayer(), slot);
        clientHandler.sendJson(outcome);
    }
}
