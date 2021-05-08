package it.polimi.ingsw.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocket {

    private final Socket socket;
    private final Scanner scanner;
    private final DataInputStream fromServer;
    private final DataOutputStream toServer;
    private final BufferedReader buffer;


    public ClientSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.scanner = new Scanner(System.in);
        this.fromServer = new DataInputStream (socket.getInputStream());
        this.toServer = new DataOutputStream (socket.getOutputStream());
        this.buffer = new BufferedReader(new InputStreamReader(System.in));
    }

    public Socket getSocket() {
        return socket;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public DataInputStream getFromServer() {
        return fromServer;
    }

    public DataOutputStream getToServer() {
        return toServer;
    }

    public BufferedReader getBuffer() {
        return buffer;
    }

    public void closeConnection() throws IOException {
        fromServer.close();
        toServer.close();
        socket.close();
    }
}
