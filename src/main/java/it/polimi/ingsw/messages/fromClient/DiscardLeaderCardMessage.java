package it.polimi.ingsw.messages.fromClient;

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

    }
}
