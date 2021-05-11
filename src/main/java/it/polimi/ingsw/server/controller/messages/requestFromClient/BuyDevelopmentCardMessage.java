package it.polimi.ingsw.server.controller.messages.requestFromClient;

import com.google.gson.Gson;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.controller.messages.Message;
import it.polimi.ingsw.server.controller.messages.MessageType;
import it.polimi.ingsw.server.model.Player;

import java.io.IOException;

public class BuyDevelopmentCardMessage extends Message {
    int row;
    int column;
    int devCardSlot;

    public BuyDevelopmentCardMessage(String nickname, int row, int column, int devCardSlot){
        super(nickname, MessageType.BUYDEVELOPMENTCARD);
        this.row = row;
        this.column = column;
        this.devCardSlot = devCardSlot;
    }

    @Override
    public boolean process(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        Message outcome = turnManager.buyDevelopmentCard(player, this.row, this.column, this.devCardSlot);
        if(outcome.getMessageType().equals(MessageType.ERROR)) return false;
        String messageToSend = gson.toJson(outcome);
        try {
            player.getToClient().writeUTF(messageToSend);
        } catch (IOException e){
            System.err.println("Exception occurred while sending json");
        }
        return true;
    }
}
