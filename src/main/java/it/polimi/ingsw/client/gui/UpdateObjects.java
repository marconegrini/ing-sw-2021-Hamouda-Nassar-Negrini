package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.gui.controllers.ControllerGUI;
import it.polimi.ingsw.enumerations.LorenzoCardType;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.StorageLeaderCard;
import it.polimi.ingsw.enumerations.CardType;
import it.polimi.ingsw.enumerations.Resource;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * This class is used to update the element of the GUI. Each method is responsible of updating a single element of the GUI.
 */

public class UpdateObjects {

    private static boolean firstTime = true;

    /**
     * This method updates the warehouse adding graphically the new resources received from the server.
     *
     * @param warehouse is the new warehouse that will be used to update the old one.
     */
    public static void updateWarehouse(Warehouse warehouse) {
        Set<Integer> keys = warehouse.getClonedWarehouse().keySet();

        for (Integer shelf : keys) {
            GridPane gridShelf = (GridPane) SceneManager.getScene().lookup("#shelf" + shelf);
            Platform.runLater(() -> {
                gridShelf.getChildren().clear();
            });
            Storage storage = warehouse.getClonedWarehouse().get(shelf);
            List<Resource> resources = storage.getResources();
            if (resources == null) continue;

            AtomicInteger col = new AtomicInteger(0);
            for (Resource resource : resources) {
                Label label = new Label();
                label.getStyleClass().add(resource.toString().toLowerCase());
                label.setPrefHeight(70.0);
                label.setPrefWidth(70.0);
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
            Platform.runLater(() -> {
                gridShelf.getChildren().clear();
            });
            Storage storage = warehouse.getClonedWarehouse().get(shelf);
            List<Resource> resources = storage.getResources();
            if (resources == null) continue;

            AtomicInteger col = new AtomicInteger(0);
            for (Resource resource : resources) {
                Label label = new Label();
                label.getStyleClass().add(resource.toString().toLowerCase());
                label.setPrefHeight(70.0);
                label.setPrefWidth(70.0);
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
     * This method updates the coffer adding graphically the new resources received from the server.
     *
     * @param coffer is the new coffer that will be used to update the old one.
     * @param scene  is the Scene to which you want to update the coffer.
     */
    public static void updateCoffer(Coffer coffer, Scene scene) {
        Set<Resource> keys = coffer.getClonedCoffer().keySet();
        for (Resource resource : keys) {
            Label label = (Label) scene.lookup("#num" + resource.toString().toLowerCase());
            Integer numResource = coffer.getClonedCoffer().get(resource);
            Platform.runLater(() -> {
                label.setText(numResource.toString());
            });
        }
    }

    /**
     * This method updates the coffer adding graphically the new resources received from the server.
     *
     * @param coffer    is the new coffer that will be used to update the view.
     * @param warehouse is the new warehouse that will be used to update the view.
     * @param scene     is the Scene to which you want to update the objects.
     */
    public static void updateResources(Coffer coffer, Warehouse warehouse, Scene scene) {
        HashMap<Resource, Integer> resourcesMap = new HashMap<>();
        resourcesMap.put(Resource.COIN, 0);
        resourcesMap.put(Resource.STONE, 0);
        resourcesMap.put(Resource.SHIELD, 0);
        resourcesMap.put(Resource.SERVANT, 0);

        List<Resource> totalResource = warehouse.getTotalResources().stream().collect(Collectors.toList());
        totalResource.addAll(coffer.getTotalResources().stream().collect(Collectors.toList()));
        for (LeaderCard ld : ControllerGUI.getServerHandler().getLightModel().getLeaderCards()) {
            if (ld.getCardType().equals(CardType.STORAGE)) {
                if (ld.isActivated()) {
                    StorageLeaderCard sld = (StorageLeaderCard) ld;
                    if (sld.getOccupiedSlots() > 0) {
                        totalResource.addAll(sld.getStoredResources());
                    }
                }
            }
        }

        for (Resource resource : totalResource) {
            resourcesMap.computeIfPresent(resource, (k, v) -> v + 1);
        }

        Set<Resource> resourcesSet = resourcesMap.keySet();
        for (Resource resource : resourcesSet) {
            Label label = (Label) scene.lookup("#num" + resource.toString().toLowerCase());
            Integer numResource = resourcesMap.get(resource);
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
    public static void updateFaithPath(HashMap<String, Integer> othersPositions, Integer position) {
        Platform.runLater(() -> {
            Label rood = (Label) SceneManager.getScene().lookup("#rood");
            GridPane.setRowIndex(rood, getFaithPathRow(position));
            GridPane.setColumnIndex(rood, getFaithPathCol(position));
        });

        Set<String> players = othersPositions.keySet();

        Platform.runLater(() -> {
            if (ControllerGUI.getServerHandler().getIsMultiplayer()) {
                if (firstTime) {
                    firstTime = false;
                    GridPane faithPathGrid = (GridPane) SceneManager.getScene().lookup("#faithPathGrid");
                    for (String player : players) {
                        Label rood = new Label();
                        rood.setId("rood" + player);
                        rood.setPrefWidth(50);
                        rood.setPrefHeight(50);

                        String zeros = "000000";
                        Random random = new Random();
                        String s = Integer.toString(random.nextInt(0X1000000), 16);
                        s = zeros.substring(s.length()) + s;
                        rood.setStyle("-fx-shape: \"M 300 100 Q 400 100 400 150 Q 400 200 300 200 Q 300 200 300 250 L 300 300 L 300 350 Q 300 400 250 400 L 250 400 Q 200 400 200 350 L 200 250 L 200 200 Q 200 200 150 200 Q 100 200 100 150 L 100 150 Q 100 100 200 100 Q 200 100 200 50 Q 200 0 250 0 L 250 0 Q 300 0 300 50 L 300 100 \";"
                                + "-fx-border-width: 1;" + "-fx-background-color: #" + s + ";-fx-border-color: #000000;" +
                                "-fx-stroke: firebrick;-fx-stroke-width: 2px;"
                        );

                        faithPathGrid.add(rood, 0, 2);
                        rood.getStyleClass().add("outline");
                    }
                }
            }

            for (String player : players) {
                Label rood = (Label) SceneManager.getScene().lookup("#rood" + player);
                GridPane.setColumnIndex(rood, getFaithPathCol(othersPositions.get(player)));
                GridPane.setRowIndex(rood, getFaithPathRow(othersPositions.get(player)));
                ;
            }
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
     *
     * @param cardsInSlot Are the new cards that are on the top of the development cards slots
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
                        cardsInSlot.get(index).toPath() + ".png\");" +
                        " -fx-background-size: 100% 100%;" +
                        "-fx-border-width: 5");
                devCardsSlotsGrid.add(card, index, 0);
            }
        });
    }

    /**
     * This method updates the grid of development cards in the GUI.
     *
     * @param cardsInSlot Are the new cards that are on the top of the development cards slots
     * @param scene       The Scene in which the update will be done
     * @param putFrame    Is used to put a blue frame in the empty slots
     */
    public static void updateDevCardsSlot(HashMap<Integer, DevelopmentCard> cardsInSlot, Scene scene, boolean putFrame) {

        Set<Integer> keys = cardsInSlot.keySet();
        GridPane devCardsSlotsGrid = (GridPane) scene.lookup("#devCardsSlots");

        Platform.runLater(() -> {

            for (int i = 0; i < 3; i++) {
                if (keys.contains(i)) {
                    Label card = new Label();
                    card.setId("card" + i);
                    card.setPrefWidth(230);
                    card.setPrefHeight(400);
                    card.setStyle("-fx-background-image: url(\"images/devcards/" +
                            cardsInSlot.get(i).toPath() + ".png\");" +
                            " -fx-background-size: 100% 100%;" +
                            "-fx-border-width: 5");
                    devCardsSlotsGrid.add(card, i, 0);
                } else if (putFrame) {
                    Label card = new Label();
                    card.setId("card" + i);
                    card.setPrefWidth(230);
                    card.setPrefHeight(400);
                    card.setStyle("-fx-border-width: 5; -fx-border-color: #1d1d2b");
                    devCardsSlotsGrid.add(card, i, 0);
                }
            }

            for (Integer index : keys) {
                Label card = new Label();
                card.setId("card" + index);
                card.setPrefWidth(230);
                card.setPrefHeight(400);
                card.setStyle("-fx-background-image: url(\"images/devcards/" +
                        cardsInSlot.get(index).toPath() + ".png\");" +
                        " -fx-background-size: 100% 100%;" +
                        "-fx-border-width: 5");
                devCardsSlotsGrid.add(card, index, 0);
            }
        });
    }

    public static void updateMarketBoard(MarketBoard marketBoard, Scene scene) {
        GridPane marketboardGrid = (GridPane) scene.lookup("#marketboardGrid");
        Label externalMarble = (Label) scene.lookup("#externalMarble");
        externalMarble.setStyle("-fx-background-image: url(\"images/marbles/" +
                marketBoard.getExternalMarbleColor().toString().toLowerCase() + ".png\");" +
                " -fx-background-size: 100% 100%;");
        Marble[][] marbles = marketBoard.getMarketBoardMarbles();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                Label marble = new Label();
                marble.setPrefWidth(55);
                marble.setPrefHeight(55);
                marble.setStyle("-fx-background-image: url(\"images/marbles/" +
                        marbles[i][j].getColor().toString().toLowerCase() + ".png\");" +
                        " -fx-background-size: 100% 100%;");
                marketboardGrid.add(marble, j, i);
            }
        }
    }

    public static void updateLeaderCards(List<LeaderCard> leaderCards, Scene scene) {


        for (int i = 0; i < 2; i++) {
            try {
                Label card = (Label) scene.lookup("#leaderCard" + (i + 1));
                card.setStyle("-fx-background-image: url(\"images/leadercards/" +
                        leaderCards.get(i).toPath() + ".png\");" +
                        " -fx-background-size: 100% 100%;" +
                        "-fx-border-width: 5");

                if (leaderCards.get(i).getCardType().equals(CardType.STORAGE) && leaderCards.get(i).isActivated()) {
                    StorageLeaderCard storageLeaderCard = (StorageLeaderCard) leaderCards.get(i);
                    Resource leaderResource = storageLeaderCard.storageType();

                    int maxCapacity = storageLeaderCard.getMaxCapacity();
                    Integer capacity = storageLeaderCard.getLeaderCardPower().get(leaderResource);
                    if (capacity == null) capacity = 0;
                    int emptyCap = maxCapacity - capacity;
                    int occupiedSlots = maxCapacity - emptyCap;

                    for (int j = 1; j <= occupiedSlots; j++) {
                        Label slot = (Label) scene.lookup("#resource" + (i + 1) + j);
                        slot.getStyleClass().add(leaderResource.toString().toLowerCase());
                    }
                }
                if (leaderCards.get(i).isActivated()) {
                    card.getStyleClass().remove("notSelectedCard");
                    card.getStyleClass().add("selectedCard");
                    card.setOpacity(1.0);
                } else if (leaderCards.get(i).isDiscarded()) {
                    card.setOpacity(0.9);
                    card.getStyleClass().remove("notSelectedCard");
                    card.getStyleClass().add("redFrame");
                } else card.setOpacity(0.9);
            } catch (NullPointerException e) {

            }
        }
    }

    public static void updateDevCardsDeck(ArrayList<DevelopmentCard> devCards, Scene scene) {


        int i = 0;
        for (DevelopmentCard card : devCards) {
            Label label = (Label) scene.lookup("#card" + i);
            try {
                label.setStyle("-fx-background-image: url(\"images/devcards/" +
                        card.toPath() + ".png\");" +
                        " -fx-background-size: 100% 100%;" +
                        "-fx-border-width: 5");
            } catch (NullPointerException e) {
                label.setStyle("-fx-background-image: url(\"images/devcards/backCard.png\");" +
                        " -fx-background-size: 100% 100%;" +
                        "-fx-border-width: 5");
            }
            i++;
        }

    }

    public static void updatePopeCards(List<VaticanSection> vaticanSections, Scene scene) {
        for (int i = 0; i < 3; i++) {
            Label card = (Label) scene.lookup("#popeCard" + i);
            if (vaticanSections.get(i).isCardFlipped()) {
                card.getStyleClass().add("popeCardFront" + (i + 2));
                if (vaticanSections.get(i).isActivated()) {
                    card.getStyleClass().add("greenFrame");
                }
            } else {
                card.getStyleClass().add("popeCardBack" + (i + 2));
                if (vaticanSections.get(i).isActivated()) {
                    card.getStyleClass().add("redFrame");
                }
            }
        }
    }

    public static void updateLorenzoCard(LorenzoCardType lorenzoCardType, Scene scene) {
        Label card = (Label) scene.lookup("#lorenzoCard");
        card.setStyle("-fx-background-image: url(\"images/singleplayer/" +
                lorenzoCardType.toString().toLowerCase() + ".png\");" +
                " -fx-background-size: 100% 100%;" +
                "-fx-border-width: 5");
    }

}










































