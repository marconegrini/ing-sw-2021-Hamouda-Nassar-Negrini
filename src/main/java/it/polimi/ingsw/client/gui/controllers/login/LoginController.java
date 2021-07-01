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

/**
 * GUI controller invoked during the login of the user
 */
public class LoginController {

    @FXML
    private TextField nicknameTextField;
    @FXML
    private ToggleGroup multiplayer;

    /**
     * Invoked when the continue button is pressed. This method check if the nickname is valid and send the LoginMessage to the server
     * @param actionEvent
     */
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
        ControllerGUI.getServerHandler().setIsMultiplayer(isMultiplayer);
    }

    /**
     * Invoked when the start game button is pressed. Try to start the game. The game will start only of there are at least two players in the waiting room
     * @param actionEvent
     */
    public void startGame(ActionEvent actionEvent) {
        ControllerGUI.getServerHandler().sendJson(new AskStartGameMessage());
    }

}
