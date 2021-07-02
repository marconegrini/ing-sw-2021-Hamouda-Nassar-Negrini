package it.polimi.ingsw.client.gui.controllers.game;

import it.polimi.ingsw.client.gui.SceneManager;
import it.polimi.ingsw.client.gui.UpdateObjects;
import it.polimi.ingsw.client.gui.controllers.ControllerGUI;
import it.polimi.ingsw.messages.fromClient.ActivateLeaderCardMessage;
import it.polimi.ingsw.messages.fromClient.DiscardLeaderCardMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;

/**
 * GUI controller class invoked to activate a leader card
 */
public class ActivateLeaderCardController {

    private Label selectedCard;

    /**
     * This method add a green frame to the selected label
     * @param mouseEvent
     */
    public void selectCard(MouseEvent mouseEvent) {
        Label label = (Label) mouseEvent.getPickResult().getIntersectedNode();

        if (selectedCard == null){
            selectedCard = label;
            selectedCard.getStyleClass().remove("notSelectedCard");
            selectedCard.getStyleClass().add("selectedCard");
        } else{
            selectedCard.getStyleClass().remove("selectedCard");
            selectedCard.getStyleClass().add("notSelectedCard");
            selectedCard = label;
            selectedCard.getStyleClass().remove("notSelectedCard");
            selectedCard.getStyleClass().add("selectedCard");
        }
    }

    /**
     * Activate the selected leader card
     * @param actionEvent
     */
    public void activate(ActionEvent actionEvent) {
        if (selectedCard == null)   return;
        Integer index = Integer.parseInt(selectedCard.getId().substring(10));
        //System.out.println("index: " + index);
        ControllerGUI.getServerHandler().sendJson(new ActivateLeaderCardMessage(index-1));

        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();

    }

    /**
     * Allows the user to go back to the main menu
     * @param actionEvent
     */
    public void back(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();

        if (!ControllerGUI.getServerHandler().getIsMultiplayer())   return;

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
            newStage.setOnCloseRequest( event ->{ event.consume();});
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
            UpdateObjects.updateLeaderCards(ControllerGUI.getServerHandler().getLightModel().getLeaderCards(), scene);
            UpdateObjects.updateCoffer(ControllerGUI.getServerHandler().getLightModel().getCoffer());
            UpdateObjects.updateWarehouse(ControllerGUI.getServerHandler().getLightModel().getWarehouse());
        });
    }

    /**
     * Discard the actual selected label
     * @param actionEvent
     */
    public void discard(ActionEvent actionEvent) {
        if (selectedCard == null)   return;
        Integer index = Integer.parseInt(selectedCard.getId().substring(10));
        //System.out.println("index: " + index);

        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();
        ControllerGUI.getServerHandler().sendJson(new DiscardLeaderCardMessage(index-1));
        UpdateObjects.updateLeaderCards(ControllerGUI.getServerHandler().getLightModel().getLeaderCards(), SceneManager.getScene());
        UpdateObjects.updateCoffer(ControllerGUI.getServerHandler().getLightModel().getCoffer());
        UpdateObjects.updateWarehouse(ControllerGUI.getServerHandler().getLightModel().getWarehouse());
    }
}
