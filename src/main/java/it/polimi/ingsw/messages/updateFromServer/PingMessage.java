package it.polimi.ingsw.messages.updateFromServer;

import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
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
    public boolean serverProcess(Player player, TurnManager turnManager) {
        return false;
    }

    @Override
    public boolean clientProcess(){ return false; }
}
