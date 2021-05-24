package it.polimi.ingsw.client;

import java.io.IOException;
import java.net.Socket;


public class Client implements Runnable{

    private ServerHandler serverHandler;
    private boolean shallTerminate;

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.run();
    }

    @Override
    public void run(){
        Socket server;
        try {
            server = new Socket("127.0.0.1", 5056);
        } catch (IOException e){
            System.out.println("Server unreachable");
            return;
        }
        serverHandler = new ServerHandler(server, this);
        Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
        serverHandlerThread.start();

        //serverHandler.stop();
    }

    public synchronized void terminate(){
        //shallTerminate is the signal to the view handler loop that it should exit
        if(!shallTerminate){
            shallTerminate = true;
        }
    }

}


