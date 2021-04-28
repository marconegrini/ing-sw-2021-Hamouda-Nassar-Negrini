package it.polimi.ingsw.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static List<DataOutputStream> temporaryPlayers = new ArrayList<>();

    public static void remove(DataOutputStream dos){
        temporaryPlayers.remove(dos);
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

                Thread t = new ClientHandler(clientSocket, fromClient, toClient, temporaryPlayers);

                temporaryPlayers.add(toClient);

                for(DataOutputStream dos : temporaryPlayers){
                    dos.writeUTF(temporaryPlayers.size() + " players waiting for the game to start");
                }

                t.start();

            } catch(Exception e){
                clientSocket.close();
                e.printStackTrace();
            }


        }

    }

}
