package it.polimi.ingsw.client.gui;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;

public class SceneManager {

    private static Stage primaryStage;
    private static Scene scene;


    public static void setScene(Scene newScene){
        scene = newScene;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                primaryStage.setScene(newScene);
            }
        });
    }

    public static void setScene(Parent root){
        Platform.runLater(() -> { primaryStage.setScene(new Scene(root, 1080, 730)); });
    }

    public static void setPrimaryStage(Stage stage){
        primaryStage = stage;
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void setLabelText(String s){
        Label participantsNumber = (Label) primaryStage.getScene().lookup(s);
    }

    public static Scene getScene(){
        return scene;
    }

}
