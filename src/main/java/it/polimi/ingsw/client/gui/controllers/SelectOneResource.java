package it.polimi.ingsw.client.gui.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class SelectOneResource {


    public Label servant;
    public Label coin;
    public Label shield;
    public Label stone;
    public GridPane warehouse;
    public ToggleGroup resources;
    public RadioButton servantRadio;
    public RadioButton coinRadio;
    public RadioButton stoneRadio;
    public RadioButton shieldRadio;

    public void continueToGameCalamaio(ActionEvent actionEvent) {
    }

    public void ContinueToGame(ActionEvent actionEvent) {
    }

    public void selectSlot(MouseEvent mouseEvent) {

        if (!isSelectedResource()){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You should select a resource before selecting a shelf.");
                alert.showAndWait();
            });
            return;
        }

        System.out.println(GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode()) + " " + GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode()));

    }

    private boolean isSelectedResource(){
        if (resources == null)  return false;
        return ((RadioButton)resources.getSelectedToggle()).getText() != null;
    }

    public void selectServant(ActionEvent actionEvent) {
        servantRadio.setSelected(true);
        servant.getStyleClass().remove("notSelectedCard");
        servant.getStyleClass().add("selectedCard");
        disSelectResources(servant);
    }

    public void selectShield(ActionEvent actionEvent) {
        shieldRadio.setSelected(true);
        shield.getStyleClass().remove("notSelectedCard");
        shield.getStyleClass().add("selectedCard");
        disSelectResources(shield);
    }

    public void selectStone(ActionEvent actionEvent) {
        stoneRadio.setSelected(true);
        stone.getStyleClass().remove("notSelectedCard");
        stone.getStyleClass().add("selectedCard");
        disSelectResources(stone);
    }

    public void selectCoin(ActionEvent actionEvent) {
        coinRadio.setSelected(true);
        coin.getStyleClass().remove("notSelectedCard");
        coin.getStyleClass().add("selectedCard");
        disSelectResources(coin);
    }

    private void disSelectResources(Label label){
        if (!label.equals(coin)){
            coin.getStyleClass().remove("selected");
            coin.getStyleClass().add("notSelectedCard");
        }
        if (!label.equals(stone)){
            stone.getStyleClass().remove("selected");
            stone.getStyleClass().add("notSelectedCard");
        }
        if (!label.equals(servant)){
            servant.getStyleClass().remove("selected");
            servant.getStyleClass().add("notSelectedCard");
        }
        if (!label.equals(shield)){
            shield.getStyleClass().remove("selected");
            shield.getStyleClass().add("notSelectedCard");
        }
    }
}
