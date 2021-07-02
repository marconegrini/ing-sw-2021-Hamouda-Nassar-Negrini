package it.polimi.ingsw.client.gui.controllers.login;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.ServerPingSender;
import it.polimi.ingsw.client.gui.controllers.ControllerGUI;
import it.polimi.ingsw.messages.fromClient.ExitFromGameMessage;
import it.polimi.ingsw.messages.fromClient.SelectLeaderCardMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.Socket;

/**
 * GUI controller class invoked to set a connection with server
 */
public class ConnectionToServerController {


    @FXML
    private TextField IPTextFiled;



    /**
     * Invoked when the connect button is pressed. Tries to start the connection with the server
     * @param actionEvent
     * @throws Exception
     */
    public void connectToTheServer(ActionEvent actionEvent) throws Exception {

        if (IPTextFiled.getText().isBlank() || IPTextFiled.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Server unreachable.");
            alert.setContentText("Be sure that you wrote a correct IP address or try to change the server.");
            alert.showAndWait();
            return;
        }
        Socket server;
        try {
            server = new Socket(IPTextFiled.getText(), 5056);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Server unreachable.");
            alert.setContentText("Be sure that you wrote a correct IP address or try to change the server.");
            alert.showAndWait();
            return;
        }

        Socket serverPingSocket;
        try {
            serverPingSocket = new Socket(IPTextFiled.getText(), 5070);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Server unreachable.");
            alert.setContentText("Be sure that you wrote a correct IP address or try to change the server.");
            alert.showAndWait();
            return;
        }


        IPTextFiled.clear();
        ServerHandler serverHandler = new ServerHandler(server, false);
        ControllerGUI.setServerHandler(serverHandler);
        Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
        serverHandlerThread.start();

        ServerPingSender serverPingSender = new ServerPingSender(serverPingSocket, serverHandler);
        Thread serverPingSenderThread = new Thread(serverPingSender);
        serverPingSenderThread.start();
    }

    /**
     * Manage the closing of the window. If a user closes the window, it sent a ExitFromGameMessage to the server
     */
    public void shutDown() {
        if (ControllerGUI.getServerHandler() != null) {
            ControllerGUI.getServerHandler().sendJson(new ExitFromGameMessage());
        }
    }


}
