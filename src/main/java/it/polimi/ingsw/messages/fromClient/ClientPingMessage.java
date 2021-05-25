package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerPingMessage;
import it.polimi.ingsw.server.handlers.ClientHandler;

public class ClientPingMessage extends ClientMessage{

    public ClientPingMessage(){
        super(ClientMessageType.PING);
    }

    @Override
    public void serverProcess(ClientHandler clientHandler){
        System.out.println("PING from client");
        ServerMessage ping = new ServerPingMessage();
        clientHandler.sendJson(ping);
    }
}
