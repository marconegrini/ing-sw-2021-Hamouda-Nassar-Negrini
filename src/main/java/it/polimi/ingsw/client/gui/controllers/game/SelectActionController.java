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
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;

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
            //ConnectionToServerController controller = loader.getController();
            newStage.setTitle("Market board");
            Scene scene = new Scene(root, 600,520);
            newStage.setScene(scene);
            newStage.initStyle(StageStyle.TRANSPARENT);
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
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game/activateProduction.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //ConnectionToServerController controller = loader.getController();
            newStage.setTitle("Activate production");
            Scene scene = new Scene(root, 400,520);
            newStage.setScene(scene);
            newStage.initStyle(StageStyle.TRANSPARENT);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
        });
    }


    public void buyDevelopmentCard(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();
        Platform.runLater(() ->{
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game/buyDevCards.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //ConnectionToServerController controller = loader.getController();
            newStage.setTitle("Market board");
            Scene scene = new Scene(root, 400,520);
            newStage.setScene(scene);
            newStage.initStyle(StageStyle.TRANSPARENT);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
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
            //ConnectionToServerController controller = loader.getController();
            newStage.setTitle("Market board");
            Scene scene = new Scene(root, 400,520);
            newStage.setScene(scene);
            newStage.initStyle(StageStyle.TRANSPARENT);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
        });
    }
}
