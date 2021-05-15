package it.polimi.ingsw.server.controller.messages.requestFromClient;

import com.google.gson.Gson;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.controller.messages.Message;
import it.polimi.ingsw.server.controller.messages.MessageType;
import it.polimi.ingsw.model.Player;

import java.io.IOException;

public class DiscardLeaderCard extends Message {
    private Integer indexNumber;

    public DiscardLeaderCard(String nickname, Integer indexNumber){
        super(nickname, MessageType.DISCARDLEADERCARD);
        this.indexNumber = indexNumber;
    }
    @Override
    public boolean process(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        Message outcome = turnManager.discardLeaderCard(player, indexNumber);
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
