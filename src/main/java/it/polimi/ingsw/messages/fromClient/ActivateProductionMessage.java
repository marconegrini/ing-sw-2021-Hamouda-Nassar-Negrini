package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.server.handlers.ClientHandler;

import java.util.List;

/**
 * Invoked to activate productions. Contains information about the slot numbers in which activate production
 * (max 3) and, if the user has a production power leader card activated, optional leader resources chosen in
 * leaderResource.
 */
public class ActivateProductionMessage extends ClientMessage{

    private List<Integer> slots;
    private List<Resource> leaderResource;

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
