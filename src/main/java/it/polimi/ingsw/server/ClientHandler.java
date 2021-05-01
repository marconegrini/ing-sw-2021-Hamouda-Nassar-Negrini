package it.polimi.ingsw.server;

import it.polimi.ingsw.model.exceptions.MaxPlayersException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Locale;

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

        String nickname=null;

        boolean nicknameAlreadyExists = true;

        try {

            toClient.writeUTF("Type your nickname: ");

            while (true) {

                while (!(fromClient.available() > 0)) ;
                nickname = fromClient.readUTF();

                if (nickname.isEmpty() || Server.nicknameAlreadyExist(nickname))
                    toClient.writeUTF("KO");
                else {
                    toClient.writeUTF("OK");
                    break;
                }
                ;
            }

            toClient.writeUTF("Start a multiplayer game? [yes/no]");

            while (true) {
                while (!(fromClient.available() > 0)) ;
                multiplayer = fromClient.readUTF();
                if ((multiplayer.isEmpty() || !multiplayer.toUpperCase().equals("YES"))
                        && (multiplayer.isEmpty() || !multiplayer.toUpperCase().equals("NO")))
                    toClient.writeUTF("KO");
                else {
                    toClient.writeUTF("OK");
                    break;
                }
                ;
            }

            if (multiplayer.toUpperCase().equals("YES")) {

                temporaryPlayer = new TemporaryPlayer(this.clientSocket, nickname, false);
                Server.add(temporaryPlayer);
                System.out.println("Added to players list " + nickname);
                toClient.writeUTF("Added to players list.\nType 'EXIT' to leave the game");
                Server.updateUsers();


                while (true) {
                    if (fromClient.available() > 0) {
                        String command = fromClient.readUTF().toUpperCase();
                        if (command.equals("START") && Server.isLeader(this.temporaryPlayer) && (Server.size() > 1)) {
                            Server.startMultiplayerGame(Server.size());
                            break;
                        }
                        if (command.equals("EXIT")) {
                            System.out.println(this.temporaryPlayer.getNickname() + " exit from game");
                            toClient.writeUTF("EXIT");
                            Server.remove(this.temporaryPlayer);
                            Server.updateUsers();
                            break;
                        }
                    }
                }

            } else {

            }

        } catch (IOException | MaxPlayersException e) {
            e.printStackTrace();
        }
    }
}
