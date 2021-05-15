package it.polimi.ingsw.messages.updateFromServer;

import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.controller.TurnManager;

import java.util.List;

public class ResourcesFromMarketMessage extends Message {
    List<Resource> resourcesToStore;

    public ResourcesFromMarketMessage(String nickname, List<Resource> resourcesToStore){
        super(nickname, MessageType.RESOURCESFROMMARKET);
        this.resourcesToStore = resourcesToStore;
    }

    @Override
    public boolean process(Player player, TurnManager turnManager){
        return false;
    }
}
