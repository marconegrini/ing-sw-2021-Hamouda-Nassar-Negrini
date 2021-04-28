package it.polimi.ingsw.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Locale;

public class ClientHandler extends Thread{

    private final Socket clientSocket;
    private final DataInputStream fromClient;
    private final DataOutputStream toClient;

    public ClientHandler(Socket clientSocket, DataInputStream fromClient, DataOutputStream toClient) {
        this.clientSocket = clientSocket;
        this.fromClient = fromClient;
        this.toClient = toClient;
    }


    public void run(){

        String multiplayer;

        String nickname;

        try {

            toClient.writeUTF("Type your nickname: ");
            nickname = fromClient.readUTF();

            toClient.writeUTF("Start a multiplayer game? yes/no");
            multiplayer = fromClient.readUTF();

            if(multiplayer.toUpperCase().equals("YES")){

                toClient.writeUTF("Added to players list");
                Server.add(new TemporaryPlayer(this.clientSocket, nickname));
                Server.updateUsers();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
