package it.polimi.ingsw.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TemporaryPlayer {

    private Socket socket;
    private String nickname;
    private Integer userId;
    private boolean firstPlayer;

    public TemporaryPlayer(Socket socket, String nickname, boolean firstPlayer){
        this.socket = socket;
        this.nickname= nickname;
        this.firstPlayer = firstPlayer;
    }

    public String getNickname() {
        return nickname;
    }

    public DataInputStream getDataInputStream() throws IOException {
        return new DataInputStream(socket.getInputStream());
    }

    public DataOutputStream getDataOutputStream() throws IOException {
        return new DataOutputStream(socket.getOutputStream());
    }

    public void setFirstPlayer(){
        this.firstPlayer = true;
    }
}