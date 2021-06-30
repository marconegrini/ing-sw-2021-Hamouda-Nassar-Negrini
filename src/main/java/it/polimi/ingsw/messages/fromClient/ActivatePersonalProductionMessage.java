package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.handlers.ClientHandler;

import java.util.List;

/**
 * contains information about:
 * 1) the two resources the user wants as production input. The will be taken from warehouse or coffer.
 * 2) the resource the user wants as production output. This resource will be stored in coffer.
 * 3) If one or two production power leader card is/are activated, selected resources will be chosen by the user and inserted
 *    in leaderResource arrayList
 */
public class ActivatePersonalProductionMessage extends ClientMessage{

    private Resource prodIn1;
    private Resource prodIn2;
    private Resource prodOut;
    private List<Resource> leaderResource;
    boolean activate;
    boolean asSecondProduction;


    public ActivatePersonalProductionMessage(Resource prodIn1, Resource prodIn2, Resource prodOut,
                                             List<Resource> leaderResource,
                                             boolean asSecondProduction, boolean activate) {
        super(ClientMessageType.ACTIVATEPERSONALPRODUCTION);
        this.prodIn1 = prodIn1;
        this.prodIn2 = prodIn2;
        this.prodOut = prodOut;
        this.leaderResource = leaderResource;
        this.asSecondProduction = asSecondProduction;
        this.activate = activate;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {
        TurnManager turnManager = clientHandler.getTurnManager();
        ServerMessage outcome = turnManager.activatePersonalProduction(clientHandler.getPlayer(), prodIn1, prodIn2, prodOut, leaderResource, asSecondProduction, activate);
        clientHandler.sendJson(outcome);
    }
}
