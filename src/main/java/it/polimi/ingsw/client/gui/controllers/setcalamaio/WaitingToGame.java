package it.polimi.ingsw.client.gui.controllers.setcalamaio;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class WaitingToGame {

    @FXML
    private Ellipse ellipse;

    @FXML
    private Path path;

    @FXML
    private Circle circle2;

    @FXML
    private Circle circle1;

//
//
//    public  void initialize(){
//        PathTransition pt1 = new PathTransition(Duration.seconds(4), ellipse, circle1);
//        PathTransition pt2 = new PathTransition(Duration.seconds(5), ellipse, circle2);
//        pt1.setCycleCount(PathTransition.INDEFINITE);
//        pt2.setCycleCount(PathTransition.INDEFINITE);
//
//        pt1.play();
//        pt2.play();
//    }

}
