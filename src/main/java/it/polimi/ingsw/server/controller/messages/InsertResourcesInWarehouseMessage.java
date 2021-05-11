package it.polimi.ingsw.server.controller.messages;

import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.List;

public class InsertResourcesInWarehouseMessage extends Message{

    List<Resource> resources;
    Integer shelf;

    public InsertResourcesInWarehouseMessage(String nickname, List<Resource> resources, Integer shelf ){
        super(nickname, MessageType.INSERTRESOURCESINWAREHOUSE);
        this.resources = resources;
        this.shelf = shelf;
,
    }


    @Override
    public void process(Player player, TurnManager turnManager) {

    }
}
