package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.gui.SceneManager;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GUIView extends View {

    @Override
    public ClientMessage logClient() {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/login/loginInformation.fxml")));
            SceneManager.setScene(new Scene(root, 1080, 730));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new EmptyMessage();
    }

    @Override
    public ClientMessage initializeCalamaio(String strIn) {
        return null;
    }

    @Override
    public ClientMessage calamaioErrHandelr(String strIn) {
        return null;
    }


    @Override
    public ClientMessage selectLeaderCards(List<LeaderCard> leaderCards) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/login/chooseLeaderCards.fxml")));
            SceneManager.setScene(new Scene(root, 1080, 730));
            //SceneManager.setScene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (LeaderCard leaderCard: leaderCards) System.out.println(leaderCard.toPath());
        //leaderCards.forEach(System.out::println);


        Label firstCard = (Label) SceneManager.getScene().lookup("#firstCard");
        Label secondCard = (Label) SceneManager.getScene().lookup("#secondCard");
        Label thirdCard = (Label) SceneManager.getScene().lookup("#thirdCard");
        Label fourthCard = (Label) SceneManager.getScene().lookup("#fourthCard");

        firstCard.setStyle("-fx-background-image: url(\"images/leadercards/" +
                leaderCards.get(0).toPath() + ".png\");" +
                " -fx-background-size: 100% 100%;" +
                "-fx-border-width: 5");
        secondCard.setStyle("-fx-background-image: url(\"images/leadercards/" +
                leaderCards.get(1).toPath() + ".png\");" +
                " -fx-background-size: 100% 100%;" +
                "-fx-border-width: 5");
        thirdCard.setStyle("-fx-background-image: url(\"images/leadercards/" +
                leaderCards.get(2).toPath() + ".png\");" +
                " -fx-background-size: 100% 100%;" +
                "-fx-border-width: 5");
        fourthCard.setStyle("-fx-background-image: url(\"images/leadercards/" +
                leaderCards.get(3).toPath() + ".png\");" +
                " -fx-background-size: 100% 100%;" +
                "-fx-border-width: 5");

        return new EmptyMessage();
    }

    @Override
    public ClientMessage selectAction(String choice, boolean err) {
        return null;
    }


    @Override
    public ClientMessage storeResources(List<Resource> resources) {
        return null;
    }

    @Override
    public ClientMessage buyDVCard(ArrayList<DevelopmentCard> devCards, boolean err) {
        return null;
    }

    @Override
    public ClientMessage waitingRoom() {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/login/waitingRoom.fxml")));
            SceneManager.setScene(new Scene(root, 750, 500));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new EmptyMessage();
    }

    @Override
    public void endWaitingRoom() {

    }

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

    @Override
    public void showLeaderCards(List<LeaderCard> leaderCards) {
    }

    @Override
    public void showResources(List<Resource> resources) {
    }

    @Override
    public ClientMessage activateProduction() {
        return null;
    }

    @Override
    public void showParticipantsNumber(String s) {
        Label participantsNumber = (Label) SceneManager.getScene().lookup("#playersNumber");
        Platform.runLater(() -> participantsNumber.setText(s));
    }
}
