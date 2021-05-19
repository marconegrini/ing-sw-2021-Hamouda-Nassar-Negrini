package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Client {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 5056);
        ClientSocket clientSocket = new ClientSocket(socket);

        GameConnection gameConnection = new GameConnection();

        boolean isStarted = gameConnection.executeLobby(clientSocket.getSocket(), clientSocket.getScanner(),
                clientSocket.getFromServer(), clientSocket.getToServer(), clientSocket.getBuffer());

        Queue<Message> clientBuffer;

        clientBuffer = new ConcurrentLinkedQueue<>();

        if(isStarted) {
            GameSetUp gameSetUp = new GameSetUp();
            gameSetUp.initialSetUp(clientSocket.getSocket(), clientSocket.getScanner(),
                    clientSocket.getFromServer(), clientSocket.getToServer(), clientSocket.getBuffer(), clientBuffer);
        }
        clientSocket.closeConnection();
    }
}


