package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.CLI.MarketTracer;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Resource;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is used to update the element of the GUI. Each method is responsible of updating a single element of the GUI.
 */

public class UpdateObjects {

    /**
     * This method updates the warehouse adding graphically the new resources received from the server.
     *
     * @param warehouse is the new warehouse that will be used to update the old one.
     */
    public static void updateWarehouse(Warehouse warehouse) {
        Set<Integer> keys = warehouse.getClonedWarehouse().keySet();

        for (Integer shelf : keys) {
            GridPane gridShelf = (GridPane) SceneManager.getScene().lookup("#shelf" + shelf);
            Storage storage = warehouse.getClonedWarehouse().get(shelf);
            List<Resource> resources = storage.getResources();
            if (resources == null) continue;

            AtomicInteger col = new AtomicInteger(0);
            for (Resource resource : resources) {
                Label label = new Label();
                label.getStyleClass().add(resource.toString().toLowerCase());
                label.getStyleClass().add("notSelectedCard");
                label.setPrefHeight(74.0);
                label.setPrefWidth(74.0);
                Platform.runLater(() -> {
                    gridShelf.add(label, col.getAndIncrement(), 0);
                });
            }
        }
    }

    /**
     * This method updates the warehouse adding graphically the new resources received from the server.
     *
     * @param warehouse is the new warehouse that will be used to update the old one.
     */
    public static void updateWarehouse(Warehouse warehouse, Scene scene) {
        Set<Integer> keys = warehouse.getClonedWarehouse().keySet();

        for (Integer shelf : keys) {
            GridPane gridShelf = (GridPane) scene.lookup("#shelf" + shelf);
            Storage storage = warehouse.getClonedWarehouse().get(shelf);
            List<Resource> resources = storage.getResources();
            if (resources == null) continue;

            AtomicInteger col = new AtomicInteger(0);
            for (Resource resource : resources) {
                Label label = new Label();
                label.getStyleClass().add(resource.toString().toLowerCase());
                label.getStyleClass().add("notSelectedCard");
                label.setPrefHeight(74.0);
                label.setPrefWidth(74.0);
                Platform.runLater(() -> {
                    gridShelf.add(label, col.getAndIncrement(), 0);
                });
            }
        }
    }

    /**
     * This method updates the coffer adding graphically the new resources received from the server.
     *
     * @param coffer is the new coffer that will be used to update the old one.
     */
    public static void updateCoffer(Coffer coffer) {
        Set<Resource> keys = coffer.getClonedCoffer().keySet();
        for (Resource resource : keys) {
            Label label = (Label) SceneManager.getScene().lookup("#num" + resource.toString().toLowerCase());
            Integer numResource = coffer.getClonedCoffer().get(resource);
            Platform.runLater(() -> {
                label.setText(numResource.toString());
            });
        }
    }

    /**
     * This method updates the faith path moving graphically the roods.
     *
     * @param position the position of the player that received the update message
     */
    public static void updateFaithPath(Integer position) {
        Platform.runLater(() -> {
            Label rood = (Label) SceneManager.getScene().lookup("#rood");
            GridPane.setRowIndex(rood, getFaithPathRow(position));
            GridPane.setColumnIndex(rood, getFaithPathCol(position));
        });
    }

    /**
     * takes the position of the player and get back the row in the grid in which the rood should be put
     *
     * @param position The position in the faith path
     * @return the row in the gridPane
     */
    private static Integer getFaithPathRow(Integer position) {
        if ((position >= 0 && position <= 2) || (position >= 11 && position <= 16)) return 2;
        if ((position >= 4 && position <= 9) || (position >= 18 && position <= 24)) return 0;
        return 1;
    }

    /**
     * takes the position of the player and get back the column in the grid in which the rood should be put
     *
     * @param position The position in the faith path
     * @return the column in the gridPane
     */
    private static Integer getFaithPathCol(Integer position) {
        if (position >= 2 && position <= 4) return 2;
        if (position >= 9 && position <= 11) return 7;
        if (position >= 16 && position <= 18) return 12;
        if (position >= 0 && position <= 1) return position;
        if (position >= 5 && position <= 8) return position - 2;
        if (position >= 12 && position <= 15) return position - 4;
        return position - 6;
    }

    /**
     * This method updates the grid of development cards in the GUI.
     * @param cardsInSlot  Are the new cards that are on the top of the development cards slots
     */
    public static void updateDevCardsSlot(HashMap<Integer, DevelopmentCard> cardsInSlot) {

        Set<Integer> keys = cardsInSlot.keySet();
        GridPane devCardsSlotsGrid = (GridPane) SceneManager.getScene().lookup("#devCardsSlots");

        Platform.runLater(() -> {
            for (Integer index : keys) {
                Label card = new Label();
                card.setPrefWidth(200);
                card.setPrefHeight(400);
                card.setStyle("-fx-background-image: url(\"images/devcards/" +
                        cardsInSlot.get(index - 1).toPath() + ".png\");" +
                        " -fx-background-size: 100% 100%;" +
                        "-fx-border-width: 5");
                devCardsSlotsGrid.add(card, index - 1, 0);
            }
        });
    }

    public static void updateMarketBoard(MarketBoard marketBoard, Scene scene){
        GridPane marketboardGrid = (GridPane) scene.lookup("#marketboardGrid");
        Label externalMarble = (Label) scene.lookup("#externalMarble");
        externalMarble.setStyle("-fx-background-image: url(\"images/marbles/" +
                marketBoard.getExternalMarbleColor().toString().toLowerCase() + ".png\");" +
                " -fx-background-size: 100% 100%;");
        Marble[][] marbles = marketBoard.getMarketBoardMarbles();
        for (int i=0; i<3; i++){
            for (int j=0; j<4; j++){
                Label marble = new Label();
                marble.setPrefWidth(55);
                marble.setPrefHeight(55);
                marble.setStyle("-fx-background-image: url(\"images/marbles/" +
                        marbles[i][j].getColor().toString().toLowerCase() + ".png\");" +
                        " -fx-background-size: 100% 100%;");
                marketboardGrid.add(marble, j, i);
            }
        }
        MarketTracer mr  = new MarketTracer();
        mr.marketTracer(marketBoard);
    }

    public static void updateLeaderCards (List<LeaderCard> leaderCards, Scene scene){
        for (int i=0; i<2; i++){
            Label card = (Label) scene.lookup("#leaderCard"+(i+1));
            card.setStyle("-fx-background-image: url(\"images/leadercards/" +
                    leaderCards.get(i).toPath() + ".png\");" +
                    " -fx-background-size: 100% 100%;" +
                    "-fx-border-width: 5");
        }
    }
}










































