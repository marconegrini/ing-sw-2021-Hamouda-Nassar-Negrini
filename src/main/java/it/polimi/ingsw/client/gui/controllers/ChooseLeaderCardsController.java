package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.messages.fromClient.SelectLeaderCardMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * GUI controller class used to choose leader cards.
 */
public class ChooseLeaderCardsController {

    @FXML
    public CheckBox selectedFirst, selectedSecond, selectedThird, selectedFourth;

    @FXML
    private Label card1, card2, card3, card4;

    public void selectSlot1(MouseEvent mouseEvent) {
        if (selectedFirst.isSelected()) {
            selectedFirst.setSelected(false);
            card1.getStyleClass().remove("selectedCard");
            card1.getStyleClass().add("notSelectedCard");
        } else {
            if (canSelect()) {

                selectedFirst.setSelected(true);
                card1.getStyleClass().remove("notSelectedCard");
                card1.getStyleClass().add("selectedCard");
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
            card2.getStyleClass().remove("selectedCard");
            card2.getStyleClass().add("notSelectedCard");
        } else {
            if (canSelect()) {
                selectedSecond.setSelected(true);
                card2.getStyleClass().remove("notSelectedCard");
                card2.getStyleClass().add("selectedCard");
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
            card3.getStyleClass().remove("selectedCard");
            card3.getStyleClass().add("notSelectedCard");
        } else {
            if (canSelect()) {
                selectedThird.setSelected(true);
                card3.getStyleClass().remove("notSelectedCard");
                card3.getStyleClass().add("selectedCard");
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
            card4.getStyleClass().remove("selectedCard");
            card4.getStyleClass().add("notSelectedCard");
        } else {
            if (canSelect()) {
                selectedFourth.setSelected(true);
                card4.getStyleClass().remove("notSelectedCard");
                card4.getStyleClass().add("selectedCard");
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
            ControllerGUI.getServerHandler().sendJson(new SelectLeaderCardMessage((slot1-1), (slot2-1)));
        } else
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
