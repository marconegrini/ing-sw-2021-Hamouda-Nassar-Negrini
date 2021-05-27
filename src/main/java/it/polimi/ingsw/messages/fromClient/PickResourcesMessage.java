package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.update.UpdateMarketboardMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.handlers.ClientHandler;

public class PickResourcesMessage extends ClientMessage{

    private boolean isRow;
    private int rowOrColNum;

    public PickResourcesMessage(boolean isRow, int rowOrColNum) {
        super(ClientMessageType.PICKRESOURCES);
        this.rowOrColNum = rowOrColNum;
        this.isRow = isRow;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {
        Player player = clientHandler.getPlayer();
        TurnManager turnManager = clientHandler.getTurnManager();
        ServerMessage outcome = turnManager.pickResources(clientHandler.getPlayer(), isRow, rowOrColNum);
        clientHandler.sendJson(outcome);
        //clientHandler.sendJson(new UpdateMarketboardMessage(clientHandler.g));
    }
}
