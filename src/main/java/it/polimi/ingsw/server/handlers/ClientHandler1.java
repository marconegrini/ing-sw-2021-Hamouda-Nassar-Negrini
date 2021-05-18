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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientHandler1 extends Thread {

    private final Socket clientSocket;
    private final DataInputStream fromClient;
    private final DataOutputStream toClient;
    private TemporaryPlayer temporaryPlayer;

    public ClientHandler1(Socket clientSocket, DataInputStream fromClient, DataOutputStream toClient) {
        this.clientSocket = clientSocket;
        this.fromClient = fromClient;
        this.toClient = toClient;
        this.temporaryPlayer = null;
    }

    public void run(){

        AtomicInteger millis = new AtomicInteger();
        AtomicInteger oldPingTime = new AtomicInteger();
        AtomicInteger PingTime = new AtomicInteger();
        AtomicInteger receivedPingTime = new AtomicInteger();
        String message = "";
        String nickname = "";
        AtomicBoolean stillAlive = new AtomicBoolean(true);
        AtomicBoolean continueHandling = new AtomicBoolean(true);

        boolean obtainedNickname = false;
        boolean obtainedMultiplayer = false;
        boolean waiting = false;



        Runnable runnablePing = () -> {

            oldPingTime.set((int) System.currentTimeMillis());
            receivedPingTime.set((int) System.currentTimeMillis());
            try {
                toClient.writeUTF("PING");
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (stillAlive.get()){
                try{

                    if ((System.currentTimeMillis() - receivedPingTime.get()) > 5*1000){
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
                        }

                    }else if ((System.currentTimeMillis() - oldPingTime.get()) > 10*1000){
                        toClient.writeUTF("PING");
                        System.out.println("Ping sent");
                        oldPingTime.set((int) System.currentTimeMillis());
                    }
                } catch (IOException e){
                    e.printStackTrace();
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
        }

        while (continueHandling.get()){

            while (true) {
                try {
                    if (fromClient.available() > 0) break;
                } catch (IOException e) {
                    e.printStackTrace();
                    continueHandling.set(false);
                    break;
                }
            }

            //obtaining the message from the server
            try {
                message = fromClient.readUTF();
                System.out.println("Message received from the client: "+ message);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Handle the ping message received from the client
            if (message.equalsIgnoreCase("PING")){
                receivedPingTime.set((int) System.currentTimeMillis());
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
                            waiting = true;
                        }
                    } catch (MaxPlayersException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        toClient.writeUTF("MULTIPLAYEROK");
                        temporaryPlayer = new TemporaryPlayer(this.clientSocket, nickname, false);
                        Server.startSinglePlayergame(temporaryPlayer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    continueHandling.set(false);
                }
            }

            if (message.equalsIgnoreCase("EXIT")){
                System.out.println(this.temporaryPlayer.getNickname() + " exit from game");
                try {
                    toClient.writeUTF("EXIT");
                    Server.remove(this.temporaryPlayer);
                    Server.updateUsers();
                } catch (IOException | MaxPlayersException e){
                    e.printStackTrace();
                }
                continueHandling.set(false);
            }

            if (message.equalsIgnoreCase("START") && Server.isLeader(this.temporaryPlayer) && obtainedMultiplayer) {
                if (Server.size() > 1) {
                    Server.startMultiplayerGame(Server.size());
                    continueHandling.set(false);
                }
            }

            //System.out.println("giro");

        }


        /*
        try {
            toClient.writeUTF("NICKNAME");

            while (true) {

                    while (!(fromClient.available() > 0)) ;
                    nickname = fromClient.readUTF();

                    if( (System.currentTimeMillis() - millis.get()) >= (15 * 1000)) {
                        stillAlive.set(false);
                        System.out.println("Old time:" + millis.get()+ " Current time: "+ System.currentTimeMillis()+" Delta: " + (System.currentTimeMillis() - millis.get())/1000 );
                        throw new SocketTimeoutException();
                    }

                    if ( nickname.equalsIgnoreCase("PING")) {
                            millis.set((int) System.currentTimeMillis());
                            stillAlive.set(true);
                    }

                    if (nickname.isEmpty() || Server.nicknameAlreadyExist(nickname))
                        toClient.writeUTF("KO");
                    else {
                        toClient.writeUTF("OK");
                        break;
                    }
                }

            toClient.writeUTF("MULTIPLAYER");

            while (true) {

                while (!(fromClient.available() > 0)) ;
                multiplayer = fromClient.readUTF();

                if ( nickname.equalsIgnoreCase("PING")) {
                    if( (System.currentTimeMillis() - millis.get()) >= (15 * 1000)){
                        stillAlive.set(false);
                        throw new SocketTimeoutException();
                    } else{
                        stillAlive.set(true);
                        millis.set((int) System.currentTimeMillis());
                    }
                }

                if (multiplayer.equalsIgnoreCase("YES")) {
                    temporaryPlayer = new TemporaryPlayer(this.clientSocket, nickname, false);
                    Server.add(temporaryPlayer);
                    System.out.println("Added to players list " + nickname);
                    Server.updateUsers();

                    while (true) {
                        if (fromClient.available() > 0) {
                            String command = fromClient.readUTF().toUpperCase();

                            if (nickname.equalsIgnoreCase("PING")) {
                                if( (System.currentTimeMillis() - millis.get()) >= (15 * 1000)){
                                    stillAlive.set(false);
                                    throw new SocketTimeoutException();
                                } else{
                                    stillAlive.set(true);
                                    millis.set((int) System.currentTimeMillis());
                                }
                            }

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
            }
        } catch (SocketException e){
            System.out.println("Socket closed");
        } catch (InterruptedIOException e){
            e.printStackTrace();
            //Server.remove(this.temporaryPlayer);
        } catch (IOException | MaxPlayersException e) {
            e.printStackTrace();
        }
         */
    }
}
