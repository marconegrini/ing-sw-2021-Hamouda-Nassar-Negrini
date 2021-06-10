package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.gui.controllers.login.ConnectionToServerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game/setcalamaio/selectOneResource.fxml"));
        Parent root = loader.load();
        //ConnectionToServerController controller = loader.getController();
        SceneManager.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Master of Renaissance");
        Scene scene = new Scene(root, 1080, 720);
        primaryStage.setScene(scene);
        //primaryStage.setOnHidden(e -> controller.shutDown());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
