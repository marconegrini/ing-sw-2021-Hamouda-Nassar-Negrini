package it.polimi.ingsw.server.handlers;

import com.google.gson.JsonIOException;
import com.google.gson.stream.MalformedJsonException;
import it.polimi.ingsw.messages.fromClient.ClientMessageFactory;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageFactory;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.MaxPlayersException;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.TemporaryPlayer;
import it.polimi.ingsw.server.controller.TurnManager;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class ClientHandler extends Thread {

    private final Socket client;
    private InputStreamReader isr;
    private OutputStreamWriter osr;
    private BufferedReader reader;
    private BufferedWriter writer;
    private Player player;
    private TurnManager turnManager;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.client = clientSocket;
    }

    @Override
    public void run(){
        try{
            this.isr = new InputStreamReader(client.getInputStream());
            this.osr = new OutputStreamWriter(client.getOutputStream());
            this.reader = new BufferedReader(this.isr);
            this.writer = new BufferedWriter(this.osr);
        } catch (IOException e){
            System.out.println("Could note open connection to " + client.getInetAddress());
        }

        System.out.println("Connected to " + client);

        try{
            processServerMessage();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Connection lost with client " + client);
        }
    }

    private void processServerMessage() throws IOException {
        ServerMessageFactory factory = new ServerMessageFactory();
        while (true) {
            try {
                String jsonMessage = reader.readLine();
                ServerMessage message = factory.returnMessage(jsonMessage);
                message.process(this);
            } catch (MalformedJsonException e){
                System.out.println("Invalid json object from client");
            }
        }
    }

    private TurnManager getTurnManager(){
        return this.turnManager;
    }
}
