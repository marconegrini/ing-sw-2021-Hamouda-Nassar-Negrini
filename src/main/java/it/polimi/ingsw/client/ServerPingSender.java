package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromClient.ClientPingMessage;
import it.polimi.ingsw.server.Server;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

public class ServerPingSender implements Runnable {

    private InputStreamReader isr;
    private OutputStreamWriter osw;
    private BufferedReader reader;
    private BufferedWriter writer;
    private PrintWriter out;
    private ServerHandler owner;

    Socket socket;
    ClientPingMessage clientPingMessage = new ClientPingMessage();

    public ServerPingSender(Socket socket, ServerHandler owner) {
        this.socket = socket;
        this.owner = owner;
    }

    public void run() {
        try {
            isr = new InputStreamReader(socket.getInputStream());
            osw = new OutputStreamWriter(socket.getOutputStream());
            reader = new BufferedReader(isr); //ciao
            writer = new BufferedWriter(osw);
            out = new PrintWriter(writer, true);
        } catch (IOException e){
            ClientCLI.logger.log(Level.INFO,"Cannot open connection to " + socket);
            return;
        }

        while (true) {
            sendJson(clientPingMessage);
            try {
                // thread to sleep for 5000 milliseconds
                Thread.sleep(5000);
            } catch (Exception e) {
                System.out.println(e);
            }
            if(owner.getShouldStop()){
                break;
            }
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClientCLI.logger.log(Level.INFO,"end of Server ping sendder");
        System.exit(0);
    }

    public void sendJson(ClientMessage message){
        Gson gson = new Gson();
        String toSend = gson.toJson(message);
        out.println(toSend);
    }
}
