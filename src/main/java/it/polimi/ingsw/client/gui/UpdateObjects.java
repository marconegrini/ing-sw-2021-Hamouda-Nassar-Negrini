package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.Coffer;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.model.enumerations.Resource;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is used to update the element of the GUI. Each method is responsible of updating a single element of the GUI.
 */

public class UpdateObjects {

    /**
     * This method updates the warehouse adding graphically the new resources received from the server.
     * @param warehouse  is the new warehouse that will be used to update the old one.
     */
    public static void updateWarehouse(Warehouse warehouse){
        Set<Integer> keys = warehouse.getClonedWarehouse().keySet();

        for (Integer shelf: keys){
            GridPane gridShelf = (GridPane) SceneManager.getScene().lookup("#shelf"+shelf);
            Storage storage = warehouse.getClonedWarehouse().get(shelf);
            List<Resource> resources = storage.getResources();
            if (resources == null)  continue;

            AtomicInteger col = new AtomicInteger(0);
            for (Resource resource: resources){
                Label label = new Label();
                label.getStyleClass().add(resource.toString().toLowerCase());
                label.getStyleClass().add("notSelectedCard");
                label.setPrefHeight(74.0);
                label.setPrefWidth(74.0);
                Platform.runLater(()->{gridShelf.add(label, col.getAndIncrement(), 0);});
            }
        }
    }

    /**
     * This method updates the coffer adding graphically the new resources received from the server.
     * @param coffer  is the new coffer that will be used to update the old one.
     */
    public static void updateCoffer(Coffer coffer){
        Set<Resource> keys = coffer.getClonedCoffer().keySet();
        for (Resource resource: keys){
            Label label = (Label) SceneManager.getScene().lookup("#num"+resource.toString().toLowerCase());
            Integer numResource = coffer.getClonedCoffer().get(resource);
            Platform.runLater(() -> {label.setText(numResource.toString());});
        }
    }

    /**
     * This method updates the faith path moving graphically the roods.
     * @param position  the position of the player that received the update message
     */
    public static void updateFaithPath(Integer position){
        GridPane gridShelf = (GridPane) SceneManager.getScene().lookup("#faithPathGrid");
        Label rood = (Label) SceneManager.getScene().lookup("#rood");
        Platform.runLater(() -> {gridShelf.add(rood, getFaithPathCol(position), getFaithPathRow(position));});
    }

    /**
     * takes the position of the player and get back the row in the grid in which the rood should be put
     * @param position  The position in the faith path
     * @return  the row in the gridPane
     */
    private static Integer getFaithPathRow(Integer position){
        if ((position >= 0 && position <= 2) || (position >= 11 && position <= 16)) return  2;
        if ((position >= 4 && position <= 9) || (position >= 18 && position <= 24)) return  0;
        return  1;
    }

    /**
     * takes the position of the player and get back the column in the grid in which the rood should be put
     * @param position  The position in the faith path
     * @return  the column in the gridPane
     */
    private static Integer getFaithPathCol(Integer position){
        if (position >= 2 && position <= 4)     return 2;
        if (position >= 9 && position <= 11)    return 7;
        if (position >= 16 && position <= 18)   return 12;
        if (position >= 0 && position <= 1)    return position;
        if (position >= 5 && position <= 8)    return position-2;
        if (position >= 12 && position <= 15)    return position-4;
        return position-6;
    }


}
