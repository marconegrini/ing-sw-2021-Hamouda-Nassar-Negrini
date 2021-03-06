package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.ClientGUI;
import javafx.application.Application;

import java.io.IOException;
import java.util.logging.*;

/**
 * Main Client class that launches the CLI game or the GUI one
 */
public class ClientApp {
/*
*   SEVERE (highest)
    WARNING
    INFO
    CONFIG
    FINE
    FINER
    FINEST
* */
    public static void main(String[] args) {

        Logger logger = Logger.getLogger(ClientApp.class.getName());
        try {
            LogManager.getLogManager().readConfiguration(ClientApp.class.getClassLoader().getResourceAsStream("logging.properties"));
        } catch (SecurityException | IOException e1) {
            e1.printStackTrace();
        }

        boolean cliParam = false; // default value


        for (String arg : args) {
            if (arg.equals("--cli") || arg.equals("-c")) {
                cliParam = true;
                break;
            }
        }

        if (cliParam) {
            ClientCLI clientCLI = new ClientCLI();
            clientCLI.run();
        } else {
            Application.launch(ClientGUI.class);
        }
    }
}

