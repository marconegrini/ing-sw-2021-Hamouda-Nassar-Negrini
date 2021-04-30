package it.polimi.ingsw.client;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Client {

    public static void main(String[] args) throws IOException
    {
        try
        {
            AtomicBoolean endThread = new AtomicBoolean(true);
            Scanner scn = new Scanner(System.in);
            Socket s = new Socket("127.0.0.1", 5056);

            BufferedReader br = new BufferedReader (new InputStreamReader(System.in));

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
                    while (endThread.get()) {

                        if (br.ready()){
                            toExit = br.readLine();
                            dos.writeUTF(toExit);
                            if (toExit.toUpperCase().equals("EXIT")){
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
                if(notification.toUpperCase().equals("LEADER"))
                    System.out.println("You are the game leader: type 'START' to start the game.");
                else System.out.println(notification);

                if (notification.toUpperCase().equals("GAME STARTED") || notification.toUpperCase().equals("EXIT")){
                    endThread.set(false);
                    //System.out.println("while true finished with endThread: " + endThread.get());
                    exit.interrupt();
                    break;
                }
            }

            //exit.interrupt();
            scn.close();
            dis.close();
            dos.close();
            s.close();
            br.close();
            System.out.println("Program ended");


        }catch(Exception e){
            e.printStackTrace();
        }


    }
}


