package it.polimi.ingsw.server;
import it.polimi.ingsw.enumerations.ANSITextFormat;
import it.polimi.ingsw.messages.fromServer.ParticipantsMessage;
import it.polimi.ingsw.server.handlers.ClientHandler;
import it.polimi.ingsw.server.handlers.ClientPingHandler;
import it.polimi.ingsw.server.handlers.MultiPlayerGameHandler;
import it.polimi.ingsw.server.handlers.SinglePlayerGameHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


/**
 * Class that starts single or multiplayer games.
 * Contains waiting room for players that choose the multiplayer game.
 */
public class Server {

    /**
     * Linked list that keeps track of users waiting to start the game
     */
    private static final LinkedList<ClientHandler> clientHandlers = new LinkedList<>();
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private static AtomicBoolean firstTime = new AtomicBoolean(false);

    public static void main(String[] args) throws IOException {
        try {
            LogManager.getLogManager().readConfiguration(Server.class.getClassLoader().getResourceAsStream("logging.properties"));
        } catch (SecurityException | IOException e1) {
            e1.printStackTrace();
        }
        ServerSocket serverSocket = new ServerSocket(5056);

        System.out.println(ANSITextFormat.BOLD +"Server running..."+ANSITextFormat.RESET);
        logger.log(Level.INFO,"Server On listening with port: " + serverSocket);
        logger.log(Level.INFO,"-------------");

        ServerSocket serverPingSocket = new ServerSocket(5070);

        System.out.println(ANSITextFormat.BOLD +"Thread for ping running..."+ANSITextFormat.RESET);
        logger.log(Level.INFO,"listening with port: " + serverSocket);
        logger.log(Level.INFO,"-------------");

        while (true){
            Socket clientSocket;
            Socket clientPingSocket;
            try{
                clientSocket = serverSocket.accept();
                clientPingSocket = serverPingSocket.accept();

                logger.log(Level.INFO,"-------------");
                logger.log(Level.INFO,"New connection from: " + clientSocket);
                logger.log(Level.INFO,"Assigning a new thread to the host: " + clientSocket);
                logger.log(Level.INFO,"-------------");
                ClientHandler client  = new ClientHandler(clientSocket,clientPingSocket);
                client.start();
            } catch(IOException e){
                logger.log(Level.INFO,"Connection dropped");
                e.printStackTrace();
            }
            logger.log(Level.INFO,"completed cycle in Server main process");
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
        logger.log(Level.INFO,"Players:");
        for (ClientHandler clientHandler: clientHandlers){
            logger.log(Level.INFO,clientHandler.getNickname());
        }
        logger.log(Level.INFO,"");
        logger.log(Level.INFO,"--------");
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
        logger.log(Level.INFO,"Removed " + clientHandler.getNickname());
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
