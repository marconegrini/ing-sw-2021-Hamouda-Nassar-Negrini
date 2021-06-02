package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.gui.SceneManager;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromClient.EmptyMessage;
import it.polimi.ingsw.messages.fromClient.LoginMessage;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Resource;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GUIView extends View{

    Stage primaryStage;

    @Override
    public ClientMessage logClient() {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/login/loginInformation.fxml")));
            SceneManager.setScene(new Scene(root, 727, 395));
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
    public ClientMessage selectLeaderCards(List<LeaderCard> leaderCards){
        return null;
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
        return null;
    }

    @Override
    public void endWaitingRoom() {

    }

    @Override
    public void showMessage(String message) {}

    @Override
    public void showLeaderCards(List<LeaderCard> leaderCards) {}

    @Override
    public void showResources(List<Resource> resources) {}

    @Override
    public ClientMessage activateProduction() {
        return null;
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
}
