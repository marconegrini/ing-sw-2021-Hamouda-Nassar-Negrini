package it.polimi.ingsw.client.gui;

import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.client.view.CLIView;
import it.polimi.ingsw.client.view.GUIView;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromClient.ExitFromGameMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageFactory;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerHandlerGUI implements Runnable{

    private Socket server;
    private InputStreamReader isr;
    private OutputStreamWriter osw;
    private BufferedReader reader;
    private BufferedWriter writer;
    private PrintWriter out;
    private AtomicBoolean shouldStop = new AtomicBoolean(false);
    private LightModel lightModel;
    private View view;

    public ServerHandlerGUI(Socket server) {
        this.server = server;
        this.lightModel = new LightModel();
        this.view = new GUIView();
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
            //owner.terminate();
            return;
        }

        //System.out.println("Serverhandler on serverHandler:" + this);

        try{
            processServerMessages();
        } catch (IOException e) {
            System.out.println("Server" + server.getInetAddress() + " connection drop");
        }

        try{
            server.close();
        } catch (IOException e){
            System.out.println("Exception occurred while closing client socket");
            //owner.terminate();
        }
    }

    public void processServerMessages() throws IOException{
        ServerMessageFactory factory = new ServerMessageFactory();
        try{
            boolean stop = false;
            while(!stop) {
                //System.out.println("\nWaiting for a json message from server...");
                try {
                    if (reader.ready()) {
                        String jsonMessage = reader.readLine();
                        //System.out.println(jsonMessage);
                        if (jsonMessage != null) {
                            ServerMessage message = factory.returnMessage(jsonMessage);
                            System.out.println(message.toString());
                            System.out.println("\n");
                            //message.clientProcess(this);
                        }
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
        System.out.println("Message:" + toSend + " sent from sendJson");
    }

    public void stop(){
        //System.out.println("Inside stop");

        ExitFromGameMessage exit = new ExitFromGameMessage();
        System.out.println(exit);
        sendJson(exit);

        shouldStop.set(true);
        try {
            server.shutdownInput();
        } catch (IOException ignored) {}
    }

    /**
     * Used inside ServerMessages' clientProcess methods to get user's data and pass it to the view
     * @return light model data
     */
    public LightModel getLightModel(){
        return lightModel;
    }

    /**
     * Used inside ServerMessages' clientProcess methods to get CLI/GUI view methods
     * @return CLI/GUI view methods
     */
    public View getView(){
        return view;
    }

}
