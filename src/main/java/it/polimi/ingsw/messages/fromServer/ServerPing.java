package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromClient.ClientPing;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.controller.TurnManager;

import java.io.DataOutputStream;

public class ServerPing extends ServerMessage {

    public ServerPing() {
        super(ServerMessageType.PING);
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        System.out.println("PING from server");
        ClientMessage ping = new ClientPing();
        try {
            synchronized (this) {
                this.wait(3000);
                serverHandler.sendJson(ping);
            }
        } catch (InterruptedException e) {}
    }
}

