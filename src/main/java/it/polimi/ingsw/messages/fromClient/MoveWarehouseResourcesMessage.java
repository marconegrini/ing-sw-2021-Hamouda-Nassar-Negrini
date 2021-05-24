package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.handlers.ClientHandler;

public class MoveWarehouseResourcesMessage extends ClientMessage{

    private Integer sourceStorage;
    private Integer destStorage;

    public MoveWarehouseResourcesMessage(Integer sourceStorage, Integer destStorage) {
        super(ClientMessageType.MOVEWAREHOUSERESOURCES);
        this.sourceStorage = sourceStorage;
        this.destStorage = destStorage;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {
        Player player = clientHandler.getPlayer();
        TurnManager turnManager = clientHandler.getTurnManager();
        ServerMessage outcome = turnManager.moveResourcesInWarehouse(clientHandler.getPlayer(), sourceStorage, destStorage);
        clientHandler.sendJson(outcome);
    }
}
