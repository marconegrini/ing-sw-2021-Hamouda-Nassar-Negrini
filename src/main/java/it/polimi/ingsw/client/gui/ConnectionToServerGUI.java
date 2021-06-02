package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ExitFromGameMessage;
import it.polimi.ingsw.messages.fromClient.LoginMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class ConnectionToServerGUI {

    public TextField nicknameTextField;
    public RadioButton noRadioButton;
    public RadioButton yesRadioButton;
    @FXML
    private Button connectButton;
    @FXML
    private TextField IPTextFiled;

    @FXML
    ToggleGroup multiplayer;

    private ServerHandler serverHandler;

    public void connectToTheServer(ActionEvent actionEvent) throws Exception{
        //System.out.println("Button pressed");
        //System.out.println(IPTextFiled.getCharacters());

        Socket server;
        try {
            server = new Socket(IPTextFiled.getText(), 5056);
        } catch (IOException e){
            System.out.println("Server unreachable");
            return;
        }

        Stage window = (Stage) connectButton.getScene().getWindow();
        SceneManager.setPrimaryStage(window);

        IPTextFiled.clear();
        serverHandler = new ServerHandler(server, false);
        Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
        serverHandlerThread.start();
/*
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/connected.fxml")));

        Stage window = (Stage) connectButton.getScene().getWindow();
        window.setScene(new Scene(root, 727, 395));
        window.setTitle("Master of Renaissance - Connected");
 */
    }

    public void shutDown(){
        System.out.println("Exiting GUI");
        if (serverHandler != null) {
            serverHandler.sendJson(new ExitFromGameMessage());
            serverHandler.stop();
        }
    }


    public void askLogin(ActionEvent actionEvent) {

        if (nicknameTextField.getText().isEmpty() || nicknameTextField.getText().isBlank()){
            return;
        }
        boolean isMultiplayer;
        RadioButton selected = (RadioButton) multiplayer.getSelectedToggle();

        isMultiplayer = selected.getText().equalsIgnoreCase("YES");

        System.out.println("Sending: " + new LoginMessage(nicknameTextField.getText(), isMultiplayer));
        serverHandler.sendJson(new LoginMessage(nicknameTextField.getText(), isMultiplayer));
    }
}
