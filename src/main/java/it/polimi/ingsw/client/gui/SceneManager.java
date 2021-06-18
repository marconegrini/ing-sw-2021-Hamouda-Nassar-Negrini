package it.polimi.ingsw.client.gui;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This class allows you to set a new Scene from everywhere in the code
 */

public class SceneManager {

    private static Stage primaryStage;
    private static Scene scene;


    /**
     * Sets a new Scene with the Scene in the parameter
     * @param newScene  The new Scene to be set
     */
    public static void setScene(Scene newScene){
        scene = newScene;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                primaryStage.setScene(newScene);
            }
        });
    }

    /**
     * Set a Scene from a Parent
     * @param root  A Parent object that will be used to set the new Sceme
     */
    public static void setScene(Parent root){
        Platform.runLater(() -> { primaryStage.setScene(new Scene(root, 1080, 730)); });
    }

    /**
     * Used to set a new Stage
     * @param stage  The Stage to be set
     */
    public static void setPrimaryStage(Stage stage){
        primaryStage = stage;
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    /**
     * Method used to get the current Scene that is showed
     * @return  The current showed Scene
     */
    public static Scene getScene(){
        return scene;
    }

}
