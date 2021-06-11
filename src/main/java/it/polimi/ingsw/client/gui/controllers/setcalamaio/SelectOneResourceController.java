package it.polimi.ingsw.client.gui.controllers.setcalamaio;

import it.polimi.ingsw.client.gui.SceneManager;
import it.polimi.ingsw.client.gui.controllers.ControllerGUI;
import it.polimi.ingsw.messages.fromClient.CalamaioResponseMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Objects;

public class SelectOneResourceController {

    @FXML
    private GridPane firstShelf, secondShelf, thirdShelf;
    @FXML
    private ToggleGroup resource;
    @FXML
    private Label servant, coin, shield, stone, selectedLabel;
    @FXML
    private RadioButton selected;
    @FXML
    private RadioButton servantRadio, coinRadio, stoneRadio, shieldRadio;
    private boolean insertedResource = false;
    private int selectedShelf = 0;


    /**
     * Manage the action on the button that allow the user to confirm the selected resource
     * @param actionEvent
     */
    public void ContinueToGame(ActionEvent actionEvent) {
        if (!insertedResource) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You should put a resource on the shelf before continuing.");
                alert.showAndWait();
            });
            return;
        }
        ControllerGUI.getServerHandler().sendJson(new CalamaioResponseMessage(resourceConverter(selectedLabel), 0, selectedShelf, 0));

    }

    /**
     * Manage the onClick event on the first shelf
     * @param mouseEvent
     */
    public void selectFirstShelf(MouseEvent mouseEvent) {
        if (!isSelectedResource()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You should select a resource before selecting a shelf.");
                alert.showAndWait();
            });
            return;
        }

        if (insertedResource) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You already inserted a resource in the shelf.");
                alert.showAndWait();
            });
            disSelectResources(null);
            return;
        }

        Label toPut = selectedLabel;
        Integer column = GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode());
        Integer row = GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode());
        if (column == null) column = 0;
        if (row == null)    row = 0;
        firstShelf.add(toPut, column, row);
        insertedResource = true;
        disSelectResources(null);
        selectedShelf = 3;

    }

    /**
     * Manage the onClick event on the second shelf
     * @param mouseEvent
     */
    public void selectSecondShelf(MouseEvent mouseEvent) {
        if (!isSelectedResource()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You should select a resource before selecting a shelf.");
                alert.showAndWait();
            });
            return;
        }

        if (insertedResource) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You already inserted a resource in the shelf.");
                alert.showAndWait();
            });
            disSelectResources(null);
            return;
        }

        Label toPut = selectedLabel;
        Integer column = GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode());
        Integer row = GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode());
        if (column == null) column = 0;
        if (row == null)    row = 0;
        //System.out.println("column: " + column + " row: " + row);
        secondShelf.add(toPut, column, row);
        insertedResource = true;
        disSelectResources(null);
        selectedShelf = 2;
    }

    /**
     * Manage the onClick event on the third shelf
     * @param mouseEvent
     */
    public void selectThirdShelf(MouseEvent mouseEvent) {
        if (!isSelectedResource()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You should select a resource before selecting a shelf.");
                alert.showAndWait();
            });
            return;
        }

        if (insertedResource) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You already inserted a resource in the shelf.");
                alert.showAndWait();
            });
            disSelectResources(null);
            return;
        }

        Label toPut = selectedLabel;
        Integer column = GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode());
        Integer row = GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode());
        if (column == null) column = 0;
        if (row == null)    row = 0;
        thirdShelf.add(toPut, column, row);
        insertedResource = true;
        disSelectResources(null);
        selectedShelf = 1;
    }

    /**
     * Check if a resource is selected or not
     * @return true if a resource is selected. False if there isn't a selected resource.
     */
    private boolean isSelectedResource() {
        if (selected == null) {
            //System.out.println(" toggle before: " + resource + " this: " + this);
            //System.out.println("Return null. resources: " + resources + " toggle text: " + ((RadioButton)resources.getSelectedToggle()).getText());
            return false;
        }
        selected = (RadioButton) resource.getSelectedToggle();
        //System.out.println(" toggle text: " + selected.getText());
        return selected.getText() != null;
    }

    /**
     * Manage the selection of the resource servant
     * @param actionEvent
     */
    public void selectServant(MouseEvent actionEvent) {
        if (insertedResource) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You already inserted a resource in the shelf.");
                alert.showAndWait();
            });
            disSelectResources(null);
            return;
        }
        servantRadio.setSelected(true);
        servant.getStyleClass().remove("notSelectedCard");
        servant.getStyleClass().add("selectedCard");
        disSelectResources(servant);
        selectedLabel = servant;
        //System.out.println(" toggle before: " + resource + " this: " + this);
        selected = (RadioButton) resource.getSelectedToggle();
    }

    /**
     * Manage the selection of the resource shield
     * @param actionEvent
     */
    public void selectShield(MouseEvent actionEvent) {
        if (insertedResource) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You already inserted a resource in the shelf.");
                alert.showAndWait();
            });
            disSelectResources(null);
            return;
        }
        shieldRadio.setSelected(true);
        shield.getStyleClass().remove("notSelectedCard");
        shield.getStyleClass().add("selectedCard");
        disSelectResources(shield);
        selectedLabel = shield;
        //System.out.println(" toggle before: " + resource + " this: " + this);
        selected = (RadioButton) resource.getSelectedToggle();

    }

    /**
     * Manage the selection of the resource stone
     * @param actionEvent
     */
    public void selectStone(MouseEvent actionEvent) {
        if (insertedResource) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You already inserted a resource in the shelf.");
                alert.showAndWait();
            });
            disSelectResources(null);
            return;
        }
        stoneRadio.setSelected(true);
        stone.getStyleClass().remove("notSelectedCard");
        stone.getStyleClass().add("selectedCard");
        disSelectResources(stone);
        selectedLabel = stone;
        //System.out.println(" toggle before: " + resource + " this: " + this);
        selected = (RadioButton) resource.getSelectedToggle();
    }

    /**
     * Manage the selection of the resource coin
     * @param actionEvent
     */
    public void selectCoin(MouseEvent actionEvent) {
        if (insertedResource) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You already inserted a resource in the shelf.");
                alert.showAndWait();
            });
            disSelectResources(null);
            return;
        }
        coinRadio.setSelected(true);
        coin.getStyleClass().remove("notSelectedCard");
        coin.getStyleClass().add("selectedCard");
        disSelectResources(coin);
        selectedLabel = coin;
        //System.out.println(" toggle before: " + resource + " this: " + this);
        selected = (RadioButton) resource.getSelectedToggle();
    }

    /**
     * Remove the selection from all the labels except the label in the parameter.
     * It does remove the green frame around the labels and put a blue one.
     * @param label  The label from which you didn't want to remove the green frame
     */
    private void disSelectResources(Label label) {
        //selected = (RadioButton)resources.getSelectedToggle();
        if (!coin.equals(label)) {
            coin.getStyleClass().remove("selectedCard");
            coin.getStyleClass().add("notSelectedCard");
            //System.out.println("disselected Coin");
            // System.out.println(" toggle text: " + selected.getText());
        }
        if (!stone.equals(label)) {
            stone.getStyleClass().remove("selectedCard");
            stone.getStyleClass().add("notSelectedCard");
            //System.out.println("disselected Stone");
            //System.out.println(" toggle text: " + selected.getText());
        }
        if (!servant.equals(label)) {
            servant.getStyleClass().remove("selectedCard");
            servant.getStyleClass().add("notSelectedCard");
            //System.out.println("disselected Servant");
            //System.out.println(" toggle text: " + selected.getText());
        }
        if (!shield.equals(label)) {
            shield.getStyleClass().remove("selectedCard");
            shield.getStyleClass().add("notSelectedCard");
            //System.out.println("disselected Shield");
            //System.out.println(" toggle text: " + selected.getText());
        }
    }

    /**
     * This method is used to convert a label into a number that represent that label.
     * This is necessary to use the same message standard that will be sent to the server.
     * @param label  the label that should be converted in an integer
     * @return  the integer that refers to the label
     */
    private int resourceConverter (Label label){
        if (label.getStyleClass().contains("shield")) return 1;
        if (label.getStyleClass().contains("coin")) return 2;
        if (label.getStyleClass().contains("servant")) return 3;
        if (label.getStyleClass().contains("stone")) return 4;
        return 0;
    }

}
