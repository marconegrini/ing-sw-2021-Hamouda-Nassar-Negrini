package it.polimi.ingsw.messages.requestFromClient;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.model.Player;

import java.io.IOException;

public class BuyDevelopmentCardMessage extends Message {
    int row;
    int column;
    int devCardSlot;
    Integer selectedLeaderCard;


    public BuyDevelopmentCardMessage(String nickname, int row, int column, int devCardSlot, Integer selectedLeaderCard){
        super(nickname, MessageType.BUYDEVELOPMENTCARD);
        this.row = row;
        this.column = column;
        this.devCardSlot = devCardSlot;
        this.selectedLeaderCard = selectedLeaderCard;
    }

    @Override
    public boolean process(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        Message outcome = turnManager.buyDevelopmentCard(player, this.row, this.column, this.devCardSlot, this.selectedLeaderCard);
        String messageToSend = gson.toJson(outcome);
        try {
            player.getToClient().writeUTF(messageToSend);
        } catch (IOException e){
            System.err.println("Exception occurred while sending json");
        }
        if(outcome.getMessageType().equals(MessageType.ERROR)) return false;
        return true;    }
}