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
                clientHandlers.add(client);
                client.start();
            } catch(IOException e){
                System.out.println("Connection dropped");
                e.printStackTrace();
            }
        }
    }

    public void checkStart(){
        if(clientHandlers.size() == 4){
            //start the game
        }
    }

    public static Integer getParticipants(){
        return clientHandlers.size();
    }

    public static void startMultiplayerGame(){
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


}
