package it.polimi.ingsw.messages.requestFromClient;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.server.controller.TurnManager;

import java.io.IOException;

public class ActivatePersonalProductionMessage extends Message {
    Resource prodIn1;
    Resource prodIn2;
    Resource prodOut;
    Resource leaderResource;

    public ActivatePersonalProductionMessage(String nickname, Resource prodIn1, Resource prodIn2, Resource prodOut, Resource leaderResource){
        super(nickname, MessageType.ACTIVATEPERSONALPRODUCTION);
        this.prodIn1 = prodIn1;
        this.prodIn2 = prodIn2;
        this.prodOut = prodOut;
        this.leaderResource = leaderResource;
    }

    @Override
    public boolean serverProcess(Player player, TurnManager turnManager){
        Gson gson = new Gson();
        Message toSend = turnManager.activatePersonalProduction(player, prodIn1, prodIn2, prodOut, leaderResource);
        String messageToSend = gson.toJson(toSend);
        try {
            player.getToClient().writeUTF(messageToSend);
        } catch (IOException e){
            System.err.println("Exception occurred while sending json");
        }
        if(toSend.getMessageType().equals(MessageType.ERROR)) return false;
        return true;
    }

    @Override
    public boolean clientProcess(){
        return false;
    }
}
