package it.polimi.ingsw.server.controller.messages.requestFromClient;

import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.controller.messages.Message;
import it.polimi.ingsw.server.controller.messages.MessageType;
import it.polimi.ingsw.server.model.Player;

import java.util.List;

public class PickResourcesMessage extends Message {
    boolean isRow;
    int rowOrColNum;
    Integer selectedLeaderCard;

    public PickResourcesMessage(String nickname, boolean isRow, int rowOrColNum, Integer selectedLeaderCard){
        super(nickname, MessageType.PICKRESOURCES);
        this.isRow = isRow;
        this.rowOrColNum = rowOrColNum;
        this.selectedLeaderCard = selectedLeaderCard;
    }

    @Override
    public boolean process(Player player, TurnManager turnManager) {
        return false;
    }
}
