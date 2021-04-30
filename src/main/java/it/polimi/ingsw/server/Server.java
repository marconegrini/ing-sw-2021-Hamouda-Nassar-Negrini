package it.polimi.ingsw.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    private static LinkedList<TemporaryPlayer> temporaryPlayers = new LinkedList<>();

    public static synchronized void add(TemporaryPlayer tp) throws IOException {
        temporaryPlayers.add(tp);
        if(isLeader(tp)){
            tp.setFirstPlayer();
            tp.getDataOutputStream().writeUTF("LEADER");
        }
    }

    public static synchronized void remove(TemporaryPlayer tp){
        temporaryPlayers.remove(tp);
    }

    public static synchronized void startgame (int size) throws IOException {
        for (int i=0; i < size; i++){
            temporaryPlayers.removeFirst().getDataOutputStream().writeUTF("GAME STARTED");
            System.out.println("Starting game for: " + temporaryPlayers.get(i).getNickname());
        }
        if(!temporaryPlayers.isEmpty())
            temporaryPlayers.getFirst().setFirstPlayer();
    }

    public static synchronized int size(){
        return temporaryPlayers.size();
    }

    public static synchronized void updateUsers() throws IOException {
            if(temporaryPlayers.size() == 4){
                startgame(4);
            } else{
                for(TemporaryPlayer tp : temporaryPlayers){
                //System.out.println("");
                tp.getDataOutputStream().writeUTF("Waiting with other " + (temporaryPlayers.size()-1) + " players");
                }
            }
    }

    public static synchronized boolean isLeader(TemporaryPlayer tp){
        if(temporaryPlayers.getFirst().equals(tp)) return true;
        else return false;
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
