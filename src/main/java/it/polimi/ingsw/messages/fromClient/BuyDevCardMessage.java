package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.handlers.ClientHandler;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

/**
 * Sent from the client to buy specified development card. Contains the row and column numbers of the development
 * cards matrix. Specifies moreover the number of slot in which to insert bought development card.
 */
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
    if (player.getClonedCoffer().keySet().stream().anyMatch(x->player.getClonedCoffer().get(x)<20))
        {
            List<Resource> resourcesIn = new ArrayList<>();
            for (int i = 0; i < 8; i++)
                resourcesIn.add(Resource.SHIELD);
            for (int i = 0; i < 8; i++)
                resourcesIn.add(Resource.COIN);
            for (int i = 0; i < 8; i++)
                resourcesIn.add(Resource.SERVANT);
            for (int i = 0; i < 8; i++)
                resourcesIn.add(Resource.STONE);

            player.putCofferResources(resourcesIn);
        }


        //for testing   -- full all leader cards storage
//        if (player.getLeaderCards().get(0).isActivated()&&player.getLeaderCards().get(0).getCardType().equals(CardType.STORAGE)){
//           StorageLeaderCard sld = (StorageLeaderCard)player.getLeaderCards().get(0);
//            for (int i=sld.getOccupiedSlots(); i< sld.getMaxCapacity(); i++){
//                try {
//                    sld.putResourceInCardStorage(null,sld.storageType());
//                } catch (IllegalInsertionException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        //

        try {
            ServerMessage outcome = turnManager.buyDevelopmentCard(clientHandler.getPlayer(), row, column, devCardSlot);
            clientHandler.sendJson(outcome);   //response message to client {OkMessage, BuyDevCardMessage}
        }catch(EmptyStackException e){
            e.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
