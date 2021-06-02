package it.polimi.ingsw.client.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private static Stage primaryStage;

    public SceneManager(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public static void setScene(Scene scene){
        primaryStage.setScene(scene);
    }

    public static void setPrimaryStage(Stage stage){
        primaryStage = stage;
    }


}
