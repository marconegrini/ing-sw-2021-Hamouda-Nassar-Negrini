package it.polimi.ingsw.messages.requestFromClient;

import com.google.gson.Gson;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.Player;

import java.io.IOException;

/**
 * When invoking the process method of pickResourcesMessage the external marble is inserted into the market.
 * If everything worked fine, the process method sends to the client two messages:
 *  1- UpdateMarketMarbleMessage: it contains the new List of marbles and the new external marble
 *  2- ResourcesFromMarketMessage: the list of resources obtained from market's marbles conversion
 */
public class PickResourcesMessage extends Message {
    boolean isRow;
    int rowOrColNum;

    public PickResourcesMessage(String nickname, boolean isRow, int rowOrColNum){
        super(nickname, MessageType.PICKRESOURCES);
        this.isRow = isRow;
        this.rowOrColNum = rowOrColNum;
    }

    @Override
    public boolean serverProcess(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        Message outcome = turnManager.pickResources(player, this.isRow, this.rowOrColNum);
        String messageToSend = gson.toJson(outcome);
        try {
            player.getToClient().writeUTF(messageToSend);
        } catch (IOException e){
            System.err.println("Exception occurred while sending json");
        }
        if(outcome.getMessageType().equals(MessageType.ERROR)) return false;
        else {
            outcome.serverProcess(player, turnManager);
        }
        return false;
    }

    @Override
    public boolean clientProcess(){
        return false;
    }
}
