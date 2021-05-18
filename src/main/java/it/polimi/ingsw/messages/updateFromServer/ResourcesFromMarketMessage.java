package it.polimi.ingsw.messages.updateFromServer;

import com.google.gson.Gson;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.controller.TurnManager;

import java.io.IOException;
import java.util.List;

/**
 * The message is sent inside the process method of UpdateMarketMarbles. It contains the
 * returned resources from the market
 */
public class ResourcesFromMarketMessage extends Message {

    List<Resource> resourcesToStore;

    public ResourcesFromMarketMessage(String nickname, List<Resource> resourcesToStore){
        super(nickname, MessageType.RESOURCESFROMMARKET);
        this.resourcesToStore = resourcesToStore;
    }

    public List<Resource> getResourcesToStore() {
        return resourcesToStore;
    }

    @Override
    public boolean serverProcess(Player player, TurnManager turnManager){
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
    public boolean clientProcess(){
        return false;
    }
}
