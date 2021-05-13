package it.polimi.ingsw.server.controller.messages.requestFromClient;

import com.google.gson.Gson;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.controller.messages.Message;
import it.polimi.ingsw.server.controller.messages.MessageType;
import it.polimi.ingsw.server.model.Player;

import java.io.IOException;
import java.util.Set;

public class ActivateLeaderCardMessage extends Message {
    private Integer indexNumber;

    public ActivateLeaderCardMessage(String nickname, Integer indexNumber){
        super(nickname, MessageType.ACTIVATELEADERCARD);
        this.indexNumber = indexNumber;
    }

    @Override
    public boolean process(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        Message outcome = turnManager.activateLeaderCard(player, indexNumber);
        String messageToSend = gson.toJson(outcome);
        try {
            player.getToClient().writeUTF(messageToSend);
        } catch (IOException e){
            System.err.println("Exception occurred while sending json");
        }
        if(outcome.getMessageType().equals(MessageType.ERROR)) return false;
        return true;
    }
}
