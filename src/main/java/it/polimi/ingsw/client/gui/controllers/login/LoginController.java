package it.polimi.ingsw.client.gui.controllers.login;

import it.polimi.ingsw.client.gui.controllers.ControllerGUI;
import it.polimi.ingsw.messages.fromClient.AskStartGameMessage;
import it.polimi.ingsw.messages.fromClient.LoginMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: nickname");
                alert.setContentText("You can't insert a blank nickname.");
                alert.showAndWait();
            });
            return;
        }
        boolean isMultiplayer;
        RadioButton selected = (RadioButton) multiplayer.getSelectedToggle();

        if (selected == null){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Game modality");
                alert.setContentText("You should select a game modality.");
                alert.showAndWait();
            });
            return;
        }

        isMultiplayer = selected.getText().equalsIgnoreCase("YES");
        ControllerGUI.getServerHandler().sendJson(new LoginMessage(nicknameTextField.getText(), isMultiplayer));
    }

    public void startGame(ActionEvent actionEvent) {

        ControllerGUI.getServerHandler().sendJson(new AskStartGameMessage());
    }

}
