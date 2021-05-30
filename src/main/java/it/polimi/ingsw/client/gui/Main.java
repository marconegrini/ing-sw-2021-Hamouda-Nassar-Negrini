package it.polimi.ingsw.client.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/connectToServer.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();


        primaryStage.setTitle("Master of Renaissance");
        primaryStage.setScene(new Scene(root, 727, 395));
        primaryStage.setOnHidden(e -> controller.shutDown());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
