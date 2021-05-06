package it.polimi.ingsw.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Connection {

    protected final Socket socket;
    protected final Scanner scanner;
    protected final DataInputStream fromServer;
    protected final DataOutputStream toServer;
    protected final BufferedReader buffer;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.scanner = new Scanner(System.in);
        this.fromServer = new DataInputStream (socket.getInputStream());
        this.toServer = new DataOutputStream (socket.getOutputStream());
        this.buffer = new BufferedReader(new InputStreamReader(System.in));
    }

    public void closeConnection() throws IOException {
        fromServer.close();
        toServer.close();
        socket.close();
    }
}
