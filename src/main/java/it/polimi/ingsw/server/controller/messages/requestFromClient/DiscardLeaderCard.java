package it.polimi.ingsw.server.controller.messages.requestFromClient;

import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.controller.messages.Message;
import it.polimi.ingsw.server.controller.messages.MessageType;
import it.polimi.ingsw.server.model.Player;

public class DiscardLeaderCard extends Message {

    public DiscardLeaderCard(String nickname){
        super(nickname, MessageType.DISCARDLEADERCARD);
    }
    @Override
    public boolean process(Player player, TurnManager turnManager) {
        return false;
    }
}
