package it.polimi.ingsw.server;
import it.polimi.ingsw.messages.fromServer.ParticipantsMessage;
import it.polimi.ingsw.server.handlers.ClientHandler;
import it.polimi.ingsw.server.handlers.MultiPlayerGameHandler;
import it.polimi.ingsw.server.handlers.SinglePlayerGameHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.net.InetAddress;
public class Server {

    /**
     * Linked list that keeps track of users waiting to start the game
     */
    private static final LinkedList<ClientHandler> clientHandlers = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println("IP Address:- " + inetAddress.getHostAddress());
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

    /**
     * Number of players waiting for the game to start
     */
    public static Integer getPlayersNumber(){
        return clientHandlers.size();
    }

    /**
     * Invoked to start the multiplayer game. If four players are waiting for a multiplayer game
     * to start, the method is invoked automatically. If less than four players are waiting for the
     * multiplayer game to start and one of them types 'START', the method is invoked 'manually'.
     */
    public static synchronized void startMultiplayerGame(){
            List<ClientHandler> handlers = new ArrayList<>();
            handlers.addAll(clientHandlers);
            MultiPlayerGameHandler gameHandler = new MultiPlayerGameHandler(handlers);
            gameHandler.start();
            clientHandlers.clear();
    }

    /**
     * Automatically invoked when a player asks to start a single player game
     * @param clientHandler
     */
    public static void startSinglePlayerGame(ClientHandler clientHandler){
        SinglePlayerGameHandler gameHandler = new SinglePlayerGameHandler(clientHandler);
        gameHandler.start();
    }

    /**
     * Adds a client handler waiting for a multiplayer game to start in the list if client handlers.
     * @param clientHandler
     */
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

    /**
     * Invoked each time a multiplayer player logs in the game. If typed nickname from the user already
     * exists, the method returns false and the game asks to insert a different nickname
     * @param nickname
     * @return
     */
    public static synchronized boolean nicknameAlreadyExist(String nickname){
        for (ClientHandler clientHandler : clientHandlers){
            if (nickname.equalsIgnoreCase(clientHandler.getNickname())) return true;
        }
        return false;
    }

    /**
     * If a multiplayer player waiting for a multiplayer game to start decides to exit the waiting room,
     * the following method is invoked and the client is removed from the list of clients waiting for
     * the game to start.
     * @param clientHandler
     */
    public static synchronized void removeClientHandler (ClientHandler clientHandler){
        clientHandlers.remove(clientHandler);
        System.out.println("Removed " + clientHandler.getNickname());
        sendParticipantsNumberUpdate();
    }

    public static synchronized void clearParticipants(){
        clientHandlers.clear();
    }

    /**
     * Updates clients about the number af participants waiting for the game to start
     */
    public static synchronized void sendParticipantsNumberUpdate(){
        for (ClientHandler clientHandler : clientHandlers){
            clientHandler.sendJson(new ParticipantsMessage(getPlayersNumber() -1));
        }
    }


}
