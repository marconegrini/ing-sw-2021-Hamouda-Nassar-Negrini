package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.handlers.ClientHandler;

/**
 * Message sent from the user to select two leader cards out of four at the beginning of the game.
 * Contains leader cards' arraylist's indexes of specified leader cards.
 */
public class SelectLeaderCardMessage extends ClientMessage{

    private Integer index1;
    private Integer index2;

    public SelectLeaderCardMessage(Integer index1, Integer index2){
        super(ClientMessageType.SELECTLEADERCARDS);
        this.index1 = index1;
        this.index2 = index2;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {
        Player player = clientHandler.getPlayer();
        TurnManager turnManager = clientHandler.getTurnManager();
        ServerMessage outcome = turnManager.selectLeaderCard(clientHandler.getPlayer(), index1, index2);
        clientHandler.sendJson(outcome);
        clientHandler.getTurnManager().clientDone();
    }
}
