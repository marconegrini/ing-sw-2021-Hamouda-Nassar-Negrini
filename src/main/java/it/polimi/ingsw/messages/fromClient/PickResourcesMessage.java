package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.update.UpdateLeaderCardStatusMessage;
import it.polimi.ingsw.messages.fromServer.update.UpdateMarketboardMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.handlers.ClientHandler;

/**
 * Message sent from the user after the action "pick resources from market" has been selected.
 * Contains the boolean that specifies if rowOrColNum is the number of row or column, and the respective
 * number.
 */
public class PickResourcesMessage extends ClientMessage{

    private boolean isRow;
    private int rowOrColNum;
    private boolean useStorageLCs;

    public PickResourcesMessage(boolean isRow, int rowOrColNum, boolean useStorageLCs) {
        super(ClientMessageType.PICKRESOURCES);
        this.rowOrColNum = rowOrColNum;
        this.isRow = isRow;
        this.useStorageLCs = useStorageLCs;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {
        Player player = clientHandler.getPlayer();
        TurnManager turnManager = clientHandler.getTurnManager();
        ServerMessage outcome = turnManager.pickResources(clientHandler.getPlayer(), isRow, rowOrColNum, useStorageLCs);
        clientHandler.sendJson(outcome);

        //clientHandler.sendJson(new UpdateMarketboardMessage(clientHandler.g));
    }
}
