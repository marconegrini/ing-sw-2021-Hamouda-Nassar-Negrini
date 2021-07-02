package it.polimi.ingsw.server.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.MalformedJsonException;
import it.polimi.ingsw.client.ClientCLI;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromClient.ClientMessageFactory;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientPingHandler extends Thread {
    private static final Logger logger = Logger.getLogger(ClientPingHandler.class.getName());
    private final Socket client;
    private InputStreamReader isr;
    private OutputStreamWriter osw;
    private BufferedReader reader;
    private BufferedWriter writer;
    private PrintWriter out;
    private AtomicLong currentTimeMillis = new AtomicLong();
    private AtomicBoolean stop = new AtomicBoolean();
    private ClientHandler owner;

    public ClientPingHandler(Socket clientSocket, ClientHandler owner) throws IOException {
        this.client = clientSocket;
        this.owner = owner;
    }


    public void run() {
        try {
            this.isr = new InputStreamReader(client.getInputStream());
            this.osw = new OutputStreamWriter(client.getOutputStream());
            this.reader = new BufferedReader(this.isr);
            this.writer = new BufferedWriter(this.osw);
            out = new PrintWriter(writer, true);
            this.currentTimeMillis.set(System.currentTimeMillis());
            this.stop.set(false);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not open connection to " + client.getInetAddress());
        }

        String clientid = String.valueOf(this.client.getPort());

        logger.log(Level.INFO, clientid);

        logger.log(Level.INFO, "-------PING HANDLER------");
        logger.log(Level.INFO, "Connected to clientid : " + clientid);
        logger.log(Level.INFO, "-------------");

        ClientMessageFactory factory = new ClientMessageFactory();


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                while (!stop.get()) {
                    try {
                        String jsonMessage = reader.readLine();
                        if (jsonMessage != null) {
                            JsonElement json = gson.fromJson(jsonMessage, JsonElement.class);
                            JsonObject messageObject = json.getAsJsonObject();
                            String messageTypeString = messageObject.get("type").getAsString();

                            if (messageTypeString.equals("PING")) {
                                logger.log(Level.INFO, "Received PING Message from clientid : "+ clientid);
                                currentTimeMillis.set(System.currentTimeMillis());
                            }
//                    message.serverProcess(this);
                        }
                    } catch (IOException e) {
//                        logger.log(Level.INFO, "Invalid json object from client");
//                        System.out.println("Client disconnected - via Exception");
//                        stop.set(true);
                    }

//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();

        while (true) {
            try {
                Thread.sleep(7000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            if (System.currentTimeMillis() - currentTimeMillis.get() > 15000) {
                System.out.println("Client disconnected - via Timer! , clientid =" + clientid);
                //ServerHandler.setShouldStop(true);
                t.interrupt();
                owner.getTurnManager().turnDone();
                owner.getTurnManager().setDisconnected();
                break;
            }
        }

        try {
            client.close();
        } catch (IOException e) {
            ClientCLI.logger.log(Level.INFO, "Exception occurred while closing client socket , clientid = " + clientid);
        }

    logger.log(Level.INFO,"end run of Client ping Handler - exit class , clientid = " + clientid);
    }

}
