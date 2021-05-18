package it.polimi.ingsw.messages.requestFromClient;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.model.Player;

import java.io.DataOutputStream;
import java.io.IOException;

public class ChooseLeaderCardMessage extends Message {

    private Integer index1;
    private Integer index2;

    public ChooseLeaderCardMessage(String nickname, Integer index1, Integer index2){
        super(nickname, MessageType.CHOOSELEADERCARD);
        this.index1 = index1;
        this.index2 = index2;
    }

    @Override
    public boolean serverProcess(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        Message outcome = turnManager.chooseLeaderCard(player, this.index1, this.index2);
        String messageToSend = gson.toJson(outcome);
        try {
            player.getToClient().writeUTF(messageToSend);
        } catch (IOException e){
            System.err.println("Exception occurred while sending json");
        }
        if(outcome.getMessageType().equals(MessageType.ERROR)) return false;
        return true;
    }

    @Override
    public boolean clientProcess(DataOutputStream dos){
        return false;
    }



}
