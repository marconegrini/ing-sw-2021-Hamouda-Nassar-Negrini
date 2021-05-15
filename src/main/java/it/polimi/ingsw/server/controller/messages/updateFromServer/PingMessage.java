package it.polimi.ingsw.server.controller.messages.updateFromServer;

import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.server.controller.messages.Message;
import it.polimi.ingsw.server.controller.messages.MessageType;
import it.polimi.ingsw.model.Player;

public class PingMessage extends Message {

    public PingMessage(String nickname){
        super(nickname, MessageType.PING);
    }

    @Override
    public String toString(){
        return "PingMessage{" +
                "nickname = " + getNickname() +
                '}';
    }

    @Override
    public boolean process(Player player, TurnManager turnManager) {
        return true;
    }
}
