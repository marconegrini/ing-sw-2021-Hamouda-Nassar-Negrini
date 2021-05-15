package it.polimi.ingsw.server.controller.messages.updateFromServer;

import com.google.gson.Gson;
import it.polimi.ingsw.client.ClientModel.enumerations.Resource;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.controller.messages.Message;
import it.polimi.ingsw.server.controller.messages.MessageType;
import it.polimi.ingsw.server.model.Player;

import java.util.ArrayList;
import java.util.List;

public class ResourcesFromMarketMessage extends Message {

    private List<Resource> resourcesToStore;

    public ResourcesFromMarketMessage(String nickname, List<Resource> resourcesToStore){
        super(nickname, MessageType.RESOURCESFROMMARKET);
        this.resourcesToStore = new ArrayList<>();
        this.resourcesToStore = resourcesToStore;

    }
    @Override
    public boolean process(Player player, TurnManager turnManager) {
        Gson gson = new Gson();


        return false;
    }
}
