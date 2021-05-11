package it.polimi.ingsw.server.controller.messages.requestFromClient;

import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.controller.messages.Message;
import it.polimi.ingsw.server.controller.messages.MessageType;
import it.polimi.ingsw.server.model.Player;

public class ActivateLeaderCard extends Message {

    public ActivateLeaderCard(String nickname){
        super(nickname, MessageType.ACTIVATELEADERCARD);
    }
    @Override
    public boolean process(Player player, TurnManager turnManager) {
        return false;
    }
}
