package it.polimi.ingsw.client.gui.controllers.game;

import it.polimi.ingsw.client.gui.UpdateObjects;
import it.polimi.ingsw.client.gui.controllers.ControllerGUI;
import it.polimi.ingsw.messages.fromClient.InsertResourcesInWarehouseMessage;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.IllegalInsertionException;
import it.polimi.ingsw.model.exceptions.StorageOutOfBoundsException;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;


public class PutResourcesController {


    @FXML
    private GridPane resourcesGrid;
    @FXML
    private Button discardButton;
    private Label selectedLabel;

    public void selectedFirstShelf(MouseEvent mouseEvent) {
        if (selectedLabel == null) return;

        List<Resource> resourcesOnShelf = ControllerGUI.getServerHandler().getLightModel().getWarehouse().getWarehouseStorage(1);
        if (resourcesOnShelf.size() == 1) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("There isn't enough space on this shelf.");
                alert.showAndWait();
            });
            disSelectResources(null);
            discardButton.setDisable(true);
            return;
        }

        List<Resource> resourceToInsert = new ArrayList<>();
        resourceToInsert.add(resourceConverter(selectedLabel));
        try {
            ControllerGUI.getServerHandler().getLightModel().getWarehouse().putResource(1, resourceToInsert);
        } catch (StorageOutOfBoundsException | IllegalInsertionException e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You cant insert this resource here.");
                alert.showAndWait();
            });
            disSelectResources(null);
            discardButton.setDisable(true);
            return;
        }

        System.out.println(resourcesGrid.getChildren().size());
        Node source = (Node) mouseEvent.getSource();
        Window theStage = source.getScene().getWindow();
        //if (resourcesGrid.getChildren().size() >= 1){
            System.out.println("nascondi");
            theStage.hide();
        //}
        ControllerGUI.getServerHandler().sendJson(new InsertResourcesInWarehouseMessage(false, resourceToInsert, 1));
        UpdateObjects.updateWarehouse(ControllerGUI.getServerHandler().getLightModel().getWarehouse(), source.getScene());

    }

    public void selectedSecondShelf(MouseEvent mouseEvent) {
        if (selectedLabel == null) return;
        List<Resource> resourcesOnShelf = ControllerGUI.getServerHandler().getLightModel().getWarehouse().getWarehouseStorage(2);
        if (resourcesOnShelf.size() == 2) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("There isn't enough space on this shelf.");
                alert.showAndWait();
            });
            disSelectResources(null);
            discardButton.setDisable(true);
            return;
        }

        List<Resource> resourceToInsert = new ArrayList<>();
        resourceToInsert.add(resourceConverter(selectedLabel));
        try {
            ControllerGUI.getServerHandler().getLightModel().getWarehouse().putResource(2, resourceToInsert);
        } catch (StorageOutOfBoundsException | IllegalInsertionException e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You cant insert this resource here.");
                alert.showAndWait();
            });
            disSelectResources(null);
            discardButton.setDisable(true);
            return;
        }

        System.out.println(resourcesGrid.getChildren().size());
        Node source = (Node) mouseEvent.getSource();
        Window theStage = source.getScene().getWindow();
       // if (resourcesGrid.getChildren().size() >= 1){
            System.out.println("nascondi");
            theStage.hide();
        //}
        ControllerGUI.getServerHandler().sendJson(new InsertResourcesInWarehouseMessage(false, resourceToInsert, 2));
        UpdateObjects.updateWarehouse(ControllerGUI.getServerHandler().getLightModel().getWarehouse(), source.getScene());

    }

    public void selectedThirdShelf(MouseEvent mouseEvent) {
        //System.out.println(resourcesGrid);
        if (selectedLabel == null) return;
        List<Resource> resourcesOnShelf = ControllerGUI.getServerHandler().getLightModel().getWarehouse().getWarehouseStorage(3);
        if (resourcesOnShelf.size() == 3) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("There isn't enough space on this shelf.");
                alert.showAndWait();
            });
            disSelectResources(null);
            discardButton.setDisable(true);
            return;
        }

        List<Resource> resourceToInsert = new ArrayList<>();
        resourceToInsert.add(resourceConverter(selectedLabel));
        try {
            ControllerGUI.getServerHandler().getLightModel().getWarehouse().putResource(3, resourceToInsert);
        } catch (StorageOutOfBoundsException | IllegalInsertionException e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You cant insert this resource here.");
                alert.showAndWait();
            });
            disSelectResources(null);
            discardButton.setDisable(true);
            return;
        }

        resourcesGrid.getChildren().remove(selectedLabel);
        Node source = (Node) mouseEvent.getSource();
        Window theStage = source.getScene().getWindow();
       // if (resourcesGrid.getChildren().size() >= 1){
            System.out.println("nascondi");
            theStage.hide();
        //}

        ControllerGUI.getServerHandler().sendJson(new InsertResourcesInWarehouseMessage(false, resourceToInsert, 3));
        UpdateObjects.updateWarehouse(ControllerGUI.getServerHandler().getLightModel().getWarehouse(), source.getScene());

        System.out.println(resourcesGrid.getChildren().size());
    }


    public void selectResource(MouseEvent mouseEvent) {
        selectedLabel = (Label) mouseEvent.getPickResult().getIntersectedNode();
        selectedLabel.getStyleClass().remove("notSelectedCard");
        selectedLabel.getStyleClass().add("selectedCard");
        disSelectResources(selectedLabel);
        discardButton.setDisable(false);

    }

    /**
     * Remove the selection from all the labels except the label in the parameter.
     * It does remove the green frame around the labels and put a blue one.
     *
     * @param label The label from which you didn't want to remove the green frame
     */
    private void disSelectResources(Label label) {

        if (resourcesGrid == null) return;
        //System.out.println("selectedLabel = " + selectedLabel);
        ObservableList<Node> childrens = resourcesGrid.getChildren();
        //System.out.println("");
        for (Node node : childrens) {
            Label lb = (Label) node;
            if (!lb.equals(label)) {
                //System.out.println("disselected = " + lb);
                if (lb.getStyleClass().contains("selectedCard")) {
                    lb.getStyleClass().remove("selectedCard");
                    lb.getStyleClass().add("notSelectedCard");
                }
            }
        }
    }

    /**
     * This method is used to convert a label into a Resource object that represent that label.
     *
     * @param label the label that should be converted in an Resource
     * @return the Resource that refers to the label
     */
    private Resource resourceConverter(Label label) {
        if (label.getStyleClass().contains("shield")) return Resource.SHIELD;
        if (label.getStyleClass().contains("coin")) return Resource.COIN;
        if (label.getStyleClass().contains("servant")) return Resource.SERVANT;
        if (label.getStyleClass().contains("stone")) return Resource.STONE;
        return null;
    }

    public void discardResource(ActionEvent actionEvent) {
    }
}
