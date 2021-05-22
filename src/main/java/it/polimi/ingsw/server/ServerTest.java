package it.polimi.ingsw.server;
import it.polimi.ingsw.server.controller.MultiPlayerManager;
import it.polimi.ingsw.server.controller.SinglePlayerManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.exceptions.MaxPlayersException;
import it.polimi.ingsw.model.multiplayer.MultiPlayerGameInstance;
import it.polimi.ingsw.model.singleplayer.SinglePlayerGameInstance;
import it.polimi.ingsw.server.handlers.ClientHandler;
import it.polimi.ingsw.server.handlers.MultiPlayerGameHandler;
import it.polimi.ingsw.server.handlers.SinglePlayerGameHandler;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerTest {

    private static final LinkedList<ClientHandler> temporaryPlayers = new LinkedList<>();

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(5056);

        System.out.println("Server On listening with port: " + serverSocket);
        System.out.println("-------------");

        while (true){

            Socket clientSocket = null;
            System.out.println("");

            try{
                clientSocket = serverSocket.accept();
                System.out.println("-------------");
                System.out.println("New connection from: " + clientSocket);

                System.out.println("Assigning a new thread to the host:" + clientSocket);
                System.out.println("-------------");

                ClientHandler client  = new ClientHandler(clientSocket);
                temporaryPlayers.add(client);
                client.start();

            } catch(IOException e){
                System.out.println("Connection dropped");
                e.printStackTrace();
            }
        }
    }

}
