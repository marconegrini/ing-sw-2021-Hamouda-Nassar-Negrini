package it.polimi.ingsw.client;

import it.polimi.ingsw.client.CLI.LeaderCardsTracer;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.enumerations.ASCII_DV_Cards;
import it.polimi.ingsw.enumerations.ASCII_Resources;
import javafx.application.Application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.*;
import java.util.Arrays;

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
            LogManager.getLogManager().readConfiguration(new FileInputStream("src/main/java/it/polimi/ingsw/Logger/logging.properties"));
        } catch (SecurityException | IOException e1) {
            e1.printStackTrace();
        }
//        logger.setLevel(Level.ALL);

//        consoleHandler.setLevel(Level.FINE); //

            //logging messages
            logger.log(Level.SEVERE, "INFO MGS num:");

            logger.log(Level.CONFIG, "Config data");

//
        boolean cliParam = false; // default value



//        System.out.println(Arrays.toString(ASCII_Resources.values()));
//        System.out.println(Arrays.toString(ASCII_DV_Cards.values()));
//
//        LeaderCardsTracer leaderCardTracer = new LeaderCardsTracer();
//        leaderCardTracer.main();




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

