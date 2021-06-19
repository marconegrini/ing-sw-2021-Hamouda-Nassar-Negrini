package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.gui.SceneManager;
import it.polimi.ingsw.client.gui.UpdateObjects;
import it.polimi.ingsw.client.gui.controllers.ControllerGUI;
import it.polimi.ingsw.messages.fromClient.CalamaioResponseMessage;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromClient.EmptyMessage;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Resource;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


public class GUIView extends View {
    /**
     * Shows a view in which the user can insert his nickname and the game modality.
     * @return  EmptyMessage
     */
    @Override
    public ClientMessage logClient() {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/login/loginInformation.fxml")));
            SceneManager.setScene(new Scene(root, 1080, 720));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new EmptyMessage();
    }

    @Override
    public ClientMessage initializeCalamaio(String strIn) {

        String source = "";

        //Define which view should be viewed
        if (strIn.contains("first")){
            source = "fxml/game/setcalamaio/setCalamaio.fxml";
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(source)));
                SceneManager.setScene(new Scene(root, 1080, 720));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new CalamaioResponseMessage(0, 0, 0, 0);
        } else if(strIn.contains("second") || strIn.contains("third")){
            source = "fxml/game/setcalamaio/selectOneResource.fxml";
        } else  source = "fxml/game/setcalamaio/selectTwoResources.fxml";

        // set the correct Scene
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(source)));
            SceneManager.setScene(new Scene(root, 1080, 720));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new EmptyMessage();
    }

    @Override
    public ClientMessage calamaioErrHandelr(String strIn) {
        this.showMessage(strIn);
        return new EmptyMessage();
    }


    /**
     * this method show the four leader cards through the GUI
     * @param leaderCards  List of LeaderCards to be shown
     * @return  EmptyMessage
     */
    @Override
    public ClientMessage selectLeaderCards(List<LeaderCard> leaderCards) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/game/chooseLeaderCards.fxml")));
            SceneManager.setScene(new Scene(root, 1080, 720));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        for (LeaderCard leaderCard: leaderCards){ System.out.println(leaderCard.toPath());
        for (int i=0; i<4; i++){
            Label card = (Label) SceneManager.getScene().lookup("#card"+(i+1));
            card.setStyle("-fx-background-image: url(\"images/leadercards/" +
                    leaderCards.get(i).toPath() + ".png\");" +
                    " -fx-background-size: 100% 100%;" +
                    "-fx-border-width: 5");
        }
        return new EmptyMessage();
    }

    @Override
    public ClientMessage selectAction(String choice, boolean err) {
        Platform.runLater(() ->{
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game/selectAction.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //ConnectionToServerController controller = loader.getController();
            newStage.setTitle("Select action");
            Scene scene = new Scene(root, 500,390);
            newStage.setScene(scene);
            newStage.initStyle(StageStyle.TRANSPARENT);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
        });

        return new EmptyMessage();
    }


    @Override
    public ClientMessage storeResources(List<Resource> resources) {

        Platform.runLater(() ->{
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game/selectShelfs.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            newStage.setTitle("Storage cards");
            Scene scene = new Scene(root, 600,400);
            newStage.setScene(scene);
            newStage.initStyle(StageStyle.TRANSPARENT);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();

            GridPane resourcesGrid = (GridPane) scene.lookup("#resources");

            AtomicInteger col = new AtomicInteger(0);
            for (Resource resource: resources){
                Label label = new Label();
                label.getStyleClass().add(resource.toString().toLowerCase());
                label.getStyleClass().add("notSelectedCard");
                label.setPrefHeight(60.0);
                label.setPrefWidth(60.0);
                Platform.runLater(() -> {
                    resourcesGrid.add(label, col.getAndIncrement(), 0);
                });
            }

            try{
                UpdateObjects.updateWarehouse(ControllerGUI.getServerHandler().getLightModel().getWarehouse(), scene);
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        });

        return new EmptyMessage();
    }

    @Override
    public ClientMessage buyDVCard(ArrayList<DevelopmentCard> devCards, boolean err) {
        return null;
    }

    /**
     * Shows a view with a waiting room, in which the user can see how many other players are waiting
     * @return EmptyMessage
     */
    @Override
    public ClientMessage waitingRoom() {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/login/waitingRoom.fxml")));
            SceneManager.setScene(new Scene(root, 1080, 720));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new EmptyMessage();
    }

    @Override
    public void endWaitingRoom() {

    }

    @Override
    public void startGame() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/game/game.fxml")));
            SceneManager.setScene(new Scene(root, 1080, 720));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows an alert with the message
     * @param message  message is shown in the alert pop-up
     */
    @Override
    public void showMessage(String message) {

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText("Error.");
            alert.setContentText(message);
            alert.showAndWait();
        });

    }

    /**
     *
     * @param leaderCards List of LeaderCard that was chosen from the client
     */
    @Override
    public void showLeaderCards(List<LeaderCard> leaderCards) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/game/showLeaderCard.fxml")));
            SceneManager.setScene(new Scene(root, 1080, 720));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Selected leader cards:\n" + leaderCards);

        for (int i=0; i<2; i++){
            Label card = (Label) SceneManager.getScene().lookup("#card"+(i+1));
            card.setStyle("-fx-background-image: url(\"images/leadercards/" +
                    leaderCards.get(i).toPath() + ".png\");" +
                    " -fx-background-size: 100% 100%;" +
                    "-fx-border-width: 5");
        }

    }

    @Override
    public void showResources(List<Resource> resources) {
    }

    @Override
    public ClientMessage activateProduction() {
        return null;
    }

    /**
     * updates the label that contains the number of players that are in waiting room
     * @param s  String that represent number of players in waiting room
     */
    @Override
    public void showParticipantsNumber(String s) {
        Label participantsNumber = (Label) SceneManager.getScene().lookup("#playersNumber");
        Platform.runLater(() -> participantsNumber.setText(s));
    }
}
