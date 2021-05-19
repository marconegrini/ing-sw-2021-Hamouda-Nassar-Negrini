package it.polimi.ingsw.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameConnection {

    public boolean executeLobby(Socket socket, Scanner scanner, DataInputStream fromServer, DataOutputStream toServer, BufferedReader buffer) {


        AtomicBoolean isStarted = new AtomicBoolean(false);
        AtomicBoolean endLobby = new AtomicBoolean(true);
        AtomicBoolean endThread = new AtomicBoolean(true);
        AtomicBoolean isMultiplayer = new AtomicBoolean();
        AtomicBoolean threadStarted = new AtomicBoolean(false);
        AtomicBoolean nicknameOk = new AtomicBoolean(false);
        AtomicBoolean alreadyExecuted = new AtomicBoolean(false);
        AtomicBoolean nicknameInserted = new AtomicBoolean(false);


        String message = "";

        System.out.println("Connecting to:" + socket);

        while (endLobby.get()) {

            //waiting for a message from the server
            while (true) {
                try {
                    if (fromServer.available() > 0) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //obtaining the message from the server
            try {
                message = fromServer.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (message.toUpperCase().contains("PING")) {
                Runnable runnable = () -> {
                    try {
                        toServer.writeUTF("PING");
                        System.out.println("Ricevuto e ripingato");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                };

                Thread startGame = new Thread(runnable);
                startGame.start();
            }

            //starting to analyze the message
            if (message.toUpperCase().contains("NICKNAME")) {

                if (message.toUpperCase().contains("OK")) {
                    nicknameOk.set(true);
                }

                if (message.toUpperCase().contains("KO")) {
                    System.out.println("Choose another nickname: ");
                    nicknameOk.set(false);
                }

                if (!nicknameInserted.get()) {
                    System.out.println("Type your nickname:");
                }

                Runnable runnable = () -> {

                    try {
                        while (!nicknameOk.get()) {
                            String nickname = scanner.nextLine();
                            toServer.writeUTF("NICKNAME" + nickname);
                            nicknameOk.set(true);
                            nicknameInserted.set(true);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                };

                Thread nickname = new Thread(runnable);
                nickname.start();
                alreadyExecuted.set(true);
            }

            if (message.toUpperCase().contains("MULTIPLAYER")) {

                if (message.toUpperCase().contains("OK")) {
                    if (isMultiplayer.get())
                        System.out.println("Added to players list.\nType 'EXIT' to leave the game");
                    else endLobby.set(false);
                } else {
                    Runnable runnable = () -> {
                        System.out.println("Start a multiplayer game? [yes/no]");

                        while (true) {
                            try {
                                String multiplayer = scanner.nextLine();
                                if ((multiplayer.isEmpty() || !multiplayer.equalsIgnoreCase("YES"))
                                        && (multiplayer.isEmpty() || !multiplayer.equalsIgnoreCase("NO"))) {
                                    System.out.println("Type again your choice: ");
                                } else {
                                    isMultiplayer.set(multiplayer.equalsIgnoreCase("YES"));
                                    try {
                                        toServer.writeUTF("MULTIPLAYER" + multiplayer);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Catchata: " + e.getMessage());
                                break;
                            }
                        }
                    };
                    Thread multiplayer = new Thread(runnable);
                    multiplayer.start();
                }
            }

            if (message.toUpperCase().contains("LEADER")) {

                Runnable runnable = () -> {
                    System.out.println("You are the leader: Type START to start the game");
                };

                Thread leader = new Thread(runnable);
                leader.start();
            }

            if (message.toUpperCase().contains("WAITING")) {
                String data = message;
                Runnable runnable = () -> {
                    System.out.println("Waiting with other: " + data.charAt(0) + " players");
                };

                Thread leader = new Thread(runnable);
                leader.start();
            }


            if (!threadStarted.get() && isMultiplayer.get()) {
                threadStarted.set(true);
                Runnable runnable1 = () -> {
                    try {
                        String toExit;
                        while (endThread.get()) {

                            if (buffer.ready()) {
                                toExit = buffer.readLine();
                                if (toExit.equalsIgnoreCase("EXIT")) {
                                    toServer.writeUTF(toExit);
                                    buffer.close();
                                    endThread.set(false);
                                } else if (toExit.equalsIgnoreCase("START")) toServer.writeUTF(toExit);
                                else System.out.println("Bad command. Type another command");
                            }
                        }
                    } catch (IOException e) {
                        endThread.set(false);
                    }
                };
                Thread exit = new Thread(runnable1);
                exit.start();
            }

            if (message.toUpperCase().contains("EXIT")) {

                System.out.println("Exiting the game...");
                endLobby.set(false);
            }

            if (message.toUpperCase().contains("GAME STARTED")) {

                try {
                    toServer.writeUTF("PINGKO");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Runnable runnable = () -> {
                    endThread.set(false);
                    isStarted.set(true);
                };

                Thread startGame = new Thread(runnable);
                startGame.start();

                if (isMultiplayer.get()) {
                    System.out.println("You are inside the game");
                } else {
                    System.out.println("You are inside the game with Lorenzo");
                }
                endLobby.set(false);
            }
        }
        try {
            buffer.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isStarted.get();
    }
}