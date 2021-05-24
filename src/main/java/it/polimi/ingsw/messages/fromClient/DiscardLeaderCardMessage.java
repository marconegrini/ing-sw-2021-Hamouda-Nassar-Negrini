package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.handlers.ClientHandler;

public class DiscardLeaderCardMessage extends ClientMessage{

    Integer slot;

    /**
     *
     * @param slot  slot is the position of the leader card that the player wants to discard
     */
    public DiscardLeaderCardMessage(Integer slot) {
        super(ClientMessageType.DISCARDLEADERCARD);
        this.slot = slot;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {
        Player player = clientHandler.getPlayer();
        TurnManager turnManager = clientHandler.getTurnManager();
        ServerMessage outcome = turnManager.discardLeaderCard(clientHandler.getPlayer(), slot);
        clientHandler.sendJson(outcome);
    }
}
