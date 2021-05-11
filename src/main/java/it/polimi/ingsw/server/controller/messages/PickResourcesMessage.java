package it.polimi.ingsw.server.controller.messages;

import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.multiplayer.MultiPlayer;

public class PickResourcesMessage extends Message{

    private boolean isRow;
    private int rowOrColNum;

    public PickResourcesMessage(String nickname ,boolean isRow, int rowOrColNum){
        super(nickname, MessageType.PICKRESOURCES);
        this.isRow = isRow;
        this.rowOrColNum = rowOrColNum;
    }


    @Override
    public boolean process(Player player, TurnManager turnManager) {

        //Message outcome = turnManager.pickResources (player,  this.isRow, this.rowOrColNum);


        return true;
    }

}
