package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.fromServer.BuyDVCardError;
import it.polimi.ingsw.messages.fromServer.OkMessage;
import it.polimi.ingsw.messages.fromServer.activateProduction.ProductionResultMessage;
import it.polimi.ingsw.messages.fromServer.leadercard.LeaderResultMessage;
import it.polimi.ingsw.messages.fromServer.storeResources.ErrorWarehouseMessage;
import it.polimi.ingsw.messages.fromServer.storeResources.ResourcesToStoreMessage;
import it.polimi.ingsw.messages.fromServer.warehouse.MoveResourcesResultMessage;
import it.polimi.ingsw.model.LeaderCardTest;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.CardType;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.model.parser.LeaderCardParser;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;
import it.polimi.ingsw.server.controller.TurnManager;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerTest1 {
    TurnManager turnManager;
    TurnManager singlePlayerTurnManager;
    CardsDeck cardsDeck;
    MarketBoard marketBoard;
    SinglePlayer player;
    MultiPlayer player1;
    MultiPlayer player2;
    MultiPlayer player3;
    MultiPlayer player4;
    List<LeaderCard> StorageLeaderCards;
    List<LeaderCard> DiscountLeaderCards;
    List<LeaderCard> WhiteMarbleLeaderCards;
    List<LeaderCard> ProdPowerLeaderCards;
    LeaderCard discountLeaderCard;
    LeaderCard storageLeaderCard;
    LeaderCard whiteMarbleLeaderCard;
    LeaderCard prodPowerLeaderCard;

    @Before
    public void setUp(){
        cardsDeck = new CardsDeck();
        cardsDeck.initializeCardsDeck();
        marketBoard = new MarketBoard();
        turnManager= new TurnManager(cardsDeck, marketBoard);
        singlePlayerTurnManager = new TurnManager(cardsDeck, marketBoard);
        player = new SinglePlayer("Marco");
        player1 = new MultiPlayer("amir");
        player2 = new MultiPlayer("adel");
        player3 = new MultiPlayer("teo");
        player4 = new MultiPlayer("alessio");
        List<MultiPlayer> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        LeaderCardParser leaderCardParser = new LeaderCardParser("src/main/java/it/polimi/ingsw/model/jsonFiles/LeaderCardJson.json");
        List<LeaderCard> leaderCards = leaderCardParser.getLeaderCardsDeck();
        leaderCardParser.close();
        StorageLeaderCards = leaderCards.stream()
                .filter(card->(card.getCardType().equals(CardType.STORAGE)))
                .collect(Collectors.toList());
        DiscountLeaderCards = leaderCards.stream()
                .filter(card->(card.getCardType().equals(CardType.DISCOUNT)))
                .collect(Collectors.toList());
        WhiteMarbleLeaderCards = leaderCards.stream()
                .filter(card->(card.getCardType().equals(CardType.MARBLE)))
                .collect(Collectors.toList());
        ProdPowerLeaderCards = leaderCards.stream()
                .filter(card->(card.getCardType().equals(CardType.PRODUCTION)))
                .collect(Collectors.toList());

        //5 COIN as activation cost
        //2 STONE as storage
        storageLeaderCard = StorageLeaderCards.get(0);

        //activation cost: 2 yellow card (level any), 1 blue card (level any)
        //production out = 1 SERVANT
        whiteMarbleLeaderCard = WhiteMarbleLeaderCards.get(0);

        //activation cost: 1 yellow card (level any), 1 green card (level any)
        //discounted resource: 1 SERVANT
        discountLeaderCard = DiscountLeaderCards.get(0);

        //production input cost: 1 SHIELD
        //activation cost: 1 yellow card (level second)
        prodPowerLeaderCard = ProdPowerLeaderCards.get(0);

        singlePlayerTurnManager.setMultiplayer(false);
        singlePlayerTurnManager.setPlayer(player);
        turnManager.setMultiplayer(true);
        turnManager.setPlayers(players);
    }

    @Test
            (expected = InsufficientResourcesException.class)
    public void pickResourcesTest() throws AlreadyActivatedLeaderCardException, InsufficientResourcesException, AlreadyDiscardedLeaderCardException {
        List<LeaderCard> lc = new ArrayList<>();
        lc.add(storageLeaderCard);
        lc.add(discountLeaderCard);
        player.setLeaderCards(lc);
        player.activateLeaderCard(0);
        singlePlayerTurnManager.pickResources(player, true, 1, true);
    }

    @Test
    public void pickResourcesTest2() throws AlreadyActivatedLeaderCardException, InsufficientResourcesException, AlreadyDiscardedLeaderCardException {
        List<LeaderCard> lc = new ArrayList<>();
        lc.add(storageLeaderCard);
        lc.add(discountLeaderCard);
        player.setLeaderCards(lc);
        List<Resource> resources = new ArrayList<>();
        resources.add(Resource.COIN);
        resources.add(Resource.COIN);
        resources.add(Resource.COIN);
        resources.add(Resource.COIN);
        resources.add(Resource.COIN);
        player.putCofferResources(resources);
        player.activateLeaderCard(0);
        String okMessage = "Obtained resources from market.";
        assertEquals(okMessage, ((ResourcesToStoreMessage) singlePlayerTurnManager.pickResources(player, true, 1, true)).getMessage());
    }

    @Test
    public void insertResourceInWarehouseTest(){
        List<Resource> resourceIn = new ArrayList<>();
        resourceIn.add(Resource.SHIELD);
        singlePlayerTurnManager.insertResourcesInWarehouse(player, 2, resourceIn, false);
        assertEquals(ResourcesToStoreMessage.class, singlePlayerTurnManager.insertResourcesInWarehouse(player, 2, resourceIn, false).getClass());
    }

    @Test
    public void insertResourceInWarehouseTest2(){
        List<Resource> resourceIn = new ArrayList<>();
        resourceIn.add(Resource.SHIELD);
        resourceIn.add(Resource.SHIELD);
        resourceIn.add(Resource.SHIELD);
        assertEquals(ErrorWarehouseMessage.class, singlePlayerTurnManager.insertResourcesInWarehouse(player, 2, resourceIn, false).getClass());
    }

    @Test
    public void insertResourceInWarehouseTest3(){
        List<Resource> resourceIn = new ArrayList<>();
        resourceIn.add(Resource.SHIELD);
        resourceIn.add(Resource.SHIELD);
        resourceIn.add(Resource.SHIELD);
        turnManager.insertResourcesInWarehouse(player1, 2, resourceIn, true);
        assertEquals(java.util.Optional.of(3), java.util.Optional.of(player2.getFaithPathPosition()));
        assertEquals(java.util.Optional.of(3), java.util.Optional.of(player3.getFaithPathPosition()));
        assertEquals(java.util.Optional.of(3), java.util.Optional.of(player4.getFaithPathPosition()));
    }

    @Test
    public void moveResourceInWarehouseTest(){
        List<Resource> resourceIn = new ArrayList<>();
        resourceIn.add(Resource.SHIELD);
        singlePlayerTurnManager.insertResourcesInWarehouse(player, 1, resourceIn, false);
        singlePlayerTurnManager.moveResourcesInWarehouse(player, 1, 2);
        assertEquals(false, ((MoveResourcesResultMessage) singlePlayerTurnManager.moveResourcesInWarehouse(player, 1, 2)).getError());
    }

    @Test
    public void moveResourceInWarehouseTest2(){
        List<Resource> resourceIn = new ArrayList<>();
        resourceIn.add(Resource.SHIELD);
        singlePlayerTurnManager.insertResourcesInWarehouse(player, 1, resourceIn, false);
        assertEquals(true, ((MoveResourcesResultMessage) singlePlayerTurnManager.moveResourcesInWarehouse(player, 1, 4)).getError());
    }

    @Test
    public void buyDEvCardTest() throws IllegalInsertionException, AlreadyActivatedLeaderCardException, InsufficientResourcesException, AlreadyDiscardedLeaderCardException {
        DevelopmentCard dvTest;
        HashMap<Resource, Integer> cardCost;
        HashMap<Resource, Integer> prodIn;
        HashMap<Resource, Integer> prodOut;
        cardCost = new HashMap<>();
        cardCost.put(Resource.SERVANT, 2);
        cardCost.put(Resource.COIN, 1);

        prodIn = new HashMap<>();
        prodIn.put(Resource.SERVANT, 2);

        prodOut = new HashMap<>();
        prodOut.put(Resource.COIN, 1);

        DevelopmentCard card1 = new DevelopmentCard(2, CardColor.YELLOW, Level.FIRST, cardCost, prodIn, prodOut);
        DevelopmentCard card2 = new DevelopmentCard(2, CardColor.GREEN, Level.FIRST, cardCost, prodIn, prodOut);
        player.addCardInDevCardSlot(1, card1);
        player.addCardInDevCardSlot(2, card2);


        List<Resource> resources = new ArrayList();
        resources.add(Resource.COIN);
        resources.add(Resource.COIN);
        resources.add(Resource.COIN);
        resources.add(Resource.SERVANT);
        resources.add(Resource.SERVANT);
        resources.add(Resource.SERVANT);
        resources.add(Resource.STONE);
        resources.add(Resource.STONE);
        resources.add(Resource.STONE);
        resources.add(Resource.SHIELD);
        resources.add(Resource.SHIELD);
        resources.add(Resource.SHIELD);
        player.putCofferResources(resources);

        List<LeaderCard> lc = new ArrayList();
        lc.add(discountLeaderCard);
        lc.add(storageLeaderCard);
        player.setLeaderCards(lc);
        player.activateLeaderCard(0);
        //"Bought development card and inserted in slot number 1. Leader card power used."
        assertEquals("Bought development card and inserted in slot number 1. Leader card power used.", ((OkMessage) singlePlayerTurnManager.buyDevelopmentCard(player, 0, 0,0)).getMessage());
    }

    @Test
    public void containsNeededResourceTest() throws StorageOutOfBoundsException, IllegalInsertionException {
        List<Resource> warehouseRes = new ArrayList<>();
        List<LeaderCard> lc = new ArrayList();
        lc.add(discountLeaderCard);
        lc.add(storageLeaderCard);
        player.setLeaderCards(lc);
        warehouseRes.add(Resource.SHIELD);
        warehouseRes.add(Resource.SHIELD);
        player.putWarehouseResources(2, warehouseRes);
        player.putCofferResources(warehouseRes);

        assertEquals(true, singlePlayerTurnManager.containsNeededResources(player, warehouseRes));
        warehouseRes.clear();
        warehouseRes.add(Resource.SERVANT);
        assertEquals(false, singlePlayerTurnManager.containsNeededResources(player, warehouseRes));
        warehouseRes.add(Resource.SHIELD);
        warehouseRes.add(Resource.SHIELD);
        assertEquals(false, singlePlayerTurnManager.containsNeededResources(player, warehouseRes));
    }




    /*

    @Test
    public void activateProductionTest(){
        List<Integer> slots = new ArrayList<>();
        slots.add(1);
        assertEquals(ProductionResultMessage.class, turnManager.activateProduction(player, slots, null).getClass());
        assertEquals(ProductionResultMessage.class, turnManager.activatePersonalProduction(player, Resource.COIN, Resource.SHIELD, Resource.STONE, null).getClass());
    }

    @Test
    public void activateLeaderCardProductionTest(){
        List<Resource> leaderResources = new ArrayList();
        leaderResources.add(Resource.COIN);
        assertEquals(false, turnManager.activateLeaderCardProduction(player, leaderResources));
    }

    @Test
    public void activateLeaderCardTest(){
        assertEquals(LeaderResultMessage.class, turnManager.activateLeaderCard(player, 0).getClass());
        assertEquals(LeaderResultMessage.class, turnManager.activateLeaderCard(player, 1).getClass());
    }

    @Test
    public void discardLeaderCardTest(){
        assertEquals(LeaderResultMessage.class, turnManager.discardLeaderCard(player, 0).getClass());
        assertEquals(LeaderResultMessage.class, turnManager.discardLeaderCard(player, 1).getClass());
    }

    @Test
    public void discardDevelopmentCard(){
        assertEquals(false, turnManager.discardDevelopmentCards(CardColor.GREEN));
        assertEquals(false, turnManager.discardDevelopmentCards(CardColor.GREEN));
        assertEquals(false, turnManager.discardDevelopmentCards(CardColor.GREEN));
        assertEquals(false, turnManager.discardDevelopmentCards(CardColor.GREEN));
        assertEquals(false, turnManager.discardDevelopmentCards(CardColor.GREEN));
        assertEquals(false, turnManager.discardDevelopmentCards(CardColor.GREEN));
        assertEquals(true, turnManager.discardDevelopmentCards(CardColor.GREEN));
    }

     */
}
