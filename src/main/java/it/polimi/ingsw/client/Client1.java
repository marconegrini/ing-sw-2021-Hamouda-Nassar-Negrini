package it.polimi.ingsw.client;
import it.polimi.ingsw.server.Server;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

// Client class
public class Client1{

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

            Runnable runnable = () -> {
                try {
                    String toExit;
                    while (true) {
                        toExit = scn.nextLine();
                        dos.writeUTF(toExit);
                        if (toExit.toUpperCase().equals("EXIT")){
                            scn.close();
                            dis.close();
                            dos.close();
                            s.close();
                            return ;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };

            Thread exit = new Thread(runnable);
            exit.setDaemon(true);
            exit.start();

            while (true) {
                String notification = dis.readUTF();
                System.out.println(notification);
                if(notification.toUpperCase().equals("GAME STARTED")) break;
            }

            // closing resources
            scn.close();
            dis.close();
            dos.close();
            s.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
