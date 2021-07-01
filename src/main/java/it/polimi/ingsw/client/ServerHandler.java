package it.polimi.ingsw.client;

import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;
import it.polimi.ingsw.client.view.CLIView;
import it.polimi.ingsw.client.view.GUIView;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.enumerations.ANSITextFormat;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageFactory;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

/**
 * Class handled by CLIENT thread. Contains references to all user's required classes, such as
 * Socket, LightModel, View, and IO classes.
 */
public class ServerHandler implements Runnable{

    private Socket server;
    private InputStreamReader isr;
    private OutputStreamWriter osw;
    private BufferedReader reader;
    private BufferedWriter writer;
    private PrintWriter out;
    private AtomicBoolean shouldStop = new AtomicBoolean(false);
    private LightModel lightModel;
    private View view;
    private boolean isCli;

    public ServerHandler(Socket server, boolean isCli){
        this.server = server;
        this.isCli = isCli;
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
            ClientCLI.logger.log(Level.INFO,"Cannot open connection to " + server);
            return;
        }

        try{
            processServerMessages();
        } catch (IOException e) {
            ClientCLI.logger.log(Level.INFO,"Server" + server.getInetAddress() + " connection drop");
        }

        try{
            server.close();
        } catch (IOException e){
            ClientCLI.logger.log(Level.INFO,"Exception occurred while closing client socket");
        }
    }

    /**
     * receive server messages and processes them, unconditionally form the message type
     * @throws IOException
     */
    public void processServerMessages() throws IOException{
        ServerMessageFactory factory = new ServerMessageFactory();
        try{
            boolean stop = false;
            while(!stop) {
                System.out.println(ANSITextFormat.ITALIC +"\nWaiting for a json message from server..."+ANSITextFormat.RESET + "\n");
                try {
                        String jsonMessage = reader.readLine();
                    ClientCLI.logger.log(Level.INFO,jsonMessage);
                    if(jsonMessage != null) {
                        ServerMessage message = factory.returnMessage(jsonMessage);
                        ClientCLI.logger.log(Level.INFO,message.toString());
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
                if (shouldStop.get()) {
                    stop = true;
                    //this.getView().closeScanner();
                }
            }
        } catch (MalformedJsonException e){
            System.out.println("Invalid json object from server");
        }

        System.out.println("Game ended");
    }

    /**
     * used pack a ClientMessage into a json file. Sends it to the server
     * @param message
     */
    public void sendJson(ClientMessage message){
        Gson gson = new Gson();
        String toSend = gson.toJson(message);
        out.println(toSend);
    }

    /**
     * invoked in EndGameMessage to stop processServerMessages method. Only used at the end of the game
     */
    public void stop(){
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

    public boolean getIsCli(){
        return isCli;
    }



}
