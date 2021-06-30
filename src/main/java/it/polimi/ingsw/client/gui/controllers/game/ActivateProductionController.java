package it.polimi.ingsw.client.gui.controllers.game;

import it.polimi.ingsw.client.gui.SceneManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI class invoked to activate production.
 */
public class ActivateProductionController {

    @FXML
    private Label personalProduction;
    private boolean selectedPersonalProduction = false;
    private List<Integer> selectedSlots = new ArrayList<>();

    public void selectedDevCardSlot(MouseEvent mouseEvent) {
        Label label;
        try {
            label = (Label) mouseEvent.getPickResult().getIntersectedNode();
        } catch (ClassCastException e){
            //e.printStackTrace();
            return;
        }

        Integer slot = GridPane.getColumnIndex(label);

        if (label == null)  return;
        if (selectedSlots.contains(slot)){
            selectedSlots.remove(slot);
        } else{
            selectedSlots.add(slot);
        }

        if (label.getStyleClass().contains("selectedCard")) label.getStyleClass().remove("selectedCard");
        else    label.getStyleClass().add("selectedCard");
    }

    public void back(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();

        Platform.runLater(() -> {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game/selectAction.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            newStage.setTitle("Select action");
            Scene scene = new Scene(root, 500, 390);
            SceneManager.setPopUpScene(scene);
            newStage.setScene(scene);
            newStage.resizableProperty().setValue(Boolean.FALSE);
            newStage.setOnCloseRequest(event -> {
                event.consume();
            });
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
        });
    }

    public void selectPersonalProduction(MouseEvent mouseEvent) {
        if (selectedPersonalProduction){
            selectedPersonalProduction = false;
            personalProduction.getStyleClass().remove("selectedCard");
        } else{
            selectedPersonalProduction = true;
            personalProduction.getStyleClass().add("selectedCard");
        }
    }

    public void activate(ActionEvent actionEvent) {
        System.out.println(selectedSlots);
    }
}
