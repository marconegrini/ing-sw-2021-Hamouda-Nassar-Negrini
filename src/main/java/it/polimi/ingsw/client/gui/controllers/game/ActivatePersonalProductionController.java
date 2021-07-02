package it.polimi.ingsw.client.gui.controllers.game;

import it.polimi.ingsw.client.gui.UpdateObjects;
import it.polimi.ingsw.client.gui.controllers.ControllerGUI;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.messages.fromClient.ActivatePersonalProductionMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;

/**
 * GUI controller class invoked to activate the personal production
 */
public class ActivatePersonalProductionController {

    private Label selectedLabel1, selectedLabel2, selectedLabel3;

    /**
     * This method manage the selection of the resources and send the message to the server
     * @param actionEvent
     */
    public void select(ActionEvent actionEvent) {
        if (selectedLabel3 == null || selectedLabel1 == null || selectedLabel2 == null) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You have to select two input and one output resources.");
                alert.showAndWait();
            });
            return;
        }

        ControllerGUI.getServerHandler().sendJson(new ActivatePersonalProductionMessage(Resource.getEnum(getResource(selectedLabel1)), Resource.getEnum(getResource(selectedLabel2)), Resource.getEnum(getResource(selectedLabel3)), null, true, true));

        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();
    }

    /**
     * Add a green frame around the selected resource
     * @param mouseEvent
     */
    public void selectLabel1(MouseEvent mouseEvent) {

        Label label = (Label) mouseEvent.getPickResult().getIntersectedNode();

        if (selectedLabel1 == null){
            selectedLabel1 = label;
            selectedLabel1.getStyleClass().add("selectedCard");
        } else{
            selectedLabel1.getStyleClass().remove("selectedCard");
            selectedLabel1 = label;
            selectedLabel1.getStyleClass().add("selectedCard");
        }
    }

    /**
     * Add a green frame around the selected resource
     * @param mouseEvent
     */
    public void selectLabel2(MouseEvent mouseEvent) {
        Label label = (Label) mouseEvent.getPickResult().getIntersectedNode();

        if (selectedLabel2 == null){
            selectedLabel2 = label;
            selectedLabel2.getStyleClass().add("selectedCard");
        } else{
            selectedLabel2.getStyleClass().remove("selectedCard");
            selectedLabel2 = label;
            selectedLabel2.getStyleClass().add("selectedCard");
        }
    }

    /**
     * Add a green frame around the selected resource
     * @param mouseEvent
     */
    public void selectLabel3(MouseEvent mouseEvent) {
        Label label = (Label) mouseEvent.getPickResult().getIntersectedNode();

        if (selectedLabel3 == null){
            selectedLabel3 = label;
            selectedLabel3.getStyleClass().add("selectedCard");
        } else{
            selectedLabel3.getStyleClass().remove("selectedCard");
            selectedLabel3 = label;
            selectedLabel3.getStyleClass().add("selectedCard");
        }
    }

    /**
     * This method is used to get a string from the styleSheet of a label
     * @param label  is the label from which you want to retrieve the string
     * @return  the string retrieved from the label
     */
    private String getResource(Label label) {
        if (label.getStyleClass().contains("coin")) return "coin";
        if (label.getStyleClass().contains("shield")) return "shield";
        if (label.getStyleClass().contains("stone")) return "stone";
        if (label.getStyleClass().contains("servant")) return "servant";
        return "";
    }

    /**
     * Allows the user to go back to the main menu
     * @param actionEvent
     */
    public void back(ActionEvent actionEvent) {
        ControllerGUI.getServerHandler().sendJson(new ActivatePersonalProductionMessage(null, null, null, null, true, false));
        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();
        //UpdateObjects.updateLeaderCards(ControllerGUI.getServerHandler().getLightModel().getLeaderCards(), scene);
        UpdateObjects.updateCoffer(ControllerGUI.getServerHandler().getLightModel().getCoffer());
        UpdateObjects.updateWarehouse(ControllerGUI.getServerHandler().getLightModel().getWarehouse());

    }
}
