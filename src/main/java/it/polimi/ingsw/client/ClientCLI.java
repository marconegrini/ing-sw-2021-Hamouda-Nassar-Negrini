package it.polimi.ingsw.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Class that contains methods to interact with the user in the CLI game
 */
public class ClientCLI implements Runnable {

    public static Logger logger = Logger.getLogger(ClientCLI.class.getName());
    private ServerHandler serverHandler;

    //public static void main(String[] args) throws IOException {
    //    Client client = new Client();
    //    client.run();
    //}

    /**
     * run method that handles ServerHandler thread
     */
    String defaultServerIP = "127.0.0.1";

    @Override
    public void run() {
        String userInput = "";
        Socket server = null;
        System.out.println("Insert the server IP (type \"exit\" to exit): ");
        Scanner scanner = new Scanner(System.in);
        boolean ok = false;
        while (!ok && server == null) {
            userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("EXIT")) {
                break;
            } else {
                if (userInput.equals("localhost") || userInput.equals("\n") || userInput.equals("")) {
                    userInput = defaultServerIP;
                    ok = true;
                }
                try {
                    server = new Socket(userInput, 5056);
                    ok = true;
                } catch (IOException e) {
                    System.out.println("Server unreachable, Try another ip address: ");
                }
            }
        }

        if (!userInput.equalsIgnoreCase("EXIT")) {
            serverHandler = new ServerHandler(server, true);
            Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
            serverHandlerThread.start();
        }
    }

}


