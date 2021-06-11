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

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/game/setcalamaio/waiting_game.fxml")));
            SceneManager.setScene(new Scene(root, 1080, 720));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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

    private String getResource(Label label) {
        if (label.getStyleClass().contains("coin")) return "coin";
        if (label.getStyleClass().contains("shield")) return "shield";
        if (label.getStyleClass().contains("stone")) return "stone";
        if (label.getStyleClass().contains("servant")) return "servant";
        return "";
    }

    private int resourceConverter (Label label){
        if (label.getStyleClass().contains("shield")) return 1;
        if (label.getStyleClass().contains("coin")) return 2;
        if (label.getStyleClass().contains("servant")) return 3;
        if (label.getStyleClass().contains("stone")) return 4;
        return 0;
    }

}
