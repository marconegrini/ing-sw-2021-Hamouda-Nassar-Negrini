package it.polimi.ingsw.client.gui.controllers.game;

import it.polimi.ingsw.client.gui.SceneManager;
import it.polimi.ingsw.client.gui.UpdateObjects;
import it.polimi.ingsw.client.gui.controllers.ControllerGUI;
import it.polimi.ingsw.enumerations.CardType;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.messages.fromClient.ActivateProductionMessage;
import it.polimi.ingsw.model.cards.LeaderCard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
 * GUI class invoked to activate production.
 */
public class ActivateProductionController {

    public Label label1;
    public Label servant1;
    public Label coin1;
    public Label shield1;
    @FXML
    private Label personalProduction;
    private boolean selectedPersonalProduction = false;
    private static List<Integer> selectedSlots = new ArrayList<>();
    private Label selectedLabel1, selectedLabel2;

    /**
     * Add a green frame around the selected card
     * @param mouseEvent
     */
    public void selectedDevCardSlot(MouseEvent mouseEvent) {
        Label label;
        try {
            label = (Label) mouseEvent.getPickResult().getIntersectedNode();
        } catch (ClassCastException e) {
            return;
        }

        Integer slot = GridPane.getColumnIndex(label);

        if (label == null) return;
        if (selectedSlots.contains(slot)) {
            selectedSlots.remove(slot);
        } else {
            selectedSlots.add(slot);
        }

        if (label.getStyleClass().contains("selectedCard")) label.getStyleClass().remove("selectedCard");
        else label.getStyleClass().add("selectedCard");
    }

