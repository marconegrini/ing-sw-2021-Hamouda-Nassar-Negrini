package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.MultiPlayerManager;
import it.polimi.ingsw.server.controller.SinglePlayerManager;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.exceptions.MaxPlayersException;
import it.polimi.ingsw.server.model.multiplayer.MultiPlayerGameInstance;
import it.polimi.ingsw.server.model.singleplayer.SinglePlayerGameInstance;
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

public class Server {

    /**
     * List containing connected temporary players waiting for the game to start
     */
    private static final LinkedList<TemporaryPlayer> temporaryPlayers = new LinkedList<>();

    /**
     * Sets and increments a new user ID each time a user connect
     */
    private static AtomicInteger userId = new AtomicInteger(0);

    public static synchronized void add(TemporaryPlayer tp) throws IOException {
        temporaryPlayers.add(tp);
    }

    public static synchronized void remove(TemporaryPlayer tp){
        temporaryPlayers.remove(tp);
    }

    /**
     * initializes a new multiplayerGame containing temporary players in temporaryPlayers
     * @param size temporaryPlayers when method was called
     * @throws IOException
     * @throws MaxPlayersException
     */
    public static synchronized void startMultiplayerGame(int size) {
        Game game = Game.getInstance();
        Integer gameId = game.newGame(true);
        MultiPlayerGameInstance gameInstance = (MultiPlayerGameInstance) game.getGameInstance(gameId);

        for (int i=0; i < size; i++){
            TemporaryPlayer tp = temporaryPlayers.removeFirst();
            try {
                tp.getDataOutputStream().writeUTF("GAME STARTED");
                System.out.println("Starting game for: " + tp.getNickname());
                gameInstance.addPlayer(tp.getNickname(), userId.getAndIncrement(), tp.getDataOutputStream(), tp.getDataInputStream());
            } catch(IOException e1){
                System.out.println("Exception occurred while handling socket");
            } catch(MaxPlayersException e2){
                System.out.println("Maximum number of players reached");
            }
        }

        if(!temporaryPlayers.isEmpty())
            temporaryPlayers.getFirst().setFirstPlayer();

        MultiPlayerManager manager = (MultiPlayerManager) game.getGameManager(gameId);
        MultiPlayerGameHandler gameHandler = new MultiPlayerGameHandler(gameInstance, manager);
        gameHandler.start();
    }

    public static void startSinglePlayergame(TemporaryPlayer tp) {
        Game game = Game.getInstance();
        Integer gameId = game.newGame(false);
        SinglePlayerGameInstance gameInstance = (SinglePlayerGameInstance) game.getGameInstance(gameId);
        try {
            gameInstance.addPlayer(tp.getNickname(), userId.getAndIncrement(), tp.getDataOutputStream(), tp.getDataInputStream());
            tp.getDataOutputStream().writeUTF("GAME STARTED");
        } catch(IOException e){
            System.out.println("Exception occurred while handling socket");
        }
        SinglePlayerManager manager = (SinglePlayerManager) game.getGameManager(gameId);

        SinglePlayerGameHandler gameHandler = new SinglePlayerGameHandler(gameInstance, manager);
        gameHandler.start();

    }

    public static synchronized int size(){
        return temporaryPlayers.size();
    }

    public static synchronized void updateUsers() throws IOException, MaxPlayersException {
            if(temporaryPlayers.size() == 4){
                startMultiplayerGame(4);
            } else{
                for(TemporaryPlayer tp : temporaryPlayers){
                    if(isLeader(tp)){
                        tp.setFirstPlayer();
                        tp.getDataOutputStream().writeUTF("LEADER");
                    }
                    tp.getDataOutputStream().writeUTF((temporaryPlayers.size()-1) + "WAITING");
                }
            }
    }

    public static synchronized boolean isLeader(TemporaryPlayer tp){
        if(temporaryPlayers.getFirst().equals(tp)) return true;
        else return false;
    }

    public static synchronized boolean nicknameAlreadyExist(String nickname){
        for(TemporaryPlayer tp : temporaryPlayers){
            if(nickname.equalsIgnoreCase(tp.getNickname().toUpperCase()))
                return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(5056);


        System.out.println("Server On listening with port: " + serverSocket);
        System.out.println("-------------");

        while (true){

            Socket clientSocket = null;
            System.out.println("");
            for (TemporaryPlayer player: temporaryPlayers){
                System.out.println(player.getNickname());
            }

            try{
                clientSocket = serverSocket.accept();
                System.out.println("-------------");
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
