package it.polimi.ingsw.CLI;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConnectionToServer {

    private final Socket socket;
    private final Scanner scanner;
    private final DataInputStream fromServer;
    private final DataOutputStream toServer;
    private final BufferedReader buffer;


    public ConnectionToServer(Socket socket) throws IOException {
        this.socket = socket;
        this.scanner = new Scanner(System.in);
        this.fromServer = new DataInputStream (socket.getInputStream());
        this.toServer = new DataOutputStream (socket.getOutputStream());
        this.buffer = new BufferedReader(new InputStreamReader(System.in));
        this.executeLobby();
    }


    public void executeLobby() {

        try {

            AtomicBoolean endThread = new AtomicBoolean(true);
            boolean isMultiplayer, isStarted = false;

            System.out.println("Connecting to: " + socket);

            //asking and obtaining user's nickname
            System.out.println("Type your nickname:");

            while (true) {
                String nickname = scanner.nextLine();
                toServer.writeUTF(nickname);
                if (fromServer.readUTF().equals("OK"))
                    break;
                else System.out.println("Choose another nickname: ");
            }

            System.out.println("Start a multiplayer game? [yes/no]");

            while (true) {
                String multiplayer = scanner.nextLine();

                if ((multiplayer.isEmpty() || !multiplayer.equalsIgnoreCase("YES"))
                        && (multiplayer.isEmpty() || !multiplayer.equalsIgnoreCase("NO"))){
                    System.out.println("Type again your choice: ");
                }else {
                    isMultiplayer = multiplayer.equalsIgnoreCase("YES");
                    toServer.writeUTF(multiplayer);
                    break;
                }
            }

            if (isMultiplayer) {

                System.out.println("Added to players list.\nType 'EXIT' to leave the game");

                Runnable runnable1 = () -> {
                    try {
                        String toExit;
                        while (endThread.get()) {

                            if (buffer.ready()) {
                                toExit = buffer.readLine();
                                if (toExit.equalsIgnoreCase("EXIT") || toExit.equalsIgnoreCase("START")) {
                                    toServer.writeUTF(toExit);
                                    buffer.close();
                                    endThread.set(false);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                };

                Thread exit = new Thread(runnable1);
                exit.start();

                String notification = "";

                while (true) {
                    try {
                        //while (! (fromServer.available() >0) )
                        notification = fromServer.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (notification.equalsIgnoreCase("LEADER"))
                        System.out.println("You are the game leader: type 'START' to start the game.");

                    if (notification.toUpperCase().contains("WAITING"))
                        System.out.println("Waiting with other: " + notification.charAt(0) + " players");


                    if (notification.equalsIgnoreCase("EXIT")) {
                        System.out.println("Exiting the game...");
                        buffer.close();
                        break;
                    }

                    if (notification.equalsIgnoreCase("GAME STARTED")) {
                        endThread.set(false);
                        isStarted = true;
                        break;
                    }
                }

                if (isStarted) {

                    while (true) {

                        try {
                            notification = fromServer.readUTF();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (notification.equalsIgnoreCase("OK IN GAME")) {
                            System.out.println("You are inside the game");
                        }

                        if (notification.equalsIgnoreCase("HASCALAMAIO")) {
                            System.out.println("You have the calamaio");
                        }
                    }
                }

            } else {

                String notification = "";

                while (true) {
                    try {
                        notification = fromServer.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (notification.equalsIgnoreCase("GAME STARTED")) {
                        System.out.println("The game is starting...");
                        isStarted = true;
                        break;
                    }
                }

                if (isStarted) {

                    while (true) {
                        try {
                            notification = fromServer.readUTF();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (notification.equalsIgnoreCase("OK IN GAME")) {
                            System.out.println("You are inside a game with Lorenzo");
                        }
                    }
                }
            }

            buffer.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() throws IOException {
        fromServer.close();
        toServer.close();
        socket.close();
    }

}
