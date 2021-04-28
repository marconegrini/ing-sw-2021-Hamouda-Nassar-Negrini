package it.polimi.ingsw.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread{

    private final Socket clientSocket;
    private final DataInputStream fromClient;
    private final DataOutputStream toClient;

    public ClientHandler(Socket clientSocket, DataInputStream fromClient, DataOutputStream toClient, List<DataOutputStream> temporaryPlayers) {
        this.clientSocket = clientSocket;
        this.fromClient = fromClient;
        this.toClient = toClient;
    }


    public void run(){

        String received;

        while(true){

            try {
                toClient.writeUTF("Send me a message and I will get it to you back upper case");

                received = fromClient.readUTF();


                if (received.equals("Exit")){
                    System.out.println();
                    System.out.println("----------------");
                    toClient.writeUTF("Closing the connection");
                    System.out.println("Connection with:" + clientSocket + " Closed");
                    System.out.println("----------------");
                    System.out.println();
                    break;
                }

                System.out.println("Received: '"+ received+ "' from client:" + clientSocket);

                toClient.writeUTF(received.toUpperCase());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            this.fromClient.close();
            Server.remove(toClient);
            this.toClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
