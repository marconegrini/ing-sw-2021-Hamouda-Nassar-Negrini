package it.polimi.ingsw.server.handlers;
import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromClient.ClientMessageFactory;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerPing;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.controller.TurnManager;
import java.io.*;
import java.net.Socket;
import java.nio.CharBuffer;

public class ClientHandler extends Thread {

    private final Socket client;
    private InputStreamReader isr;
    private OutputStreamWriter osw;
    private BufferedReader reader;
    private BufferedWriter writer;
    private PrintWriter out;
    private Player player;
    private TurnManager turnManager;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.client = clientSocket;
    }

    @Override
    public void run(){
        try{
            this.isr = new InputStreamReader(client.getInputStream());
            this.osw = new OutputStreamWriter(client.getOutputStream());
            this.reader = new BufferedReader(this.isr);
            this.writer = new BufferedWriter(this.osw);
            out = new PrintWriter(writer, true);
        } catch (IOException e){
            System.out.println("Could not open connection to " + client.getInetAddress());
        }

        System.out.println("Connected to " + client);
        pingClient();
        try{
            processServerMessages();
        } catch (IOException e){
            System.out.println("Client " + client.getInetAddress() + " connection drop");
        }

        try {
            client.close();
        } catch (IOException e) {
            System.out.println("Exception occurred while closing server socket");
        }
    }

    private void processServerMessages() throws IOException {
        ClientMessageFactory factory = new ClientMessageFactory();
        while (true) {
            try {
                String jsonMessage = reader.readLine();
                ClientMessage message = factory.returnMessage(jsonMessage);
                message.serverProcess(this);
            } catch (MalformedJsonException e) {
                System.out.println("Invalid json object from client");
            }
        }
    }

    public void sendJson(ServerMessage message){
        Gson gson = new Gson();
        String toSend = gson.toJson(message);
        out.println(toSend);
    }

    public void pingClient(){
        ServerMessage ping = new ServerPing();
        this.sendJson(ping);
    }

    private TurnManager getTurnManager(){
        return this.turnManager;
    }
}
