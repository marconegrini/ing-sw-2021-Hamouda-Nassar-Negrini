package it.polimi.ingsw.client.gui.controllers.game;

import it.polimi.ingsw.client.gui.SceneManager;
import it.polimi.ingsw.client.gui.UpdateObjects;
import it.polimi.ingsw.client.gui.controllers.ControllerGUI;
import it.polimi.ingsw.model.MarketBoard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/**
 * GUI controller class invoked when the user needs to select an action to perform
 */
public class SelectActionController {


    public void pickResources(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();
        Platform.runLater(() ->{
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game/marketboard.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            newStage.setTitle("Market board");
            Scene scene = new Scene(root, 600,600);
            SceneManager.setPopUpScene(scene);
            newStage.setScene(scene);
            newStage.resizableProperty().setValue(Boolean.FALSE);
            newStage.setOnCloseRequest( event ->{ event.consume();});
            newStage.initModality(Modality.APPLICATION_MODAL);
            MarketBoard marketBoard = ControllerGUI.getServerHandler().getLightModel().getMarketBoard();
            newStage.show();
            UpdateObjects.updateLeaderCards(ControllerGUI.getServerHandler().getLightModel().getLeaderCards(), scene);
            UpdateObjects.updateMarketBoard(marketBoard, scene);
        });
    }


    public void activateProduction(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();
        Platform.runLater(() ->{
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game/activateProduction/activateProduction.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            newStage.setTitle("Activate production");
            Scene scene = new Scene(root, 1300,670);
            SceneManager.setPopUpScene(scene);
            newStage.setScene(scene);
            newStage.resizableProperty().setValue(Boolean.FALSE);
            newStage.setOnCloseRequest( event ->{ event.consume();});
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
            UpdateObjects.updateLeaderCards(ControllerGUI.getServerHandler().getLightModel().getLeaderCards(), scene);
            UpdateObjects.updateDevCardsSlot(ControllerGUI.getServerHandler().getLightModel().getPeekDevCardsInSlot(), scene, false);
        });
    }


    public void buyDevelopmentCard(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();
        Platform.runLater(() ->{
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game/buyDevCard.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            newStage.setTitle("Buy Development Card");
            Scene scene = new Scene(root, 1080,670);
            SceneManager.setPopUpScene(scene);
            newStage.setScene(scene);
            newStage.resizableProperty().setValue(Boolean.FALSE);
            newStage.setOnCloseRequest( event ->{ event.consume();});
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
            UpdateObjects.updateDevCardsDeck(ControllerGUI.getServerHandler().getLightModel().getDevelopmentCardsDeck(), scene);
            UpdateObjects.updateResources(ControllerGUI.getServerHandler().getLightModel().getCoffer(), ControllerGUI.getServerHandler().getLightModel().getWarehouse(), scene);
        });
    }


    public void activateLeaderCard(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();

        Platform.runLater(() ->{
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game/activateLeaderCard.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            newStage.setTitle("Activate Leader Card");
            Scene scene = new Scene(root, 1080,670);
            SceneManager.setPopUpScene(scene);
            newStage.setScene(scene);
            newStage.resizableProperty().setValue(Boolean.FALSE);
            newStage.setOnCloseRequest( event ->{ event.consume();});
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
            UpdateObjects.updateLeaderCards(ControllerGUI.getServerHandler().getLightModel().getLeaderCards(), scene);
        });
    }
}
