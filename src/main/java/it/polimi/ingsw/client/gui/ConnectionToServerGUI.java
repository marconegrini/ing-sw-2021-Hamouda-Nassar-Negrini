package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.AskStartGameMessage;
import it.polimi.ingsw.messages.fromClient.ExitFromGameMessage;
import it.polimi.ingsw.messages.fromClient.LoginMessage;
import it.polimi.ingsw.messages.fromClient.SelectLeaderCardMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.Socket;


public class ConnectionToServerGUI {

    @FXML
    public CheckBox selectedFirst, selectedSecond, selectedThird, selectedFourth;
    @FXML
    private TextField nicknameTextField;
    @FXML
    private RadioButton noRadioButton;
    @FXML
    private RadioButton yesRadioButton;
    private Button startGameButton;
    @FXML
    private Label playersNumber;
    @FXML
    private Label secondCard, firstCard, fourthCard, thirdCard;
    @FXML
    private Button connectButton;
    @FXML
    private TextField IPTextFiled;

    @FXML
    private ToggleGroup multiplayer;

    private static ServerHandler serverHandler;

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
        serverHandler = new ServerHandler(server, false);
        Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
        serverHandlerThread.start();

    }

    public void shutDown() {
        System.out.println("Exiting GUI");
        if (serverHandler != null) {
            serverHandler.sendJson(new ExitFromGameMessage());
        }
    }


    public void askLogin(ActionEvent actionEvent) {

        if (nicknameTextField.getText().isEmpty() || nicknameTextField.getText().isBlank()) {
            return;
        }
        boolean isMultiplayer;
        RadioButton selected = (RadioButton) multiplayer.getSelectedToggle();
        isMultiplayer = selected.getText().equalsIgnoreCase("YES");
        serverHandler.sendJson(new LoginMessage(nicknameTextField.getText(), isMultiplayer));
    }

    public void startGame(ActionEvent actionEvent) {

        serverHandler.sendJson(new AskStartGameMessage());
    }


    public void selectSlot1(MouseEvent mouseEvent) {
        if (selectedFirst.isSelected()) {
            selectedFirst.setSelected(false);
            firstCard.getStyleClass().remove("selectedCard");
            firstCard.getStyleClass().add("notSelectedCard");
        } else {
            if (canSelect()) {

                selectedFirst.setSelected(true);
                firstCard.getStyleClass().remove("notSelectedCard");
                firstCard.getStyleClass().add("selectedCard");
            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Error: Leader card selection");
                    alert.setContentText("You can select only two leader cards.");
                    alert.showAndWait();
                });
            }
        }
    }

    public void selectSlot2(MouseEvent mouseEvent) {
        //System.out.println("Inside select slot 2 with bool: " + selectedSecond.isSelected());
        if (selectedSecond.isSelected()) {
            selectedSecond.setSelected(false);
            secondCard.getStyleClass().remove("selectedCard");
            secondCard.getStyleClass().add("notSelectedCard");
        } else {
            if (canSelect()) {
                selectedSecond.setSelected(true);
                secondCard.getStyleClass().remove("notSelectedCard");
                secondCard.getStyleClass().add("selectedCard");
            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Error: Leader card selection");
                    alert.setContentText("You can select only two leader cards.");
                    alert.showAndWait();
                });
            }
        }
    }

    public void selectSlot3(MouseEvent mouseEvent) {
        if (selectedThird.isSelected()) {
            selectedThird.setSelected(false);
            thirdCard.getStyleClass().remove("selectedCard");
            thirdCard.getStyleClass().add("notSelectedCard");
        } else {
            if (canSelect()) {
                selectedThird.setSelected(true);
                thirdCard.getStyleClass().remove("notSelectedCard");
                thirdCard.getStyleClass().add("selectedCard");
            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Error: Leader card selection");
                    alert.setContentText("You can select only two leader cards.");
                    alert.showAndWait();
                });
            }
        }
    }

    public void selectSlot4(MouseEvent mouseEvent) {
        if (selectedFourth.isSelected()) {
            selectedFourth.setSelected(false);
            fourthCard.getStyleClass().remove("selectedCard");
            fourthCard.getStyleClass().add("notSelectedCard");
        } else {
            if (canSelect()) {
                selectedFourth.setSelected(true);
                fourthCard.getStyleClass().remove("notSelectedCard");
                fourthCard.getStyleClass().add("selectedCard");
            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Error: Leader card selection");
                    alert.setContentText("You can select only two leader cards.");
                    alert.showAndWait();
                });
            }
        }
    }


    public void sendLeaderCard(ActionEvent actionEvent) {

        if (!canSelect()) {
            int slot1 = 0, slot2 = 0;

            if (selectedFirst.isSelected()) slot1 = 1;
            if (selectedSecond.isSelected()) if (slot1 == 0) slot1 = 2;
            else slot2 = 2;
            if (selectedThird.isSelected()) if (slot1 == 0) slot1 = 3;
            else slot2 = 3;
            if (selectedFourth.isSelected()) if (slot1 == 0) slot1 = 4;
            else slot2 = 4;

            System.out.println("Selected cards: " + slot1 + " " + slot2);
            serverHandler.sendJson(new SelectLeaderCardMessage(slot1, slot1));
            return;
        }

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText("Error: Leader card selection");
            alert.setContentText("You should select two leader cards.");
            alert.showAndWait();
        });
    }

    private boolean canSelect() {
        int count = 0;
        if (selectedFourth.isSelected()) count++;
        if (selectedThird.isSelected()) count++;
        if (selectedSecond.isSelected()) count++;
        if (selectedFirst.isSelected()) count++;
        if (count < 2) return true;
        else return false;
    }
}
