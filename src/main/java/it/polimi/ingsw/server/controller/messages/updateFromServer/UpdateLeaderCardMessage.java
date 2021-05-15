package it.polimi.ingsw.server.controller.messages.updateFromServer;

import com.google.gson.Gson;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.controller.messages.Message;
import it.polimi.ingsw.server.controller.messages.MessageType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.io.IOException;
import java.util.List;

public class UpdateLeaderCardMessage extends Message {

    public UpdateLeaderCardMessage(String nickname){
        super(nickname, MessageType.UPDATELEADERCARD);
    }

    @Override
    public boolean process(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        List<LeaderCard> leaderCards = player.getPersonalBoard().getLeaderCards();
        String messageToSend = gson.toJson(leaderCards);
        try {
            player.getToClient().writeUTF(messageToSend);
        } catch (IOException e){
            System.err.println("Exception occurred while sending json");
        }
        return true;
    }
}
