package it.polimi.ingsw.server.handlers;

import it.polimi.ingsw.model.exceptions.MaxPlayersException;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.TemporaryPlayer;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class ClientHandler extends Thread {

    private final Socket clientSocket;
    private InputStreamReader isr;
    private OutputStreamWriter osr;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String nickname;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run(){
        try{
            this.isr = new InputStreamReader(clientSocket.getInputStream());
            this.osr = new OutputStreamWriter(clientSocket.getOutputStream());
            this.reader = new BufferedReader(this.isr);
            this.writer = new BufferedWriter(this.osr);
        } catch (IOException e){
            System.out.println("Could note open connection to " + clientSocket.getInetAddress());
        }

        System.out.println("Connected to " + clientSocket);

        try{

        }

    }

    private void processServerMessage() throws IOException{
        try{
            while(true){

            }
        }
    }
}
