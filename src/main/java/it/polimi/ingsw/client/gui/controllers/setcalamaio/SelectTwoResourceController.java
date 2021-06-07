package it.polimi.ingsw.client.gui.controllers.setcalamaio;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class SelectTwoResourceController {

    @FXML
    private RadioButton stoneRadio2, shieldRadio2, servantRadio2, coinRadio2;
    @FXML
    private ToggleGroup resource2, resource1;
    @FXML
    private Label stone2, shield2, coin2 ,servant2;
    private Label selectedLabel1, selectedLabel2;
    @FXML
    private GridPane firstShelf, secondShelf, thirdShelf;
    @FXML
    private RadioButton shieldRadio1, stoneRadio1, coinRadio1, servantRadio1;
    private RadioButton selected1, selected2;
    @FXML
    private Label stone1, shield1,  coin1, servant1;
    boolean insertedResource1 = false, insertedResource2 = false;

    public void ContinueToGame(ActionEvent actionEvent) {
    }

    public void selectFirstShelf(MouseEvent mouseEvent) {
        if (!isSelectedResource1() && !isSelectedResource2()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You should select a resource before selecting a shelf.");
                alert.showAndWait();
            });
            return;
        }

        if (isSelectedResource1()) {
            Label toPut = new Label();
            toPut.setLayoutX(selectedLabel1.getLayoutX());
            toPut.setLayoutY(selectedLabel1.getLayoutY());
            toPut.setPrefHeight(selectedLabel1.getPrefHeight());
            toPut.setPrefWidth(selectedLabel1.getPrefWidth());
            toPut.getStyleClass().add(getResource(selectedLabel1));
            toPut.getStyleClass().add("notSelectedCard");
            //System.out.println("getResource: " + getResource(selectedLabel));
            //System.out.println("selectedLabel: " + String.valueOf(selectedLabel.getStyleClass()) + " toPut: " + toPut.getStyleClass());
            Integer column = GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode());
            Integer row = GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode());
            if (column == null) column = 0;
            if (row == null) row = 0;
            firstShelf.add(toPut, column, row);
            insertedResource1 = true;
            disSelectResources1(null);
            selected1.setSelected(false);
            return;
        }

        if (isSelectedResource2()) {
            Label toPut = new Label();
            toPut.setLayoutX(selectedLabel2.getLayoutX());
            toPut.setLayoutY(selectedLabel2.getLayoutY());
            toPut.setPrefHeight(selectedLabel2.getPrefHeight());
            toPut.setPrefWidth(selectedLabel2.getPrefWidth());
            toPut.getStyleClass().add(getResource(selectedLabel2));
            toPut.getStyleClass().add("notSelectedCard");
            //System.out.println("getResource: " + getResource(selectedLabel));
            //System.out.println("selectedLabel: " + String.valueOf(selectedLabel.getStyleClass()) + " toPut: " + toPut.getStyleClass());
            Integer column = GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode());
            Integer row = GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode());
            if (column == null) column = 0;
            if (row == null) row = 0;
            firstShelf.add(toPut, column, row);
            insertedResource2 = true;
            disSelectResources2(null);
            selected2.setSelected(false);
        }
    }

    public void selectSecondShelf(MouseEvent mouseEvent) {
        if (!isSelectedResource1() && !isSelectedResource2()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You should select a resource before selecting a shelf.");
                alert.showAndWait();
            });
            return;
        }

        if (isSelectedResource1()) {
            Label toPut = new Label();
            toPut.setLayoutX(selectedLabel1.getLayoutX());
            toPut.setLayoutY(selectedLabel1.getLayoutY());
            toPut.setPrefHeight(selectedLabel1.getPrefHeight());
            toPut.setPrefWidth(selectedLabel1.getPrefWidth());
            toPut.getStyleClass().add(getResource(selectedLabel1));
            toPut.getStyleClass().add("notSelectedCard");
            //System.out.println("getResource: " + getResource(selectedLabel));
            //System.out.println("selectedLabel: " + String.valueOf(selectedLabel.getStyleClass()) + " toPut: " + toPut.getStyleClass());
            Integer column = GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode());
            Integer row = GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode());
            if (column == null) column = 0;
            if (row == null) row = 0;
            secondShelf.add(toPut, column, row);
            insertedResource1 = true;
            disSelectResources1(null);
            selected1.setSelected(false);
            return;
        }

        if (isSelectedResource2()) {
            Label toPut = new Label();
            toPut.setLayoutX(selectedLabel2.getLayoutX());
            toPut.setLayoutY(selectedLabel2.getLayoutY());
            toPut.setPrefHeight(selectedLabel2.getPrefHeight());
            toPut.setPrefWidth(selectedLabel2.getPrefWidth());
            toPut.getStyleClass().add(getResource(selectedLabel2));
            toPut.getStyleClass().add("notSelectedCard");
            //System.out.println("getResource: " + getResource(selectedLabel));
            //System.out.println("selectedLabel: " + String.valueOf(selectedLabel.getStyleClass()) + " toPut: " + toPut.getStyleClass());
            Integer column = GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode());
            Integer row = GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode());
            if (column == null) column = 0;
            if (row == null) row = 0;
            secondShelf.add(toPut, column, row);
            insertedResource2 = true;
            disSelectResources2(null);
            selected2.setSelected(false);
        }


    }

    public void selectThirdShelf(MouseEvent mouseEvent) {
        if (!isSelectedResource1() && !isSelectedResource2()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You should select a resource before selecting a shelf.");
                alert.showAndWait();
            });
            return;
        }

        if (isSelectedResource1()) {
            Label toPut = new Label();
            toPut.setLayoutX(selectedLabel1.getLayoutX());
            toPut.setLayoutY(selectedLabel1.getLayoutY());
            toPut.setPrefHeight(selectedLabel1.getPrefHeight());
            toPut.setPrefWidth(selectedLabel1.getPrefWidth());
            toPut.getStyleClass().add(getResource(selectedLabel1));
            toPut.getStyleClass().add("notSelectedCard");
            //System.out.println("getResource: " + getResource(selectedLabel));
            //System.out.println("selectedLabel: " + String.valueOf(selectedLabel.getStyleClass()) + " toPut: " + toPut.getStyleClass());
            Integer column = GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode());
            Integer row = GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode());
            if (column == null) column = 0;
            if (row == null) row = 0;
            thirdShelf.add(toPut, column, row);
            insertedResource1 = true;
            disSelectResources1(null);
            selected1.setSelected(false);
            return;
        }

        if (isSelectedResource2()) {
            Label toPut = new Label();
            toPut.setLayoutX(selectedLabel2.getLayoutX());
            toPut.setLayoutY(selectedLabel2.getLayoutY());
            toPut.setPrefHeight(selectedLabel2.getPrefHeight());
            toPut.setPrefWidth(selectedLabel2.getPrefWidth());
            toPut.getStyleClass().add(getResource(selectedLabel2));
            toPut.getStyleClass().add("notSelectedCard");
            //System.out.println("getResource: " + getResource(selectedLabel));
            //System.out.println("selectedLabel: " + String.valueOf(selectedLabel.getStyleClass()) + " toPut: " + toPut.getStyleClass());
            Integer column = GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode());
            Integer row = GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode());
            if (column == null) column = 0;
            if (row == null) row = 0;
            thirdShelf.add(toPut, column, row);
            insertedResource2 = true;
            disSelectResources2(null);
            selected2.setSelected(false);
        }
    }

    private boolean isSelectedResource1(){
        if (selected1 == null) return false;

        selected1 = (RadioButton) resource1.getSelectedToggle();

        return selected1.getText() != null;
    }

    private boolean isSelectedResource2(){
        if (selected2 == null) return false;

        selected2 = (RadioButton) resource2.getSelectedToggle();

        return selected2.getText() != null;
    }


    public void selectServant1(MouseEvent actionEvent) {
        if (isSelectedResource2()){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("Insert the selected resource in a shelf before selecting another one.");
                alert.showAndWait();
            });
            return;
        }

        if (insertedResource1){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You already selected a resource from this row. Choose another one from the other row");
                alert.showAndWait();
            });
            return;
        }

        servantRadio1.setSelected(true);
        servant1.getStyleClass().remove("notSelectedCard");
        servant1.getStyleClass().add("selectedCard");
        disSelectResources1(servant1);
        selectedLabel1 = servant1;
        //System.out.println(" toggle before: " + resource + " this: " + this);
        selected1 = (RadioButton) resource1.getSelectedToggle();
    }

    public void selectShield1(MouseEvent actionEvent) {
        if (isSelectedResource2()){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("Insert the selected resource in a shelf before selecting another one.");
                alert.showAndWait();
            });
            return;
        }
        if (insertedResource1){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You already selected a resource from this row. Choose another one from the other row");
                alert.showAndWait();
            });
            return;
        }

        shieldRadio1.setSelected(true);
        shield1.getStyleClass().remove("notSelectedCard");
        shield1.getStyleClass().add("selectedCard");
        disSelectResources1(shield1);
        selectedLabel1 = shield1;
        //System.out.println(" toggle before: " + resource + " this: " + this);
        selected1 = (RadioButton) resource1.getSelectedToggle();

    }

    public void selectStone1(MouseEvent actionEvent) {
        if (isSelectedResource2()){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("Insert the selected resource in a shelf before selecting another one.");
                alert.showAndWait();
            });
            return;
        }
        if (insertedResource1){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You already selected a resource from this row. Choose another one from the other row");
                alert.showAndWait();
            });
            return;
        }

        stoneRadio1.setSelected(true);
        stone1.getStyleClass().remove("notSelectedCard");
        stone1.getStyleClass().add("selectedCard");
        disSelectResources1(stone1);
        selectedLabel1 = stone1;
        //System.out.println(" toggle before: " + resource + " this: " + this);
        selected1 = (RadioButton) resource1.getSelectedToggle();
    }

    public void selectCoin1(MouseEvent actionEvent) {
        if (isSelectedResource2()){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("Insert the selected resource in a shelf before selecting another one.");
                alert.showAndWait();
            });
            return;
        }

        if (insertedResource1){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You already selected a resource from this row. Choose another one from the other row");
                alert.showAndWait();
            });
            return;
        }

        coinRadio1.setSelected(true);
        coin1.getStyleClass().remove("notSelectedCard");
        coin1.getStyleClass().add("selectedCard");
        disSelectResources1(coin1);
        selectedLabel1 = coin1;
        //System.out.println(" toggle before: " + resource + " this: " + this);
        selected1 = (RadioButton) resource1.getSelectedToggle();
    }

    public void selectServant2(MouseEvent actionEvent) {
        if (isSelectedResource1()){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("Insert the selected resource in a shelf before selecting another one.");
                alert.showAndWait();
            });
            return;
        }

        if (insertedResource2){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You already selected a resource from this row. Choose another one from the other row");
                alert.showAndWait();
            });
            return;
        }

        servantRadio2.setSelected(true);
        servant2.getStyleClass().remove("notSelectedCard");
        servant2.getStyleClass().add("selectedCard");
        disSelectResources2(servant2);
        selectedLabel2 = servant2;
        //System.out.println(" toggle before: " + resource + " this: " + this);
        selected2 = (RadioButton) resource2.getSelectedToggle();
    }

    public void selectShield2(MouseEvent actionEvent) {
        if (isSelectedResource1()){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("Insert the selected resource in a shelf before selecting another one.");
                alert.showAndWait();
            });
            return;
        }

        if (insertedResource2){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You already selected a resource from this row. Choose another one from the other row");
                alert.showAndWait();
            });
            return;
        }

        shieldRadio2.setSelected(true);
        shield2.getStyleClass().remove("notSelectedCard");
        shield2.getStyleClass().add("selectedCard");
        disSelectResources2(shield2);
        selectedLabel2 = shield2;
        //System.out.println(" toggle before: " + resource + " this: " + this);
        selected2 = (RadioButton) resource2.getSelectedToggle();

    }

    public void selectStone2(MouseEvent actionEvent) {
        if (isSelectedResource1()){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("Insert the selected resource in a shelf before selecting another one.");
                alert.showAndWait();
            });
            return;
        }

        if (insertedResource2){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You already selected a resource from this row. Choose another one from the other row");
                alert.showAndWait();
            });
            return;
        }

        stoneRadio2.setSelected(true);
        stone2.getStyleClass().remove("notSelectedCard");
        stone2.getStyleClass().add("selectedCard");
        disSelectResources2(stone2);
        selectedLabel2 = stone2;
        //System.out.println(" toggle before: " + resource + " this: " + this);
        selected2 = (RadioButton) resource2.getSelectedToggle();
    }

    public void selectCoin2(MouseEvent actionEvent) {
        if (isSelectedResource1()){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("Insert the selected resource in a shelf before selecting another one.");
                alert.showAndWait();
            });
            return;
        }

        if (insertedResource2){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You already selected a resource from this row. Choose another one from the other row");
                alert.showAndWait();
            });
            return;
        }

        coinRadio2.setSelected(true);
        coin2.getStyleClass().remove("notSelectedCard");
        coin2.getStyleClass().add("selectedCard");
        disSelectResources2(coin2);
        selectedLabel2 = coin2;
        //System.out.println(" toggle before: " + resource + " this: " + this);
        selected2 = (RadioButton) resource2.getSelectedToggle();
    }

    private void disSelectResources1(Label label) {
        //selected = (RadioButton)resources.getSelectedToggle();
        if (!coin1.equals(label)) {
            coin1.getStyleClass().remove("selectedCard");
            coin1.getStyleClass().add("notSelectedCard");
            //System.out.println("disselected Coin");
            // System.out.println(" toggle text: " + selected.getText());
        }
        if (!stone1.equals(label)) {
            stone1.getStyleClass().remove("selectedCard");
            stone1.getStyleClass().add("notSelectedCard");
            //System.out.println("disselected Stone");
            //System.out.println(" toggle text: " + selected.getText());
        }
        if (!servant1.equals(label)) {
            servant1.getStyleClass().remove("selectedCard");
            servant1.getStyleClass().add("notSelectedCard");
            //System.out.println("disselected Servant");
            //System.out.println(" toggle text: " + selected.getText());
        }
        if (!shield1.equals(label)) {
            shield1.getStyleClass().remove("selectedCard");
            shield1.getStyleClass().add("notSelectedCard");
            //System.out.println("disselected Shield");
            //System.out.println(" toggle text: " + selected.getText());
        }
    }

    private void disSelectResources2(Label label) {
        //selected = (RadioButton)resources.getSelectedToggle();
        if (!coin2.equals(label)) {
            coin2.getStyleClass().remove("selectedCard");
            coin2.getStyleClass().add("notSelectedCard");
            //System.out.println("disselected Coin");
            // System.out.println(" toggle text: " + selected.getText());
        }
        if (!stone2.equals(label)) {
            stone2.getStyleClass().remove("selectedCard");
            stone2.getStyleClass().add("notSelectedCard");
            //System.out.println("disselected Stone");
            //System.out.println(" toggle text: " + selected.getText());
        }
        if (!servant2.equals(label)) {
            servant2.getStyleClass().remove("selectedCard");
            servant2.getStyleClass().add("notSelectedCard");
            //System.out.println("disselected Servant");
            //System.out.println(" toggle text: " + selected.getText());
        }
        if (!shield2.equals(label)) {
            shield2.getStyleClass().remove("selectedCard");
            shield2.getStyleClass().add("notSelectedCard");
            //System.out.println("disselected Shield");
            //System.out.println(" toggle text: " + selected.getText());
        }
    }

    private String getResource(Label label) {
        if (label.getStyleClass().contains("coin")) return "coin";
        if (label.getStyleClass().contains("shield")) return "shield";
        if (label.getStyleClass().contains("stone")) return "stone";
        if (label.getStyleClass().contains("servant")) return "servant";
        return "";
    }


}
