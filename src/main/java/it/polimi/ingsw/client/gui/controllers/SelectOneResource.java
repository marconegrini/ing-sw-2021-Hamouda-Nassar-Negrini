package it.polimi.ingsw.client.gui.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class SelectOneResource {

    @FXML
    private Label servant, coin ,shield, stone;
    @FXML
    private GridPane warehouse;
    @FXML
    private ToggleGroup resources;
    @FXML
    private ToggleGroup multiplayer;
    @FXML
    private RadioButton selected;
    @FXML
    private RadioButton servantRadio, coinRadio, stoneRadio, shieldRadio;

    public void continueToGameCalamaio(ActionEvent actionEvent) {
    }

    public void ContinueToGame(ActionEvent actionEvent) {
        System.out.println("Toggle: " + resources);
    }

    public void selectSlot(MouseEvent mouseEvent) {
        System.out.println(" toggle before: " + resources);

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
        if (selected == null) {
            //System.out.println("Return null. resources: " + resources + " toggle text: " + ((RadioButton)resources.getSelectedToggle()).getText());
            return false;
        }
        selected = (RadioButton) resources.getSelectedToggle();
        System.out.println(" toggle text: " + selected.getText());
        return selected.getText() != null;
    }

    public void selectServant(MouseEvent actionEvent) {
        servantRadio.setSelected(true);
        servant.getStyleClass().remove("notSelectedCard");
        servant.getStyleClass().add("selectedCard");
        disSelectResources(servant);
        selected = (RadioButton) resources.getSelectedToggle();
    }

    public void selectShield(MouseEvent actionEvent) {
        shieldRadio.setSelected(true);
        shield.getStyleClass().remove("notSelectedCard");
        shield.getStyleClass().add("selectedCard");
        disSelectResources(shield);
        selected = (RadioButton) resources.getSelectedToggle();

    }

    public void selectStone(MouseEvent actionEvent) {
        stoneRadio.setSelected(true);
        stone.getStyleClass().remove("notSelectedCard");
        stone.getStyleClass().add("selectedCard");
        disSelectResources(stone);
        selected = (RadioButton)resources.getSelectedToggle();
    }

    public void selectCoin(MouseEvent actionEvent) {
        coinRadio.setSelected(true);
        coin.getStyleClass().remove("notSelectedCard");
        coin.getStyleClass().add("selectedCard");
        disSelectResources(coin);
        selected = (RadioButton)resources.getSelectedToggle();
    }

    private void disSelectResources(Label label){
        //selected = (RadioButton)resources.getSelectedToggle();
        if (!label.equals(coin)){
            coin.getStyleClass().remove("selectedCard");
            coin.getStyleClass().add("notSelectedCard");
            //System.out.println("disselected Coin");
           // System.out.println(" toggle text: " + selected.getText());
        }
        if (!label.equals(stone)){
            stone.getStyleClass().remove("selectedCard");
            stone.getStyleClass().add("notSelectedCard");
            //System.out.println("disselected Stone");
            //System.out.println(" toggle text: " + selected.getText());
        }
        if (!label.equals(servant)){
            servant.getStyleClass().remove("selectedCard");
            servant.getStyleClass().add("notSelectedCard");
            //System.out.println("disselected Servant");
            //System.out.println(" toggle text: " + selected.getText());
        }
        if (!label.equals(shield)){
            shield.getStyleClass().remove("selectedCard");
            shield.getStyleClass().add("notSelectedCard");
            //System.out.println("disselected Shield");
            //System.out.println(" toggle text: " + selected.getText());
        }
    }
}
