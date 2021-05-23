package it.polimi.ingsw.messages.requestFromClient;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.model.Player;

import java.io.DataOutputStream;
import java.io.IOException;

public class ActivateLeaderCardMessage extends Message {

    private Integer indexNumber;

    public ActivateLeaderCardMessage(String nickname, Integer indexNumber) {
        super(nickname, MessageType.ACTIVATELEADERCARD);
        this.indexNumber = indexNumber;
    }

    @Override
    public boolean serverProcess(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        Message outcome = turnManager.activateLeaderCard(player, indexNumber);
        String messageToSend = gson.toJson(outcome);
        try {
            player.getToClient().writeUTF(messageToSend);
        } catch (IOException e) {
            System.err.println("Exception occurred while sending json");
        }
        if (outcome.getMessageType().equals(MessageType.ERROR)) return false;
        return true;
    }

    @Override
    public boolean clientProcess(DataOutputStream getToServer) {
        Gson gson = new Gson();
        try {
            getToServer.writeUTF(gson.toJson(this));
        } catch (IOException e) {
            System.err.println("Exception occurred while sending json");
            return false;
        }

        return true;
    }

}
