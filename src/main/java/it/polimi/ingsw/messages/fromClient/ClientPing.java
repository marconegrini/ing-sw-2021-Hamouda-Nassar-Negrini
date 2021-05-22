package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerPing;
import it.polimi.ingsw.server.handlers.ClientHandler;

public class ClientPing extends ClientMessage{

    public ClientPing(){
        super(ClientMessageType.PING);
    }

    @Override
    public void serverProcess(ClientHandler clientHandler){
        System.out.println("PING from client");
        ServerMessage ping = new ServerPing();
        clientHandler.sendJson(ping);
    }
}
