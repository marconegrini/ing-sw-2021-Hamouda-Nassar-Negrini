package it.polimi.ingsw.client;
import it.polimi.ingsw.messages.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;

public class Client {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("192.168.43.125", 5056);
        ClientSocket clientSocket = new ClientSocket(socket);

        GameConnection1 gameConnection = new GameConnection1();

        boolean isStarted = gameConnection.executeLobby(clientSocket.getSocket(), clientSocket.getScanner(),
                clientSocket.getFromServer(), clientSocket.getToServer(), clientSocket.getBuffer());

        Queue<Message> clientBuffer;

        /*
        if(isStarted) {
            clientBuffer = new ConcurrentLinkedQueue<>();
            GameSetUp gameSetUp = new GameSetUp();
            gameSetUp.initialSetUp(clientSocket.getSocket(), clientSocket.getScanner(),
                    clientSocket.getFromServer(), clientSocket.getToServer(), clientSocket.getBuffer(), clientBuffer);
        }
         */

        clientSocket.closeConnection();
    }
}


