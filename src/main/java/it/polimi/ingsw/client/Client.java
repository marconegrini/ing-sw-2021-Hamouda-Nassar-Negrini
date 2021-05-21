package it.polimi.ingsw.client;
import it.polimi.ingsw.messages.Message;


import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Client implements Runnable{

    private ServerHandler serverHandler;

    public static void main(String[] args) throws IOException {

        Client client = new Client();
        client.run();
    }

    public void run(){
        Socket server;
        try {
            server = new Socket("127.0.0.1", 5056);
        } catch (IOException e){
            System.out.println("Server unreachable");
            return;
        }

        serverHandler = new ServerHandler(server, this);
        serverHandler.start();
    }

}


