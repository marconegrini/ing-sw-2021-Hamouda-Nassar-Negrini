package it.polimi.ingsw.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

// Client class
public class Client4{

    public static void main(String[] args) throws IOException
    {
        try
        {
            Scanner scn = new Scanner(System.in);

            Socket s = new Socket("127.0.0.1", 5056);

            System.out.println("Connecting to: " + s);

            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            //asking and obtaining user's nickname
            System.out.println(dis.readUTF());
            String nickname = scn.nextLine();
            dos.writeUTF(nickname);

            //asking and obtaining boolean multiplayer
            System.out.println(dis.readUTF());
            String multiplayer = scn.nextLine();
            dos.writeUTF(multiplayer);

            Runnable runnable1 = () -> {
                try {
                    String toExit;
                    while (true) {
                        toExit = (scn.nextLine());
                        dos.writeUTF(toExit);
                        if (toExit.toUpperCase().equals("EXIT")) break;
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
                    notification = dis.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(notification);
                if (notification.toUpperCase().equals("GAME STARTED")) break;
            }

            exit.interrupt();
            scn.close();
            dis.close();
            dos.close();
            s.close();
            return;

        }catch(Exception e){
            e.printStackTrace();
        }


    }
}
