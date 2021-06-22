package it.polimi.ingsw.server.handlers;
import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromClient.ClientMessageFactory;
import it.polimi.ingsw.messages.fromServer.EndGameMessage;
import it.polimi.ingsw.messages.fromServer.ServerLoginMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerPingMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.TurnManager;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * a class used in the SERVER, contains info like socket,Client, readers,writers and View type.
 */
public class ClientHandler extends Thread {

    private final Socket client;
    private InputStreamReader isr;
    private OutputStreamWriter osw;
    private BufferedReader reader;
    private BufferedWriter writer;
    private PrintWriter out;
    private Player player;
    private TurnManager turnManager;
    private String nickname;
    private AtomicBoolean shouldStop = new AtomicBoolean(false);

    /**
     * Class constructor
     * @param clientSocket
     * @throws IOException
     */
    public ClientHandler(Socket clientSocket) throws IOException {
        this.client = clientSocket;
    }

    /**
     * Initializes I/O variables and sets up client's connection. Starts login process and call the method
     * processClientMessages to start receiving client's messages.
     */
    @Override
    public void run(){
        try{
            this.isr = new InputStreamReader(client.getInputStream());
            this.osw = new OutputStreamWriter(client.getOutputStream());
            this.reader = new BufferedReader(this.isr);
            this.writer = new BufferedWriter(this.osw);
            out = new PrintWriter(writer, true);
        } catch (IOException e){
            System.out.println("Could not open connection to " + client.getInetAddress());
        }

        System.out.println("-------------");
        System.out.println("Connected to: " + client);
        System.out.println("-------------");
        //pingClient();
        startLogin();
        try{
            processClientMessages();
        } catch (IOException e){
            System.out.println("Client " + client.getInetAddress() + " connection drop");
            this.turnManager.turnDone();
            this.turnManager.setDisconnected();
        }

        try {
            client.close();
        } catch (IOException e) {
            System.out.println("Exception occurred while closing server socket");
        }
    }

    /**
     * Method that, independently from the message type (but it has to be a ClientMessage), receives
     * messages and processes them.
     * @throws IOException
     */
    private void processClientMessages() throws IOException {
        ClientMessageFactory factory = new ClientMessageFactory();
        boolean stop = false;
        while (!stop) {
            try {
                    String jsonMessage = reader.readLine();
                    if(jsonMessage != null) {
                        ClientMessage message = factory.returnMessage(jsonMessage);
                        System.out.println(message);
                        message.serverProcess(this);
                    }
            } catch (MalformedJsonException e) {
                System.out.println("Invalid json object from client");
            }
            if (shouldStop.get())
                stop = true;
        }
    }

    /**
     * Parse a ServerMessage in json format
     * @param message
     */
    public void sendJson(ServerMessage message){
        Gson gson = new Gson();
        String toSend = gson.toJson(message);
        out.println(toSend);
    }

    /**
     * Sends a login message to start the login process with client socket
     */
    public void startLogin(){
        ServerMessage login = new ServerLoginMessage();
        this.sendJson(login);
    }

    /**
     * Used to stop process client messages' while loop
     */
    public void setShouldStop(){
        shouldStop.set(true);
        try {
            client.shutdownInput();
        } catch (IOException ignored) {}
    }

    public void pingClient(){
        ServerMessage ping = new ServerPingMessage();
        this.sendJson(ping);
    }

    public String getNickname(){
        return nickname;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public Player getPlayer(){
        return this.player;
    }

    public void setTurnManager(TurnManager turnManager){
        this.turnManager = turnManager;
    }

    public TurnManager getTurnManager(){
        return this.turnManager;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    /**
     * Invoked when a user exits from game while in waiting room
     */
    public void exitFromGame(){
        System.out.println(nickname + " is exiting form the game");
        sendJson(new EndGameMessage("You exit from the game"));
        Server.removeClientHandler(this);
        setShouldStop();
    }


}
