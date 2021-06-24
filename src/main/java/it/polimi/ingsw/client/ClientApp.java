package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.ClientGUI;
import javafx.application.Application;

public class ClientApp {
    public static void main(String[] args) {

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
