package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.handlers.ClientHandler;

import java.util.ArrayList;
import java.util.List;


public class BuyDevCardMessage extends ClientMessage{

    private final Integer row;
    private final Integer column;
    private final Integer devCardSlot;

    public BuyDevCardMessage(Integer row, Integer column, Integer devCardSlot ) {
        super(ClientMessageType.BUYDEVELOPMENTCARD);
        this.row = row;
        this.column = column;
        this.devCardSlot = devCardSlot;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {
        Player player = clientHandler.getPlayer();
        TurnManager turnManager = clientHandler.getTurnManager();


        ServerMessage outcome = turnManager.buyDevelopmentCard(clientHandler.getPlayer(), row, column, devCardSlot);
        clientHandler.sendJson(outcome);


    }
}
