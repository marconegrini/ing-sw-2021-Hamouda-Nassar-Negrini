package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.messages.fromClient.AskStartGameMessage;
import it.polimi.ingsw.messages.fromClient.LoginMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class LoginController {

    @FXML
    private TextField nicknameTextField;
    @FXML
    private ToggleGroup multiplayer;

    public void askLogin(ActionEvent actionEvent) {

        if (nicknameTextField.getText().isEmpty() || nicknameTextField.getText().isBlank()) {
            return;
        }
        boolean isMultiplayer;
        RadioButton selected = (RadioButton) multiplayer.getSelectedToggle();
        isMultiplayer = selected.getText().equalsIgnoreCase("YES");
        ControllerGUI.getServerHandler().sendJson(new LoginMessage(nicknameTextField.getText(), isMultiplayer));
    }

    public void startGame(ActionEvent actionEvent) {

        ControllerGUI.getServerHandler().sendJson(new AskStartGameMessage());
    }

}
