package it.polimi.ingsw.server;
import it.polimi.ingsw.server.handlers.ClientHandler;
import it.polimi.ingsw.server.handlers.MultiPlayerGameHandler;
import it.polimi.ingsw.server.handlers.SinglePlayerGameHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    private static final LinkedList<ClientHandler> clientHandlers = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5056);
        System.out.println("Server On listening with port: " + serverSocket);
        System.out.println("-------------");
        while (true){
            Socket clientSocket ;
            try{
                clientSocket = serverSocket.accept();
                System.out.println("-------------");
                System.out.println("New connection from: " + clientSocket);
                System.out.println("Assigning a new thread to the host: " + clientSocket);
                System.out.println("-------------");
                ClientHandler client  = new ClientHandler(clientSocket);
                client.start();
            } catch(IOException e){
                System.out.println("Connection dropped");
                e.printStackTrace();
            }
        }
    }

    public synchronized void checkStart(){
        if(clientHandlers.size() == 4){
            //start the game
        }
    }

    public static Integer getPlayersNumber(){
        return clientHandlers.size();
    }

    public static synchronized void startMultiplayerGame(){
            List<ClientHandler> handlers = new ArrayList<>();
            handlers.addAll(clientHandlers);
            MultiPlayerGameHandler gameHandler = new MultiPlayerGameHandler(handlers);
            gameHandler.start();
            clientHandlers.clear();
    }

    public static void startSinglePlayerGame(ClientHandler clientHandler){
        SinglePlayerGameHandler gameHandler = new SinglePlayerGameHandler(clientHandler);
        gameHandler.start();
    }

    public static synchronized void add(ClientHandler clientHandler){
        clientHandlers.add(clientHandler);
        printCurrentPlayers();
    }

    public static synchronized void printCurrentPlayers(){
        System.out.println("Players:");
        for (ClientHandler clientHandler: clientHandlers){
            System.out.println(clientHandler.getNickname());
        }
        System.out.println("");
        System.out.println("--------");
    }

    public static synchronized boolean nicknameAlreadyExist(String nickname){
        for (ClientHandler clientHandler : clientHandlers){
            if (nickname.equalsIgnoreCase(clientHandler.getNickname())) return true;
        }
        return false;
    }

    public static synchronized void removeClientHandler (ClientHandler clientHandler){
        clientHandlers.remove(clientHandler);
        System.out.println("Removed " + clientHandler.getNickname());
    }

    public static synchronized void clearParticipants(){
        clientHandlers.clear();
    }



}
