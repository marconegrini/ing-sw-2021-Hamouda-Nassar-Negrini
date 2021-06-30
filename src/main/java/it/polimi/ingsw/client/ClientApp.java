package it.polimi.ingsw.client;

import it.polimi.ingsw.client.CLI.LeaderCardsTracer;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.enumerations.ASCII_DV_Cards;
import it.polimi.ingsw.enumerations.ASCII_Resources;
import javafx.application.Application;

import java.util.Arrays;

public class ClientApp {
    public static void main(String[] args) {

        boolean cliParam = false; // default value



        System.out.println(Arrays.toString(ASCII_Resources.values()));
        System.out.println(Arrays.toString(ASCII_DV_Cards.values()));

        LeaderCardsTracer leaderCardTracer = new LeaderCardsTracer();
        leaderCardTracer.main();




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

