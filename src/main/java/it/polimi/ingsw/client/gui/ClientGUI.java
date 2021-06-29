package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.gui.controllers.login.ConnectionToServerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/login/connectToServer.fxml"));
        Parent root = loader.load();
        ConnectionToServerController controller = loader.getController();
        SceneManager.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Master of Renaissance");
        Scene scene = new Scene(root, 1080, 720);
        primaryStage.setScene(scene);
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.setOnHidden(e -> controller.shutDown());
        primaryStage.show();
    }


    //public static void main(String[] args) {
        //launch(args);
   // }
}
