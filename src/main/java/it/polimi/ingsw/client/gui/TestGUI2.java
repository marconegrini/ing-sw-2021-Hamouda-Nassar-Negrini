package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.gui.controllers.setcalamaio.WaitingToGame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestGUI2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game/setcalamaio/waiting_game.fxml"));
        Parent root = loader.load();
        //ConnectionToServerController controller = loader.getController();
        SceneManager.setPrimaryStage(primaryStage);

        primaryStage.setTitle("TEST -- aster of Renaissance - TEST");
        Scene scene = new Scene(root, 1080, 720);
        primaryStage.setScene(scene);
        //primaryStage.setOnHidden(e -> controller.shutDown());
        primaryStage.show();
        WaitingToGame waitingToGame = new WaitingToGame();
//        waitingToGame.initialize();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
