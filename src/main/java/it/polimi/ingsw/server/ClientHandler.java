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
            while (!(fromClient.available() >0)); nickname = fromClient.readUTF();

            toClient.writeUTF("Start a multiplayer game? [yes/no]");
            while (!(fromClient.available() >0)); multiplayer = fromClient.readUTF();

            if(multiplayer.toUpperCase().equals("YES")){

                temporaryPlayer = new TemporaryPlayer(this.clientSocket, nickname);
                Server.add(temporaryPlayer);
                System.out.println("Added to players list "+ nickname);
                toClient.writeUTF("Added to players list.\nType 'EXIT' to leave the game");
                Server.updateUsers();
            }

            while (true){
                if (fromClient.available() >0) {
                    if (fromClient.readUTF().toUpperCase().equals("EXIT")) {
                        System.out.println(this.temporaryPlayer.getNickname() + " exit from game");
                        toClient.writeUTF("EXIT");
                        Server.remove(this.temporaryPlayer);
                        Server.updateUsers();
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
