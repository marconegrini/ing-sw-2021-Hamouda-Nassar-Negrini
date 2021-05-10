package it.polimi.ingsw.server.controller.messages;

import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.multiplayer.MultiPlayer;

public class PickResourcesMessage extends Message{

    private boolean isRow;
    private int rowOrColNum;
    private TurnManager turnManager;

    public PickResourcesMessage(String nickname ,boolean isRow, int rowOrColNum){
        super(nickname, MessageType.PICKRESOURCES);
        this.isRow = isRow;
        this.rowOrColNum = rowOrColNum;
    }

    @Override
    public void process(Player player, TurnManager turnManager) {
        //TODO Chiamare il metodo che mi ritorna l'istanza di player dal nickname

        //Player player = new MultiPlayer();
        //turnManager.pickResources (player,  this.isRow, this.rowOrColNum);
        System.out.println("Dentro process");
    }

}
