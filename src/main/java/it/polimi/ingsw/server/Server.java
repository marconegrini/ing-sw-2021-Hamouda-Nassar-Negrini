package it.polimi.ingsw.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    private static List<TemporaryPlayer> temporaryPlayers = new ArrayList();

    public static synchronized void add(TemporaryPlayer tp) throws IOException {
        temporaryPlayers.add(tp);
    }

    public static synchronized void remove(TemporaryPlayer tp){
        temporaryPlayers.remove(tp);
    }

    public static synchronized void startgame () throws IOException {
        for (int i=3; i>=0; i--){
            temporaryPlayers.get(i).getDataOutputStream().writeUTF("GAME STARTED");
            System.out.println("Starting game for: " + temporaryPlayers.get(i).getNickname());
            temporaryPlayers.remove(i);
        }
    }

    public static synchronized void updateUsers() throws IOException {

            if(temporaryPlayers.size() >= 4){
                startgame();
            } else{
                for(TemporaryPlayer tp : temporaryPlayers){
                //System.out.println("");
                tp.getDataOutputStream().writeUTF("Waiting with other " + (temporaryPlayers.size()-1) + " players");
                }
            }
    }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(5056);

        System.out.println("Server On listening with port: " + serverSocket);
        System.out.println("");

        while (true){

            Socket clientSocket = null;
            System.out.println("There are: "+ temporaryPlayers.size() + " players.");
            for (TemporaryPlayer player: temporaryPlayers){
                System.out.println(player.getNickname());

            }


            try{
                clientSocket = serverSocket.accept();
                System.out.println("New connection from: " + clientSocket);

                DataInputStream fromClient = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream toClient = new DataOutputStream(clientSocket.getOutputStream());

                System.out.println("Assigning a new thread to the host:" + clientSocket);
                System.out.println("-------------");

                Thread t = new ClientHandler(clientSocket, fromClient, toClient);

                t.start();

            } catch(Exception e){
                clientSocket.close();
                e.printStackTrace();
            }
        }
    }

}
