package it.polimi.ingsw.messages.requestFromClient;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.model.Player;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class ActivateProductionMessage extends Message {

    private List<Integer> slots;
    private List<Resource> leaderResource;

    public ActivateProductionMessage(String nickname, List<Integer> slots, List<Resource> leaderResource){
        super(nickname, MessageType.ACTIVATEPRODUCTION);
        this.slots = slots;
        this.leaderResource = leaderResource;
    }

    @Override
    public boolean serverProcess(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        Message outcome = turnManager.activateProduction(player, this.slots,this.leaderResource);
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
