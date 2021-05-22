package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.server.handlers.ClientHandler;

public class LoginMessage extends ClientMessage{
    private String nickname;
    private boolean isMultiplayer;

    public LoginMessage(String nickname, boolean isMultiplayer){
        super(ClientMessageType.LOGIN);
        this.nickname = nickname;
        this.isMultiplayer = isMultiplayer;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler){

    }
}
