package it.polimi.ingsw.client;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 5056);

        ServerConnection serverConnection = new ServerConnection(socket);

        GameSetUpConnection gameSetUp = new GameSetUpConnection(socket);

    }
}


