package it.polimi.ingsw.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Server {

    private static List<TemporaryPlayer> temporaryPlayers = new ArrayList();

    public static void add(TemporaryPlayer tp) throws IOException {
        temporaryPlayers.add(tp);
    }

    public static void remove(TemporaryPlayer tp){
        temporaryPlayers.remove(tp);
    }

    public static void updateUsers() throws IOException {
        for(TemporaryPlayer tp : temporaryPlayers){
            if(temporaryPlayers.size() == 4){
                tp.getDataOutputStream().writeUTF("GAME STARTED");
            } else tp.getDataOutputStream().writeUTF("Waiting with other " + (temporaryPlayers.size()-1) + " players");
        }
    }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(5056);

        System.out.println("Server On listening with port: " + serverSocket);
        System.out.println("");

        while (true){

            Socket clientSocket = null;

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
