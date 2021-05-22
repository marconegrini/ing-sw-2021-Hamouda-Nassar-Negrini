package it.polimi.ingsw.client;

import com.google.gson.Gson;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.stream.MalformedJsonException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromClient.ClientMessageFactory;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageFactory;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerHandler implements Runnable{

    private Socket server;
    private Client owner;
    private InputStreamReader isr;
    private OutputStreamWriter osw;
    private BufferedReader reader;
    private BufferedWriter writer;
    private PrintWriter out;
    private AtomicBoolean shouldStop = new AtomicBoolean(false);

    public ServerHandler(Socket server, Client owner){
        this.server = server;
        this.owner = owner;
    }

    @Override
    public void run(){
        try {
            isr = new InputStreamReader(server.getInputStream());
            osw = new OutputStreamWriter(server.getOutputStream());
            reader = new BufferedReader(isr);
            writer = new BufferedWriter(osw);
            out = new PrintWriter(writer, true);
        } catch (IOException e){
            System.out.println("Cannot open connection to " + server);
            owner.terminate();
            return;
        }

        try{
            processClientMessages();
        } catch (IOException e) {
            System.out.println("Server" + server.getInetAddress() + " connection drop");
        }

        try{
            server.close();
        } catch (IOException e){
                System.out.println("Exception occurred while closing client socket");
            owner.terminate();
        }
    }

    public void processClientMessages() throws IOException{
        ServerMessageFactory factory = new ServerMessageFactory();
        try{
            boolean stop = false;
            while(!stop) {
                try {
                    String jsonMessage = reader.readLine();
                    ServerMessage message = factory.returnMessage(jsonMessage);
                    message.clientProcess(this);

                } catch (IOException e) {
                    /* Check if we were interrupted because another thread has asked us to stop */
                    if (shouldStop.get()) {
                        /* Yes, exit the loop gracefully */
                        stop = true;
                    } else {
                        /* No, rethrow the exception */
                        throw e;
                    }
                }
            }
        } catch (MalformedJsonException e){
            System.out.println("Invalid json object from server");
        }
    }

    public void sendJson(ClientMessage message){
        Gson gson = new Gson();
        String toSend = gson.toJson(message);
        //try{
        out.println(toSend);
        //} catch(IOException e){
        //    System.out.println("Communication error");
        //    owner.terminate();
        //}
    }

    public void stop()
    {
        shouldStop.set(true);
        try {
            server.shutdownInput();
        } catch (IOException e) { }
    }

}
