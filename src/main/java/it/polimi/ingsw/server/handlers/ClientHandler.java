package it.polimi.ingsw.server.handlers;

import it.polimi.ingsw.model.exceptions.MaxPlayersException;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.TemporaryPlayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ClientHandler extends Thread {

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

        AtomicLong pingTime = new AtomicLong();
        AtomicLong receivedPingTime = new AtomicLong();
        String message = "";
        String nickname = "";
        AtomicBoolean stillAlive = new AtomicBoolean(true);
        AtomicBoolean continueHandling = new AtomicBoolean(true);
        AtomicBoolean pingReceived = new AtomicBoolean(true);

        boolean obtainedNickname = false;
        boolean obtainedMultiplayer = false;


        pingTime.set(System.currentTimeMillis());
        receivedPingTime.set(System.currentTimeMillis());

        //System.out.println("Long ping time: " + pingTime + " System Time: " + System.currentTimeMillis());

        Runnable runnablePing = () -> {

            while (stillAlive.get()){
                try{

                    //System.out.println("oldTime: "+ (pingTime/1000) + " Current time: " + (System.currentTimeMillis()/1000));
                    if (pingReceived.get() && ((System.currentTimeMillis() - pingTime.get()) >= 5*1000)){
                        toClient.writeUTF("PING");
                        System.out.println("Pinged");
                        pingReceived.set(false);
                        //System.out.println("Ping sent with oldTime: "+ pingTime + " Current time: " + System.currentTimeMillis() + " Difference: " + (System.currentTimeMillis() - pingTime) );
                        pingTime.set(System.currentTimeMillis());
                    }

                    if ( (System.currentTimeMillis() - pingTime.get()) > 10*1000){
                        pingReceived.set(false);
                        stillAlive.set(false);
                        System.out.println("Connection with the client lost");

                        if (this.temporaryPlayer != null) {
                            Server.remove(this.temporaryPlayer);
                            System.out.println("Removed: " + this.temporaryPlayer.getNickname());
                        }
                        try {
                            clientSocket.close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                            continueHandling.set(false);
                            stillAlive.set(false);
                        }

                    }

                } catch (IOException e){
                    e.printStackTrace();
                    continueHandling.set(false);
                    stillAlive.set(false);
                    try {
                        clientSocket.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        System.out.println("Socket closed");
                        continueHandling.set(false);
                    }
                }
            }
        };

        Thread ping = new Thread(runnablePing);
        ping.start();


        //Message to start the communication with the server
        try {
            toClient.writeUTF("NICKNAME");
        } catch (IOException e) {
            e.printStackTrace();
            continueHandling.set(false);
        }

        while (continueHandling.get()){

            while (true) {
                try {
                    if (fromClient.available() > 0) break;
                } catch (IOException e) {
                    System.out.println("Cant reach the host");
                    //e.printStackTrace();
                    continueHandling.set(false);
                    break;
                }
            }

            //obtaining the message from the server
            try {
                message = fromClient.readUTF();
                //System.out.println("Message received from the client: "+ message);
            } catch (IOException e) {
                System.out.println("Can't receive messages from the host");
                continueHandling.set(false);
                //e.printStackTrace();
            }

            //Handle the ping message received from the client
            if (message.toUpperCase().contains("PING")){
                pingTime.set(System.currentTimeMillis());
                pingReceived.set(true);
                if (!nickname.equals(""))   System.out.println("Received ping from " + nickname);
                else System.out.println("Received ping");
                if (message.toUpperCase().contains("KO"))   stillAlive.set(false);

                //System.out.println("Time of receiving ping: " + receivedPingTime.get());
                //System.out.println("Ping received from the client after: " + (pingTime.get() - receivedPingTime.get()));
            }

            if (message.toUpperCase().contains("NICKNAME")){
                //nickname = message.substring(message.toUpperCase().lastIndexOf("NICKNAME"));
                nickname = message.substring(8);
                //System.out.println("last index: "+ message.toUpperCase().lastIndexOf("NICKNAME") +"Nickname: " + nickname);
                try {
                    if (nickname.isEmpty() || Server.nicknameAlreadyExist(nickname)) {
                        //System.out.println("IsEmpty: " + nickname.isEmpty() + "AlreadyExist: "+ Server.nicknameAlreadyExist(nickname));
                        //System.out.println("KO");
                        toClient.writeUTF("NICKNAMEKO");
                    }else {
                        toClient.writeUTF("NICKNAMEOK");
                        //System.out.println("OK");
                        obtainedNickname = true;
                        toClient.writeUTF("MULTIPLAYER");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    continueHandling.set(false);
                }
            }

            if (message.toUpperCase().contains("MULTIPLAYER") && obtainedNickname) {

                //System.out.println("Inside multiplayer "+ message.substring(10));

                if (message.substring(11).equalsIgnoreCase("YES")) {
                    try {
                        if (Server.nicknameAlreadyExist(nickname)){
                            toClient.writeUTF("NICKNAMEKO");
                        } else {
                            toClient.writeUTF("MULTIPLAYEROK");
                            temporaryPlayer = new TemporaryPlayer(this.clientSocket, nickname, false);
                            Server.add(temporaryPlayer);
                            System.out.println("Added to players list " + nickname);
                            Server.updateUsers();
                            obtainedMultiplayer = true;
                        }
                    } catch (MaxPlayersException | IOException e) {
                        e.printStackTrace();
                        continueHandling.set(false);
                    }
                } else {
                    try {
                        toClient.writeUTF("MULTIPLAYEROK");
                        temporaryPlayer = new TemporaryPlayer(this.clientSocket, nickname, false);
                        Server.startSinglePlayergame(temporaryPlayer);
                    } catch (IOException e) {
                        e.printStackTrace();
                        continueHandling.set(false);
                    }
                    continueHandling.set(false);
                }
            }

            if (message.equalsIgnoreCase("EXIT")){
                System.out.println(this.temporaryPlayer.getNickname() + " exit from game");
                try {
                    toClient.writeUTF("EXIT");
                    stillAlive.set(false);
                    Server.remove(this.temporaryPlayer);
                    Server.updateUsers();
                } catch (IOException | MaxPlayersException e){
                    e.printStackTrace();
                    continueHandling.set(false);
                }
                continueHandling.set(false);
            }

            if (message.equalsIgnoreCase("START") && Server.isLeader(this.temporaryPlayer) && obtainedMultiplayer) {
                if (Server.size() > 1) {
                    stillAlive.set(false);
                    Server.startMultiplayerGame(Server.size());
                    continueHandling.set(false);
                }
            }
        }
        stillAlive.set(false);
    }
}
