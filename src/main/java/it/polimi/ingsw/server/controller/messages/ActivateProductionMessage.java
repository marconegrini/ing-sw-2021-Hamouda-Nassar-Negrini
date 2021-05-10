package it.polimi.ingsw.server.controller.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.model.Player;

import java.io.IOException;
import java.util.List;

public class ActivateProductionMessage extends Message{

    List<Integer> slots;

    public ActivateProductionMessage(String nickname, List<Integer> slots){
        super(nickname, MessageType.ACTIVATEPRODUCTION);
        this.slots = slots;
    }

    @Override
    public void process(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        Message outcome = turnManager.activateProduction(player, this.slots);
        String messageToSend = gson.toJson(outcome);
        try {
            player.getToClient().writeUTF(messageToSend);
        } catch (IOException e){
            System.err.println("Exception occurred while sending json");
        }
    }
}
