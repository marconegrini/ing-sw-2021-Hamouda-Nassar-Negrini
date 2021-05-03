package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.MultiPlayerManager;
import it.polimi.ingsw.controller.SinglePlayerManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.exceptions.MaxPlayersException;
import it.polimi.ingsw.model.multiplayer.MultiPlayerGameInstance;
import it.polimi.ingsw.model.singleplayer.SinglePlayerGameInstance;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    private static final LinkedList<TemporaryPlayer> temporaryPlayers = new LinkedList<>();

    private static AtomicInteger userId = new AtomicInteger(0);

    public static synchronized void add(TemporaryPlayer tp) throws IOException {
        temporaryPlayers.add(tp);
    }

    public static synchronized void remove(TemporaryPlayer tp){
        temporaryPlayers.remove(tp);
    }

    public static synchronized void startMultiplayerGame(int size) throws IOException, MaxPlayersException {
        Game game = Game.getInstance();
        Integer gameId = game.newGame(true);
        MultiPlayerGameInstance gameInstance = (MultiPlayerGameInstance) Game.getGameInstance(gameId);

        for (int i=0; i < size; i++){

            TemporaryPlayer tp = temporaryPlayers.removeFirst();
            tp.getDataOutputStream().writeUTF("GAME STARTED");
            System.out.println("Starting game for: " + tp.getNickname());
            gameInstance.addPlayer(tp.getNickname(), userId.getAndIncrement(), tp.getDataOutputStream(), tp.getDataInputStream());
        }

        if(!temporaryPlayers.isEmpty())
            temporaryPlayers.getFirst().setFirstPlayer();

        MultiPlayerManager manager = (MultiPlayerManager) Game.getGameManager(gameId);

        MultiPlayerGameHandler gameHandler = new MultiPlayerGameHandler(gameInstance, manager);

        gameHandler.start();

    }

    public static void startSinglePlayergame(TemporaryPlayer tp) throws IOException {
        Game game = Game.getInstance();
        Integer gameId = Game.newGame(false);
        SinglePlayerGameInstance gameInstance = (SinglePlayerGameInstance) Game.getGameInstance(gameId);
        gameInstance.addPlayer(tp.getNickname(), userId.getAndIncrement(),tp.getDataOutputStream(), tp.getDataInputStream());
        tp.getDataOutputStream().writeUTF("GAME STARTED");
        SinglePlayerManager manager = (SinglePlayerManager) Game.getGameManager(gameId);



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
