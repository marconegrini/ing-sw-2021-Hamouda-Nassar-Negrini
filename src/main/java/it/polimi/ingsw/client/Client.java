package it.polimi.ingsw.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {

    public static void main(String[] args) throws IOException {
        try {
            AtomicBoolean endThread = new AtomicBoolean(true);
            Scanner scn = new Scanner(System.in);
            Socket s = new Socket("192.168.43.91", 5056);

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            boolean isMultiplayer;
            boolean isStarted = false;

            System.out.println("Connecting to: " + s);

            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            //asking and obtaining user's nickname
            System.out.println(dis.readUTF());

            while (true) {
                String nickname = scn.nextLine();
                dos.writeUTF(nickname);
                if (dis.readUTF().equals("OK"))
                    break;
                else System.out.println("Choose another nickname: ");
            }

            //asking and obtaining boolean multiplayer
            System.out.println(dis.readUTF());

            while (true) {
                String multiplayer = scn.nextLine();
                dos.writeUTF(multiplayer);

                if (dis.readUTF().equals("OK")) {

                    if(multiplayer.toUpperCase().equals("YES")) {
                        isMultiplayer = true;
                    } else {isMultiplayer = false;}
                    break;

                } else System.out.println("Type again your choice: ");
            }

            //System.out.println("isMultiplayer: " + isMultiplayer);

            if (isMultiplayer) {

                //System.out.println("Dentro");

                Runnable runnable1 = () -> {
                    try {
                        String toExit;
                        while (endThread.get()) {

                            if (br.ready()) {
                                toExit = br.readLine();
                                dos.writeUTF(toExit);
                                if (toExit.toUpperCase().equals("EXIT")) {
                                    br.close();
                                    break;
                                }
                            }
                            //if (endThread.get())    break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //System.out.println("Thread finished with endThread: " + endThread.get());
                };

                Thread exit = new Thread(runnable1);
                exit.start();

                String notification = "";

                while (true) {
                    try {
                        notification = dis.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (notification.toUpperCase().equals("LEADER"))
                        System.out.println("You are the game leader: type 'START' to start the game.");
                    else System.out.println(notification);

                    if (notification.toUpperCase().equals("EXIT")) {
                        endThread.set(false);
                        br.close();
                        break;
                    }

                    if (notification.toUpperCase().equals("GAME STARTED")) {
                        endThread.set(false);
                        isStarted = true;
                        break;
                        //System.out.println("while true finished with endThread: " + endThread.get());
                        //exit.interrupt();
                    }
                }

                if (isStarted) {

                    while (true) {

                        try {
                            notification = dis.readUTF();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (notification.toUpperCase().equals("OK IN GAME")) {
                            System.out.println("You are inside the game");
                        }

                        if (notification.toUpperCase().equals("HASCALAMAIO")) {
                            System.out.println("You have the calamaio");
                        }
                    }
                }

            } else{
                    Runnable runnable1 = () -> {
                        try {
                            String message;
                            while (true) {
                                if (br.ready()){
                                    message = br.readLine();
                                    dos.writeUTF(message);
                                    if (message.toUpperCase().equals("OK IN GAME")){
                                        System.out.println("You are inside the game");
                                    }

                                    if (message.toUpperCase().equals("EXIT")){
                                        br.close();
                                        break;
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    };
                    Thread clientComunication = new Thread(runnable1);
                    clientComunication.start();

                String notification = "";

                while (true) {

                    try {
                        notification = dis.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (notification.toUpperCase().equals("EXIT")) {
                        endThread.set(false);
                        br.close();
                        break;
                    }

                    if (notification.toUpperCase().equals("GAME STARTED")) {
                        endThread.set(false);
                        isStarted = true;
                        break;
                    }
                }

                if (isStarted) {

                    while (true) {

                        try {
                            notification = dis.readUTF();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (notification.toUpperCase().equals("OK IN GAME")) {
                            System.out.println("You are inside the game");
                        }

                    }
                }



            }

            //Inside game


            //exit.interrupt();
            scn.close();
            dis.close();
            dos.close();
            s.close();
            br.close();
            System.out.println("Program ended");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}


