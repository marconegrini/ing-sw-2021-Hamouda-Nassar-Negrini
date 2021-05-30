package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.server.handlers.ClientHandler;

import java.util.List;

public class ActivateProductionMessage extends ClientMessage{

    private List<Integer> slots;
    private List<Resource> leaderResource;

    /**
     * @param slots  slots indicate which slots the client want to activate
     * @param leaderResource  leaderResource indicate
     */
    public ActivateProductionMessage(List<Integer> slots, List<Resource> leaderResource) {
        super(ClientMessageType.ACTIVATEPRODUCTION);
        this.slots = slots;
        this.leaderResource = leaderResource;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {
        ServerMessage message = clientHandler.getTurnManager().activateProduction(clientHandler.getPlayer(), slots, leaderResource);
        clientHandler.sendJson(message);
    }
}
