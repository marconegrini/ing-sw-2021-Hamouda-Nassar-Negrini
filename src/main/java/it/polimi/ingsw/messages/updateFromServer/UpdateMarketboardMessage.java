package it.polimi.ingsw.messages.updateFromServer;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.server.controller.TurnManager;

import java.io.DataOutputStream;
import java.util.List;

/**
 * The message is sent from the server to the client after a successful marble insertion in the market board.
 * It contains the new external marble color and the list of marbles that changed in the market (row or column)
 */
public class UpdateMarketboardMessage extends Message {

    private List<Marble> newMarbles;
    private Color externalMarble;

    public UpdateMarketboardMessage(String nickname, List<Marble> newMarbles, Color externalMarble){
        super(nickname, MessageType.UPDATEMARKETBOARD);
        this.newMarbles = newMarbles;
        this.externalMarble = externalMarble;
    }

    public Color getExternalMarble() {
        return externalMarble;
    }

    public List<Marble> getNewMarbles() {
        return newMarbles;
    }

    @Override
    public boolean serverProcess(Player player, TurnManager turnManager){

        ResourcesFromMarketMessage rfmm = new ResourcesFromMarketMessage(player.getNickname(), turnManager.getResourcesTakenFromMarket());
        rfmm.serverProcess(player, turnManager);

        return true;
    }

    @Override
    public boolean clientProcess(DataOutputStream getToServer) {
        return false;
    }


}
