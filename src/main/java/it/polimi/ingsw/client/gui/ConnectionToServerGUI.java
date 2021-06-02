package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ExitFromGameMessage;
import it.polimi.ingsw.messages.fromClient.LoginMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class ConnectionToServerGUI {

    public TextField nicknameTextField;
    public RadioButton noRadioButton;
    public RadioButton yesRadioButton;
    public Button startGameButton;
    @FXML
    public Label playersNumber;
    @FXML
    private Button connectButton;
    @FXML
    private TextField IPTextFiled;

    @FXML
    ToggleGroup multiplayer;

    private static ServerHandler serverHandler;

    public void connectToTheServer(ActionEvent actionEvent) throws Exception{

        if (IPTextFiled.getText().isBlank() || IPTextFiled.getText().isEmpty()){
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
        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Server unreachable.");
            alert.setContentText("Be sure that you wrote a correct IP address or try to change the server.");
            alert.showAndWait();
            return;
        }


        IPTextFiled.clear();
        serverHandler = new ServerHandler(server, false);
        Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
        serverHandlerThread.start();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/login/loginInformation.fxml")));

        SceneManager.setScene(new Scene(root, 800, 500));
    }

    public void shutDown(){
        System.out.println("Exiting GUI");
        if (serverHandler != null) {
            serverHandler.sendJson(new ExitFromGameMessage());
            //serverHandler.stop();
        }
    }


    public void askLogin(ActionEvent actionEvent) {

        if (nicknameTextField.getText().isEmpty() || nicknameTextField.getText().isBlank()){
            return;
        }
        boolean isMultiplayer;
        RadioButton selected = (RadioButton) multiplayer.getSelectedToggle();
        isMultiplayer = selected.getText().equalsIgnoreCase("YES");
        serverHandler.sendJson(new LoginMessage(nicknameTextField.getText(), isMultiplayer));
    }

    public void startGame(ActionEvent actionEvent) {

        Stage window = (Stage) playersNumber.getScene().getWindow();
        Label participantsNumber = (Label) window.getScene().lookup("#playersNumber");
        System.out.println("Scene in button:" + SceneManager.getScene() + " stage: " + window);
    }
}
