package it.polimi.ingsw.messages.requestFromClient;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.model.Player;

import java.io.DataOutputStream;
import java.io.IOException;

public class MoveWarehouseResources extends Message {
    Integer sourceStorage;
    Integer destStorage;

    public MoveWarehouseResources(String nickname, Integer sourceStorage, Integer destStorage){
        super(nickname, MessageType.MOVEWAREHOUSERESOURCES);
        this.sourceStorage = sourceStorage;
        this.destStorage = destStorage;
    }

    @Override
    public boolean serverProcess(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        Message outcome = turnManager.moveResourcesInWarehouse(player, this.sourceStorage, this.destStorage);
        String messageToSend = gson.toJson(outcome);
        try {
            player.getToClient().writeUTF(messageToSend);
        } catch (IOException e){
            System.err.println("Exception occurred while sending json");
        }
        if(outcome.getMessageType().equals(MessageType.ERROR)) return false;
        return true;
    }

    @Override
    public boolean clientProcess(DataOutputStream getToServer) {
        return false;
    }

}
