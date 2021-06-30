package it.polimi.ingsw.client;

import java.io.IOException;
import java.net.Socket;

/**
 * Class that contains methods to interact with the user in the CLI game
 */
public class ClientCLI implements Runnable{

    private ServerHandler serverHandler;

    //public static void main(String[] args) throws IOException {
    //    Client client = new Client();
    //    client.run();
    //}

    /**
     * run method that handles ServerHandler thread
     */
    @Override
    public void run(){
        Socket server;

        try {
            server = new Socket("127.0.0.1", 5056);
        } catch (IOException e){
            System.out.println("Server unreachable");
            return;
        }
        serverHandler = new ServerHandler(server, true);
        Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
        serverHandlerThread.start();
    }

}


