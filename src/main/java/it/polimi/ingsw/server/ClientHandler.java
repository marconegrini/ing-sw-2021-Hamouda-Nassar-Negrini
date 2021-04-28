package it.polimi.ingsw.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread{

    private final Socket clientSocket;
    private final DataInputStream fromClient;
    private final DataOutputStream toClient;
    private TemporaryPlayer temporaryPlayer;

    public ClientHandler(Socket clientSocket, DataInputStream fromClient, DataOutputStream toClient) {
        this.clientSocket = clientSocket;
        this.fromClient = fromClient;
        this.toClient = toClient;
        this.temporaryPlayer = null;
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

                toClient.writeUTF("Added to players list.\nType 'EXIT' to leave the game");
                temporaryPlayer = new TemporaryPlayer(this.clientSocket, nickname);
                Server.add(temporaryPlayer);
                Server.updateUsers();
            }

            while (true){
                if (fromClient.readUTF().toUpperCase().equals("EXIT")){
                    Server.remove(this.temporaryPlayer);
                    Server.updateUsers();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
