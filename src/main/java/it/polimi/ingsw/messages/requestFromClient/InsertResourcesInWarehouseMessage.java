package it.polimi.ingsw.messages.requestFromClient;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.Resource;

import java.io.IOException;
import java.util.List;

public class InsertResourcesInWarehouseMessage extends Message {

    List<Resource> resources;
    Integer shelf;

    public InsertResourcesInWarehouseMessage(String nickname, List<Resource> resources, Integer shelf ){
        super(nickname, MessageType.INSERTRESOURCESINWAREHOUSE);
        this.resources = resources;
        this.shelf = shelf;
    }

    @Override
    public boolean process(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        Message outcome = turnManager.insertResourcesInWarehouse(player, shelf, resources);
        String messageToSend = gson.toJson(outcome);
        try {
            player.getToClient().writeUTF(messageToSend);
        } catch (IOException e){
            System.err.println("Exception occurred while sending json");
        }
        if(outcome.getMessageType().equals(MessageType.ERROR)) return false;
        return true;    }
}
