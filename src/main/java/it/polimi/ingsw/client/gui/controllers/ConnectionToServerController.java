package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ExitFromGameMessage;
import it.polimi.ingsw.messages.fromClient.SelectLeaderCardMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.Socket;


public class ConnectionToServerController {


    @FXML
    private TextField IPTextFiled;


    //private static ServerHandler serverHandler;

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


        IPTextFiled.clear();
        ServerHandler serverHandler = new ServerHandler(server, false);
        ControllerGUI.setServerHandler(serverHandler);
        Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
        serverHandlerThread.start();

    }

    public void shutDown() {
        System.out.println("Exiting GUI");
        if (ControllerGUI.getServerHandler() != null) {
            ControllerGUI.getServerHandler().sendJson(new ExitFromGameMessage());
        }
    }


}
