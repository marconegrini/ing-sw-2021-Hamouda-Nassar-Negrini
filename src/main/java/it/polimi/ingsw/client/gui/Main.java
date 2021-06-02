package it.polimi.ingsw.client.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/login/connectToServer.fxml"));
        Parent root = loader.load();
        ConnectionToServerGUI controller = loader.getController();
        //SceneManager.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Master of Renaissance");
        Scene scene = new Scene(root, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setOnHidden(e -> controller.shutDown());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
