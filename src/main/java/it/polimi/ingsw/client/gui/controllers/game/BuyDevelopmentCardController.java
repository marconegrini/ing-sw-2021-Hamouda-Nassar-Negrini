package it.polimi.ingsw.client.gui.controllers.game;

import it.polimi.ingsw.client.gui.SceneManager;
import it.polimi.ingsw.client.gui.UpdateObjects;
import it.polimi.ingsw.client.gui.controllers.ControllerGUI;
import it.polimi.ingsw.messages.fromClient.BuyDevCardMessage;
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
 * GUI controller class invoked when buying a development card
 */
public class BuyDevelopmentCardController {

    private Label selectedCard;
    private static int column, row;

    public void selectDevCard(ActionEvent actionEvent) {

        int id = Integer.parseInt(selectedCard.getId().substring(4));

        row = id / 4;
        column = id % 4;

        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();

        Platform.runLater(() -> {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game/selectDevSlot.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            newStage.setTitle("Select slot");
            Scene scene = new Scene(root, 1080, 720);
            SceneManager.setPopUpScene(scene);
            newStage.setScene(scene);
            newStage.resizableProperty().setValue(Boolean.FALSE);
            newStage.setOnCloseRequest( event ->{ event.consume();});
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
            Label label = (Label) scene.lookup("#cardToInsert");
            label.setBackground(selectedCard.getBackground());

            UpdateObjects.updateDevCardsSlot(ControllerGUI.getServerHandler().getLightModel().getPeekDevCardsInSlot(), scene, true);
        });

    }

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
            newStage.initStyle(StageStyle.TRANSPARENT);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
            UpdateObjects.updateLeaderCards(ControllerGUI.getServerHandler().getLightModel().getLeaderCards(), scene);
            UpdateObjects.updateCoffer(ControllerGUI.getServerHandler().getLightModel().getCoffer());
            UpdateObjects.updateWarehouse(ControllerGUI.getServerHandler().getLightModel().getWarehouse());
        });

    }

    public void selectSlot(MouseEvent mouseEvent) {
        Label label = (Label) mouseEvent.getPickResult().getIntersectedNode();
        Integer slot = Integer.parseInt(label.getId().substring(4));

        ControllerGUI.getServerHandler().sendJson(new BuyDevCardMessage(row, column, slot));
        Node source = (Node) mouseEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();
    }
}
