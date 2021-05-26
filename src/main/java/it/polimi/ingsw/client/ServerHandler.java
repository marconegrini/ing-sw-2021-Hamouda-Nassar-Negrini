package it.polimi.ingsw.client;

import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;
import it.polimi.ingsw.client.view.CLIView;
import it.polimi.ingsw.client.view.GUIView;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageFactory;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * a class used in the CLIENT, contains info like socket,Client, readers,writers and View type.
 */
public class ServerHandler implements Runnable{

    private Socket server;
    private Client owner;
    private InputStreamReader isr;
    private OutputStreamWriter osw;
    private BufferedReader reader;
    private BufferedWriter writer;
    private PrintWriter out;
    private AtomicBoolean shouldStop = new AtomicBoolean(false);
    private LightModel lightModel;
    private View view;
    private boolean isCli = true;

    public ServerHandler(Socket server, Client owner){
        this.server = server;
        this.owner = owner;
        this.lightModel = new LightModel();
        if(isCli)
            this.view = new CLIView(this.lightModel);
        else this.view = new GUIView();
    }

    @Override
    public void run(){
        try {
            isr = new InputStreamReader(server.getInputStream());
            osw = new OutputStreamWriter(server.getOutputStream());
            reader = new BufferedReader(isr); //ciao
            writer = new BufferedWriter(osw);
            out = new PrintWriter(writer, true);
        } catch (IOException e){
            System.out.println("Cannot open connection to " + server);
            owner.terminate();
            return;
        }

        try{
            processServerMessages();
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

    public void processServerMessages() throws IOException{
        ServerMessageFactory factory = new ServerMessageFactory();
        try{
            boolean stop = false;
            while(!stop) {
                System.out.println("Waiting for json message from server...");
                try {
                        String jsonMessage = reader.readLine();
                        System.out.println(jsonMessage);
                        if(jsonMessage != null) {
                            ServerMessage message = factory.returnMessage(jsonMessage);
                            System.out.println(message.toString());
                            message.clientProcess(this);
                        }
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
                if (shouldStop.get())
                    stop = true;
            }
        } catch (MalformedJsonException e){
            System.out.println("Invalid json object from server");
        }

        System.out.println("Game ended");
    }

    public void sendJson(ClientMessage message){
        Gson gson = new Gson();
        String toSend = gson.toJson(message);
        out.println(toSend);
    }

    public void stop(){
        shouldStop.set(true);
        try {
            server.shutdownInput();
        } catch (IOException ignored) {}
    }

    public View getView(){
        return view;
    }

    public LightModel getLightModel(){
        return lightModel;
    }
}
