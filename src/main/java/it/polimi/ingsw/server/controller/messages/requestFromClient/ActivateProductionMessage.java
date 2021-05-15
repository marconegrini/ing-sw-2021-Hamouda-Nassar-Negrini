package it.polimi.ingsw.server.controller.messages.requestFromClient;

import com.google.gson.Gson;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.controller.messages.Message;
import it.polimi.ingsw.server.controller.messages.MessageType;
import it.polimi.ingsw.model.Player;

import java.io.IOException;
import java.util.List;

public class ActivateProductionMessage extends Message {

    List<Integer> slots;
    Integer selectedLeaderCard;

    public ActivateProductionMessage(String nickname, List<Integer> slots, Integer selectedLeaderCard){
        super(nickname, MessageType.ACTIVATEPRODUCTION);
        this.slots = slots;
        this.selectedLeaderCard = selectedLeaderCard;
    }

    @Override
    public boolean process(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        Message outcome = turnManager.activateProduction(player, this.slots,this.selectedLeaderCard);
        String messageToSend = gson.toJson(outcome);
        try {
            player.getToClient().writeUTF(messageToSend);
        } catch (IOException e){
            System.err.println("Exception occurred while sending json");
        }
        if(outcome.getMessageType().equals(MessageType.ERROR)) return false;
        return true;    }
}
