package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.client.gui.SceneManager;
import it.polimi.ingsw.client.view.CLIView;
import it.polimi.ingsw.client.view.GUIView;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageFactory;
import javafx.application.Platform;
import javafx.scene.control.Alert;

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
    private static AtomicBoolean shouldStop = new AtomicBoolean(false);
    private LightModel lightModel;
    private View view;
    private boolean isCli;
    private boolean isMultiplayer= false;

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
            reader = new BufferedReader(isr);
            writer = new BufferedWriter(osw);
            out = new PrintWriter(writer, true);
        } catch (IOException e){
            ClientCLI.logger.log(Level.INFO,"Cannot open connection to " + server);
            return;
        }

        try{
            processServerMessages();
        } catch (IOException e) {
            System.out.println("connection from server lost");
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
     * @throws IOException if the server disconnects
     */
    public void processServerMessages() throws IOException{
        ServerMessageFactory factory = new ServerMessageFactory();
        boolean stop = false;
        while(!stop) {
            try {
                    String jsonMessage = reader.readLine();
                ClientCLI.logger.log(Level.INFO,jsonMessage);
                if(jsonMessage != null) {
                    ServerMessage message = factory.returnMessage(jsonMessage);
                    ClientCLI.logger.log(Level.INFO,message.toString());
                    message.clientProcess(this);
                    }

            } catch (IOException e) {

                    stop = true;

            }
            if (shouldStop.get()) {
                stop = true;
            }
        }

        ClientCLI.logger.log(Level.INFO,"Game ended");

        //This code is to close the window for gui
        if (!isCli){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Server error.");
                alert.setContentText("The server get offline");
                alert.showAndWait();
                SceneManager.getPrimaryStage().hide();
            });
        } else {
            System.exit(0);
        }
    }

    /**
     * used pack a ClientMessage into a json file. Sends it to the server
     * @param message message to parse as json
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

    public void setIsMultiplayer (boolean isMultiplayer){
        this.isMultiplayer = isMultiplayer;
    }

    public static AtomicBoolean getShouldStop() {
        return shouldStop;
    }

    public static void setShouldStop(boolean shouldStop) {
        ServerHandler.shouldStop.set(shouldStop);
    }

    public boolean getIsMultiplayer (){
        return this.isMultiplayer;
    }

}
