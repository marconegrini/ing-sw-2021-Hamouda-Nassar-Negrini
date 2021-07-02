package it.polimi.ingsw.client.gui.controllers.game;

import it.polimi.ingsw.client.gui.SceneManager;
import it.polimi.ingsw.client.gui.UpdateObjects;
import it.polimi.ingsw.client.gui.controllers.ControllerGUI;
import it.polimi.ingsw.messages.fromClient.PickResourcesMessage;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.StorageLeaderCard;
import it.polimi.ingsw.enumerations.CardType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI controller class invoked when playing with marketboard
 */
public class MarketboardController {

    private Label selectedLabel;
    private static Integer column, row;
    private static boolean isRow = false;

    public void enteredSelection(MouseEvent mouseEvent) {
        Label label = (Label) mouseEvent.getPickResult().getIntersectedNode();
        selectedLabel = label;
        label.setOpacity(0.33);
    }


    public void exitedSelection(MouseEvent mouseEvent) {
        selectedLabel.setOpacity(0.0);
    }

    public void selectCol(MouseEvent mouseEvent) {
        column = GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode());
        if (column == null)     column = 0;
        //System.out.println(column.intValue() + 1);
        Node source = (Node) mouseEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();
        isRow = false;


        List<StorageLeaderCard> slds = new ArrayList<>();
        if (ControllerGUI.getServerHandler().getLightModel().getLeaderCards().stream().anyMatch(x->x.getCardType().equals(CardType.STORAGE)&&x.isActivated())) {
            for (LeaderCard ld : ControllerGUI.getServerHandler().getLightModel().getLeaderCards()) {
                if(ld.getCardType().equals(CardType.STORAGE))
                    slds.add(((StorageLeaderCard) ld));
            }

            if (slds.stream().anyMatch(StorageLeaderCard::hasAvailableSlots)) {
                Platform.runLater(() ->{
                    Stage newStage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game/useStorageCards.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //ConnectionToServerController controller = loader.getController();
                    newStage.setTitle("Storage cards");
                    Scene scene = new Scene(root, 450,250);
                    newStage.setScene(scene);
                    newStage.initStyle(StageStyle.TRANSPARENT);
                    newStage.initModality(Modality.APPLICATION_MODAL);
                    newStage.show();
                });
                return;
            }
        }

        ControllerGUI.getServerHandler().sendJson(new PickResourcesMessage(isRow, column+1, false));

    }

    public void selectRow(MouseEvent mouseEvent) {
        row = GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode());
        if (row == null)     row = 0;

        Node source = (Node) mouseEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();
        isRow = true;

        List<StorageLeaderCard> slds = new ArrayList<>();
        if (ControllerGUI.getServerHandler().getLightModel().getLeaderCards().stream().anyMatch(x->x.getCardType().equals(CardType.STORAGE)&&x.isActivated())) {
            for (LeaderCard ld : ControllerGUI.getServerHandler().getLightModel().getLeaderCards()) {
                if(ld.getCardType().equals(CardType.STORAGE))
                    slds.add(((StorageLeaderCard) ld));
            }

            if (slds.stream().anyMatch(StorageLeaderCard::hasAvailableSlots)) {
                Platform.runLater(() ->{
                    Stage newStage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game/useStorageCards.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    newStage.setTitle("Storage cards");
                    Scene scene = new Scene(root, 450,250);
                    newStage.setScene(scene);
                    newStage.initStyle(StageStyle.TRANSPARENT);
                    newStage.initModality(Modality.APPLICATION_MODAL);
                    newStage.show();
                });
                return;
            }
        }
        ControllerGUI.getServerHandler().sendJson(new PickResourcesMessage(isRow, row+1, false));

    }

    public void dontUseLeaderCards(ActionEvent actionEvent) {
        System.out.println(column);
        System.out.println(row);
        int result;
        if (column == null) result = row;
        else    result = column;
        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();
        ControllerGUI.getServerHandler().sendJson(new PickResourcesMessage(isRow, result+1, false));
    }

    public void useLeaderCards(ActionEvent actionEvent) {
        System.out.println(column);
        System.out.println(row);
        int result;
        if (column == null) result = row;
        else    result = column;
        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();
        ControllerGUI.getServerHandler().sendJson(new PickResourcesMessage(isRow, result+1, true));
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
}
