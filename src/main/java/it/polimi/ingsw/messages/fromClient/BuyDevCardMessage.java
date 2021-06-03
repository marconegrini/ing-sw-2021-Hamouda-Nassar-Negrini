package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.handlers.ClientHandler;

import java.util.ArrayList;
import java.util.EmptyStackException;
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

        //TESTING
        List<Resource> resourcesIn = new ArrayList<>();
        for (int i=0;i<8;i++)
            resourcesIn.add(Resource.SHIELD);
        for (int i=0;i<8;i++)
            resourcesIn.add(Resource.COIN);
        for (int i=0;i<8;i++)
            resourcesIn.add(Resource.SERVANT);
        for (int i=0;i<8;i++)
            resourcesIn.add(Resource.STONE);

        player.putCofferResources(resourcesIn);
//        //

        try {
            ServerMessage outcome = turnManager.buyDevelopmentCard(clientHandler.getPlayer(), row, column, devCardSlot);
            clientHandler.sendJson(outcome);   //response message to client {OkMessage, BuyDevCardMessage}
        }catch(EmptyStackException e){
            e.printStackTrace();
        }

    }
}
