package it.polimi.ingsw.client.gui.controllers.setcalamaio;

import it.polimi.ingsw.client.gui.SceneManager;
import it.polimi.ingsw.client.gui.controllers.ControllerGUI;
import it.polimi.ingsw.messages.fromClient.CalamaioResponseMessage;
import it.polimi.ingsw.model.enumerations.Resource;
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
import java.util.HashMap;
import java.util.Objects;

public class SelectTwoResourceController {

    @FXML
    private RadioButton stoneRadio2, shieldRadio2, servantRadio2, coinRadio2;
    @FXML
    private ToggleGroup resource2, resource1;
    @FXML
    private Label stone2, shield2, coin2, servant2;
    private Label selectedLabel1, selectedLabel2;
    @FXML
    private GridPane firstShelf, secondShelf, thirdShelf;
    @FXML
    private RadioButton shieldRadio1, stoneRadio1, coinRadio1, servantRadio1;
    private RadioButton selected1, selected2;
    @FXML
    private Label stone1, shield1, coin1, servant1;
    private boolean insertedResource1 = false, insertedResource2 = false;
    private int selectedShelf1 = 0, selectedShelf2 = 0;
    private HashMap<Integer, Resource> resourcesOnShelfs = new HashMap<>();


    public void ContinueToGame(ActionEvent actionEvent) {
        if (insertedResource1 && insertedResource2) {
            ControllerGUI.getServerHandler().sendJson(new CalamaioResponseMessage(resourceConverter(selectedLabel1), resourceConverter(selectedLabel2), selectedShelf1, selectedShelf2));

            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/game/setcalamaio/waiting_game.fxml")));
                SceneManager.setScene(new Scene(root, 1080, 720));
            } catch (IOException e) {
                e.printStackTrace();
            }


        }else{
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You should select two resource and put them on the shelf before continuing.");
                alert.showAndWait();
            });
        }
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

            Label toPut = selectedLabel1;
            Integer column = GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode());
            Integer row = GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode());
            if (column == null) column = 0;
            if (row == null) row = 0;

            if (getResourceFromIndex(1 + column) != null) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Error: Resource");
                    alert.setContentText("You can't insert two resources in the same slot.");
                    alert.showAndWait();
                });
                disSelectResources1(null);
                selected1.setSelected(false);
                return;
            }

            if (!insertionValid(toPut, 1 + column)) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Error: Resource");
                    alert.setContentText("You can't insert two different type of resources on the same shelf.");
                    alert.showAndWait();
                });
                disSelectResources1(null);
                selected1.setSelected(false);
                return;
            }

            firstShelf.add(toPut, column, row);
            insertedResource1 = true;
            disSelectResources1(null);
            selected1.setSelected(false);
            selectedShelf1 = 3;
            resourcesOnShelfs.put(1 + column, Resource.getEnum(getResource(toPut)));
            return;
        }

        if (isSelectedResource2()) {
            Label toPut = selectedLabel2;
            Integer column = GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode());
            Integer row = GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode());
            if (column == null) column = 0;
            if (row == null) row = 0;
            if (getResourceFromIndex(1 + column) != null) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Error: Resource");
                    alert.setContentText("You can't insert two resources in the same slot.");
                    alert.showAndWait();
                });
                disSelectResources2(null);
                selected2.setSelected(false);
                return;
            }
            if (!insertionValid(toPut, 1 + column)) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Error: Resource");
                    alert.setContentText("You can't insert two different type of resources on the same shelf.");
                    alert.showAndWait();
                });
                disSelectResources2(null);
                selected2.setSelected(false);
                return;
            }
            firstShelf.add(toPut, column, row);
            insertedResource2 = true;
            disSelectResources2(null);
            selected2.setSelected(false);
            selectedShelf2 = 3;
            resourcesOnShelfs.put(1 + column, Resource.getEnum(getResource(toPut)));
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
            Label toPut = selectedLabel1;
            Integer column = GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode());
            Integer row = GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode());
            if (column == null) column = 0;
            if (row == null) row = 0;
            if (getResourceFromIndex(4 + column) != null) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Error: Resource");
                    alert.setContentText("You can't insert two resources in the same slot.");
                    alert.showAndWait();
                });
                disSelectResources1(null);
                selected1.setSelected(false);
                return;
            }
            if (!insertionValid(toPut, 4 + column)) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Error: Resource");
                    alert.setContentText("You can't insert two different type of resources on the same shelf.");
                    alert.showAndWait();
                });
                disSelectResources1(null);
                selected1.setSelected(false);
                return;
            }
            secondShelf.add(toPut, column, row);
            insertedResource1 = true;
            disSelectResources1(null);
            selected1.setSelected(false);
            selectedShelf1 = 2;
            resourcesOnShelfs.put(4 + column, Resource.getEnum(getResource(toPut)));
            return;
        }

        if (isSelectedResource2()) {
            Label toPut = selectedLabel2;
            Integer column = GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode());
            Integer row = GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode());
            if (column == null) column = 0;
            if (row == null) row = 0;
            if (getResourceFromIndex(4 + column) != null) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Error: Resource");
                    alert.setContentText("You can't insert two resources in the same slot.");
                    alert.showAndWait();
                });
                disSelectResources2(null);
                selected2.setSelected(false);
                return;
            }
            if (!insertionValid(toPut, 4 + column)) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Error: Resource");
                    alert.setContentText("You can't insert two different type of resources on the same shelf.");
                    alert.showAndWait();
                });
                disSelectResources2(null);
                selected2.setSelected(false);
                return;
            }
            secondShelf.add(toPut, column, row);
            System.out.println("Added in col: " + column + " row: " + row);
            insertedResource2 = true;
            disSelectResources2(null);
            selected2.setSelected(false);
            selectedShelf2 = 2;
            resourcesOnShelfs.put(4 + column, Resource.getEnum(getResource(toPut)));
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
            Label toPut = selectedLabel1;
            Integer column = GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode());
            Integer row = GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode());
            if (column == null) column = 0;
            if (row == null) row = 0;
            if (getResourceFromIndex(6) != null) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Error: Resource");
                    alert.setContentText("You can't insert two resources in the same slot.");
                    alert.showAndWait();
                });
                disSelectResources1(null);
                selected1.setSelected(false);
                return;
            }
            if (!insertionValid(toPut, 6)) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Error: Resource");
                    alert.setContentText("You can't insert two different type of resources on the same shelf.");
                    alert.showAndWait();
                });
                disSelectResources1(null);
                selected1.setSelected(false);
                return;
            }
            thirdShelf.add(toPut, column, row);
            insertedResource1 = true;
            disSelectResources1(null);
            selected1.setSelected(false);
            selectedShelf1 = 1;
            resourcesOnShelfs.put(6, Resource.getEnum(getResource(toPut)));
            return;
        }

        if (isSelectedResource2()) {
            Label toPut = selectedLabel2;
            Integer column = GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode());
            Integer row = GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode());
            if (column == null) column = 0;
            if (row == null) row = 0;
            if (getResourceFromIndex(6) != null) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Error: Resource");
                    alert.setContentText("You can't insert two resources in the same slot.");
                    alert.showAndWait();
                });
                disSelectResources2(null);
                selected2.setSelected(false);
                return;
            }
            if (!insertionValid(toPut, 6)) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Error: Resource");
                    alert.setContentText("You can't insert two different type of resources on the same shelf.");
                    alert.showAndWait();
                });
                disSelectResources2(null);
                selected2.setSelected(false);
                return;
            }
            thirdShelf.add(toPut, column, row);
            insertedResource2 = true;
            disSelectResources2(null);
            selected2.setSelected(false);
            selectedShelf2 = 1;
            resourcesOnShelfs.put(6, Resource.getEnum(getResource(toPut)));
        }
    }

    private boolean isSelectedResource1() {
        if (selected1 == null) return false;

        selected1 = (RadioButton) resource1.getSelectedToggle();
        if (selected1 == null) return false;

        return selected1.getText() != null;
    }

    private boolean isSelectedResource2() {
        if (selected2 == null) return false;

        selected2 = (RadioButton) resource2.getSelectedToggle();
        if (selected2 == null) return false;

        return selected2.getText() != null;
    }


    public void selectServant1(MouseEvent actionEvent) {
        if (isSelectedResource2()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("Insert the selected resource in a shelf before selecting another one.");
                alert.showAndWait();
            });
            return;
        }

        if (insertedResource1) {
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
        if (isSelectedResource2()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("Insert the selected resource in a shelf before selecting another one.");
                alert.showAndWait();
            });
            return;
        }
        if (insertedResource1) {
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
        if (isSelectedResource2()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("Insert the selected resource in a shelf before selecting another one.");
                alert.showAndWait();
            });
            return;
        }
        if (insertedResource1) {
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
        selected1 = (RadioButton) resource1.getSelectedToggle();
    }

    public void selectCoin1(MouseEvent actionEvent) {
        if (isSelectedResource2()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("Insert the selected resource in a shelf before selecting another one.");
                alert.showAndWait();
            });
            return;
        }

        if (insertedResource1) {
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
        selected1 = (RadioButton) resource1.getSelectedToggle();
    }

    public void selectServant2(MouseEvent actionEvent) {
        if (isSelectedResource1()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("Insert the selected resource in a shelf before selecting another one.");
                alert.showAndWait();
            });
            return;
        }

        if (insertedResource2) {
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
        selected2 = (RadioButton) resource2.getSelectedToggle();
    }

    public void selectShield2(MouseEvent actionEvent) {
        if (isSelectedResource1()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("Insert the selected resource in a shelf before selecting another one.");
                alert.showAndWait();
            });
            return;
        }

        if (insertedResource2) {
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
        if (isSelectedResource1()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("Insert the selected resource in a shelf before selecting another one.");
                alert.showAndWait();
            });
            return;
        }

        if (insertedResource2) {
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
        selected2 = (RadioButton) resource2.getSelectedToggle();
    }

    public void selectCoin2(MouseEvent actionEvent) {
        if (isSelectedResource1()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("Insert the selected resource in a shelf before selecting another one.");
                alert.showAndWait();
            });
            return;
        }

        if (insertedResource2) {
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
        if (!coin1.equals(label)) {
            coin1.getStyleClass().remove("selectedCard");
            coin1.getStyleClass().add("notSelectedCard");
        }
        if (!stone1.equals(label)) {
            stone1.getStyleClass().remove("selectedCard");
            stone1.getStyleClass().add("notSelectedCard");
        }
        if (!servant1.equals(label)) {
            servant1.getStyleClass().remove("selectedCard");
            servant1.getStyleClass().add("notSelectedCard");
        }
        if (!shield1.equals(label)) {
            shield1.getStyleClass().remove("selectedCard");
            shield1.getStyleClass().add("notSelectedCard");
        }
    }

    private void disSelectResources2(Label label) {
        if (!coin2.equals(label)) {
            coin2.getStyleClass().remove("selectedCard");
            coin2.getStyleClass().add("notSelectedCard");
        }
        if (!stone2.equals(label)) {
            stone2.getStyleClass().remove("selectedCard");
            stone2.getStyleClass().add("notSelectedCard");
        }
        if (!servant2.equals(label)) {
            servant2.getStyleClass().remove("selectedCard");
            servant2.getStyleClass().add("notSelectedCard");
        }
        if (!shield2.equals(label)) {
            shield2.getStyleClass().remove("selectedCard");
            shield2.getStyleClass().add("notSelectedCard");
        }
    }

    private String getResource(Label label) {
        if (label.getStyleClass().contains("coin")) return "coin";
        if (label.getStyleClass().contains("shield")) return "shield";
        if (label.getStyleClass().contains("stone")) return "stone";
        if (label.getStyleClass().contains("servant")) return "servant";
        return "";
    }

    private int resourceConverter(Label label) {
        if (label.getStyleClass().contains("shield")) return 1;
        if (label.getStyleClass().contains("coin")) return 2;
        if (label.getStyleClass().contains("servant")) return 3;
        if (label.getStyleClass().contains("stone")) return 4;
        return 0;
    }

    private Resource getResourceFromIndex(Integer index) {
        return resourcesOnShelfs.get(index);
    }

    private boolean insertionValid(Label label, Integer index) {
        Resource resource = Resource.getEnum(getResource(label));
        if (index >= 1 && index <= 3) {
            for (int i = 1; i <= 3; i++) {
                Resource res = resourcesOnShelfs.get(i);
                if (res == null) continue;
                if (!res.equals(resource)) return false;
            }
        }

        if (index >= 4 && index <= 5) {
            for (int i = 4; i <= 5; i++) {
                Resource res = resourcesOnShelfs.get(i);
                if (res == null) continue;
                if (!res.equals(resource)) return false;
            }
        }
        return resourceInAnotherShelf(resource, index);
    }

    private boolean resourceInAnotherShelf(Resource resource, Integer index) {
        if (index >= 1 && index <= 3) {
            for (int i = 4; i <= 6; i++) {
                Resource res = resourcesOnShelfs.get(i);
                if (resource.equals(res)) return false;
            }
        } else if (index >= 4 && index <= 5) {
            for (int i = 1; i <= 3; i++) {
                Resource res = resourcesOnShelfs.get(i);
                if (resource.equals(res)) return false;
            }
            if (resource.equals(resourcesOnShelfs.get(6))) return false;
        } else for (int i = 1; i <= 5; i++) {
            Resource res = resourcesOnShelfs.get(i);
            if (resource.equals(res)) return false;
        }

        return true;
    }


}
