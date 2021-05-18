package it.polimi.ingsw.server.handlers;

import it.polimi.ingsw.model.exceptions.MaxPlayersException;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.TemporaryPlayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

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
        //AtomicBoolean stillAlive = new AtomicBoolean(true);


        Runnable runnable = () -> {
            long oldTimeMillis = System.currentTimeMillis();
            String pingMessage = "";
            while (true) {
                try {
                    if ((System.currentTimeMillis() - oldTimeMillis) >= (10 * 1000)) {
                        toClient.writeUTF("PING");
                        System.out.println("Pingato");
                        pingMessage = fromClient.readUTF().toUpperCase();
                        //if (!pingMessage.equalsIgnoreCase("PING"))  throw new SocketTimeoutException();
                        oldTimeMillis = System.currentTimeMillis();
                        //throw new InterruptedIOException();
                    }
                } catch (SocketTimeoutException e){
                    //e.printStackTrace();
                    System.out.println("Inside SocketTimeoutException");
                    if (this.temporaryPlayer != null) {
                        Server.remove(this.temporaryPlayer);
                        System.out.println("Removed: " + this.temporaryPlayer.getNickname());
                    }
                    //stillAlive.set(false);
                    try {
                        clientSocket.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    break;
                } catch (SocketException e){
                    e.printStackTrace();
                } catch (InterruptedIOException e){
                    e.printStackTrace();
                    System.out.println("Inside InterruptedException");
                    if (this.temporaryPlayer != null) {
                        Server.remove(this.temporaryPlayer);
                        System.out.println("Removed: " + this.temporaryPlayer.getNickname());
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        };

        Thread ping = new Thread(runnable);
        ping.start();

        try {
            toClient.writeUTF("NICKNAME");

            while (true) {
                while (!(fromClient.available() > 0)) ;
                nickname = fromClient.readUTF();
                if (nickname.isEmpty() || Server.nicknameAlreadyExist(nickname))
                    toClient.writeUTF("KO");
                else {
                    toClient.writeUTF("OK");
                    break;
                }
            }

            toClient.writeUTF("MULTIPLAYER");
            while (!(fromClient.available() > 0)) ;
            multiplayer = fromClient.readUTF();

            if (multiplayer.equalsIgnoreCase("YES")) {
                temporaryPlayer = new TemporaryPlayer(this.clientSocket, nickname, false);
                Server.add(temporaryPlayer);
                System.out.println("Added to players list " + nickname);
                Server.updateUsers();

                while (true) {
                    if (fromClient.available() > 0) {
                        String command = fromClient.readUTF().toUpperCase();

                        if (command.equals("START") && Server.isLeader(this.temporaryPlayer)) {
                            if (Server.size() > 1) {
                                Server.startMultiplayerGame(Server.size());
                                break;
                            }
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
                temporaryPlayer = new TemporaryPlayer(this.clientSocket, nickname, false);
                Server.startSinglePlayergame(temporaryPlayer);
            }
        } catch (SocketException e){
            System.out.println("Socket closed");
        } catch (InterruptedIOException e){
            e.printStackTrace();
            //Server.remove(this.temporaryPlayer);
        } catch (IOException | MaxPlayersException e) {
            e.printStackTrace();
        }
    }
}
