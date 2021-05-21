package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.server.handlers.ClientHandler;

import java.util.List;

public class ActivateProductionMessage extends ClientMessage{

    private List<Integer> slots;
    private Resource leaderResource;
    private boolean activatePersonalProduction;

    /**
     *
     * @param slots  slots indicate which slots the client want to activate
     * @param leaderResource  leaderResource indicate
     * @param activatePersonalProduction  activatePersonalProduction indicate if the player wants to activate the basic power of the personal board
     */
    public ActivateProductionMessage(List<Integer> slots, Resource leaderResource, boolean activatePersonalProduction) {
        super(ClientMessageType.ACTIVATE_PRODUCTION);
        this.slots = slots;
        this.leaderResource = leaderResource;
        this.activatePersonalProduction = activatePersonalProduction;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {

    }
}