    /**
     * Allows the user to go back to the main menu
     * @param actionEvent
     */
    public void back(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();

        selectedSlots.clear();

        if (!ControllerGUI.getServerHandler().getIsMultiplayer()) return;

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
            UpdateObjects.updateLeaderCards(ControllerGUI.getServerHandler().getLightModel().getLeaderCards(), scene);
            UpdateObjects.updateCoffer(ControllerGUI.getServerHandler().getLightModel().getCoffer());
            UpdateObjects.updateWarehouse(ControllerGUI.getServerHandler().getLightModel().getWarehouse());
        });
    }

    /**
     * Manage the selection of the personal production
     * @param mouseEvent
     */
    public void selectPersonalProduction(MouseEvent mouseEvent) {
        if (selectedPersonalProduction) {
            selectedPersonalProduction = false;
            personalProduction.getStyleClass().remove("selectedCard");
        } else {
            selectedPersonalProduction = true;
            personalProduction.getStyleClass().add("selectedCard");
        }
    }

    /**
     * Activate the personal production sending the correct message to the server
     * @param actionEvent
     */
    public void activate(ActionEvent actionEvent) {
        System.out.println(selectedSlots);

        if (selectedSlots.isEmpty() && !selectedPersonalProduction) return;
        if (selectedSlots.isEmpty()){
            Platform.runLater(() -> {
                Stage newStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game/activateProduction/activatePersonalProduction.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                newStage.setTitle("Personal production");
                Scene scene = new Scene(root, 1080, 670);
                SceneManager.setPopUpScene(scene);
                newStage.setScene(scene);
                newStage.initStyle(StageStyle.TRANSPARENT);
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.show();
            });

            Node source = (Node) actionEvent.getSource();
            Window theStage = source.getScene().getWindow();
            theStage.hide();

            return;
        }
        int activatedLeaderCards = 0;

        for (LeaderCard lc : ControllerGUI.getServerHandler().getLightModel().getLeaderCards())
            if (lc.getCardType().equals(CardType.PRODUCTION) && lc.isActivated()) activatedLeaderCards++;

        if (activatedLeaderCards > 0) {
            Platform.runLater(() -> {
                Stage newStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game/activateProduction/useProductionCards.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                newStage.setTitle("Production cards");
                Scene scene = new Scene(root, 450, 250);
                SceneManager.setPopUpScene(scene);
                newStage.setScene(scene);
                newStage.resizableProperty().setValue(Boolean.FALSE);
                newStage.setOnCloseRequest(event -> {
                    event.consume();
                });
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.show();
            });
        } else {
            ControllerGUI.getServerHandler().sendJson(new ActivateProductionMessage(selectedSlots, null));
            selectedSlots.clear();
        }


        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();

    }

    /**
     * Takes the input of the user related to the activation of the production leader card
     * @param actionEvent
     */
    public void useProductionCards(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game/activateProduction/selectLeaderResources.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            newStage.setTitle("Leader Resources");
            Scene scene = new Scene(root, 1080, 670);
            SceneManager.setPopUpScene(scene);
            newStage.setScene(scene);
            newStage.resizableProperty().setValue(Boolean.FALSE);
            newStage.setOnCloseRequest(event -> {
                event.consume();
            });
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();

            int activatedLeaderCards = 1;
            for (LeaderCard lc : ControllerGUI.getServerHandler().getLightModel().getLeaderCards()) {
                if (lc.getCardType().equals(CardType.PRODUCTION) && lc.isActivated()) {

                    System.out.println("Leader card: " + lc);
                    Label card = (Label) scene.lookup("#leaderCard" + activatedLeaderCards);
                    card.setStyle("-fx-background-image: url(\"images/leadercards/" +
                            lc.toPath() + ".png\");" +
                            " -fx-background-size: 100% 100%;" +
                            "-fx-border-width: 5;-fx-border-color: #0cad14;");

                    Label label = (Label) scene.lookup("#servant" + activatedLeaderCards);
                    label.setOpacity(1);
                    label = (Label) scene.lookup("#coin" + activatedLeaderCards);
                    label.setOpacity(1);
                    label = (Label) scene.lookup("#shield" + activatedLeaderCards);
                    label.setOpacity(1);
                    label = (Label) scene.lookup("#stone" + activatedLeaderCards);
                    label.setOpacity(1);
                    label = (Label) scene.lookup("#label" + activatedLeaderCards);
                    label.setOpacity(1);

                    activatedLeaderCards++;
                }
            }

        });

        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();

    }

    /**
     * Takes the input of the user related to the activation of the production leader card
     * @param actionEvent
     */
    public void dontUseProductionCards(ActionEvent actionEvent) {
        System.out.println("dont use: " + selectedSlots);
        ControllerGUI.getServerHandler().sendJson(new ActivateProductionMessage(selectedSlots, null));
        selectedSlots.clear();
        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();
    }

    /**
     * Send the activation message to the server
     * @param actionEvent
     */
    public void select(ActionEvent actionEvent) {
        System.out.println("select: " + selectedSlots);
        List<Resource> resources = new ArrayList<>();

        if (selectedLabel1 == null) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You have to select an output resource from the first column.");
                alert.showAndWait();
            });
            return;
        }

        int activatedLeaderCards = 0;
        for (LeaderCard lc : ControllerGUI.getServerHandler().getLightModel().getLeaderCards())
            if (lc.getCardType().equals(CardType.PRODUCTION) && lc.isActivated()) activatedLeaderCards++;

        if (activatedLeaderCards > 1 && selectedLabel2 == null) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error: Resource");
                alert.setContentText("You have to select an output resource from the second column.");
                alert.showAndWait();
            });
            return;
        }
        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        theStage.hide();

        if (selectedLabel1 != null) resources.add(Resource.getEnum(getResource(selectedLabel1)));
        if (selectedLabel2 != null) resources.add(Resource.getEnum(getResource(selectedLabel2)));
        System.out.println("Leader cards production: " + resources);
        ControllerGUI.getServerHandler().sendJson(new ActivateProductionMessage(selectedSlots, resources));
        selectedSlots.clear();
    }

    /**
     * Add a green frame around the selected resource
     * @param mouseEvent
     */
    public void selectLabel1(MouseEvent mouseEvent) {

        Label label = (Label) mouseEvent.getPickResult().getIntersectedNode();

        if (selectedLabel1 == null) {
            selectedLabel1 = label;
            selectedLabel1.getStyleClass().add("selectedCard");
        } else {
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

        if (selectedLabel2 == null) {
            selectedLabel2 = label;
            selectedLabel2.getStyleClass().add("selectedCard");
        } else {
            selectedLabel2.getStyleClass().remove("selectedCard");
            selectedLabel2 = label;
            selectedLabel2.getStyleClass().add("selectedCard");
        }
    }

    /**
     * This method is used to get a string from the styleSheet of a label
     *
     * @param label is the label from which you want to retrieve the string
     * @return the string retrieved from the label
     */
    private String getResource(Label label) {
        if (label.getStyleClass().contains("coin")) return "coin";
        if (label.getStyleClass().contains("shield")) return "shield";
        if (label.getStyleClass().contains("stone")) return "stone";
        if (label.getStyleClass().contains("servant")) return "servant";
        return "";
    }
}
