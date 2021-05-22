package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.server.handlers.ClientHandler;

public class ActivateLeaderCardMessage extends ClientMessage{

    private Integer slot;

    public ActivateLeaderCardMessage(Integer slot) {
        super(ClientMessageType.ACTIVATELEADERCARD);
        this.slot = slot;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {

    }
}
