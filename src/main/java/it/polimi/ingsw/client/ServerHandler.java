package it.polimi.ingsw.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerHandler extends Thread{

    private Socket server;
    private Client owner;
    private InputStreamReader isr;
    private OutputStreamWriter osr;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String nickname;

    public ServerHandler(Socket server, Client client){
        this.server = server;
        this.owner = owner;
    }
}
