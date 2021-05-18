package it.polimi.ingsw.messages.updateFromServer;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class UpdateLeaderCardMessage extends Message {

    List<LeaderCard> leaderCards;

    public UpdateLeaderCardMessage(String nickname, List<LeaderCard> leaderCards){
        super(nickname, MessageType.UPDATELEADERCARD);
        this.leaderCards = leaderCards;
    }

    @Override
    public boolean serverProcess(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        String messageToSend = gson.toJson(this);
        try {
            player.getToClient().writeUTF(messageToSend);
        } catch (IOException e){
            System.err.println("Exception occurred while sending json");
        }
        return true;
    }

    @Override
    public boolean clientProcess(DataOutputStream dos){
        return false;
    }
}
