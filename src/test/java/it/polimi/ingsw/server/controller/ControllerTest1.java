package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.messages.fromServer.OkMessage;
import it.polimi.ingsw.messages.fromServer.leadercard.LeaderResultMessage;
import it.polimi.ingsw.messages.fromServer.storeResources.ErrorWarehouseMessage;
import it.polimi.ingsw.messages.fromServer.storeResources.ResourcesToStoreMessage;
import it.polimi.ingsw.messages.fromServer.warehouse.MoveResourcesResultMessage;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.CardType;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.model.parser.LeaderCardParser;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;

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
        LeaderCardParser leaderCardParser = new LeaderCardParser();
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

    @Test
    public void pullNeededResource() throws StorageOutOfBoundsException, IllegalInsertionException {
        List<Resource> warehouseRes = new ArrayList<>();
        List<LeaderCard> lc = new ArrayList();
        lc.add(discountLeaderCard);
        lc.add(storageLeaderCard);
        player.setLeaderCards(lc);
        warehouseRes.add(Resource.SHIELD);
        warehouseRes.add(Resource.SHIELD);
        player.putWarehouseResources(2, warehouseRes);
        player.putCofferResources(warehouseRes);

        assertEquals(false, singlePlayerTurnManager.pullNeededResources(player, warehouseRes));
        warehouseRes.clear();
        warehouseRes.add(Resource.SERVANT);
        assertEquals(false, singlePlayerTurnManager.pullNeededResources(player, warehouseRes));
        warehouseRes.add(Resource.SHIELD);
        warehouseRes.add(Resource.SHIELD);
        assertEquals(false, singlePlayerTurnManager.pullNeededResources(player, warehouseRes));

    }

    @Test
    public void activateProductionTest() throws IllegalInsertionException, StorageOutOfBoundsException {
        List<LeaderCard> lc = new ArrayList();
        lc.add(discountLeaderCard);
        lc.add(storageLeaderCard);
        player1.setLeaderCards(lc);

        HashMap<Resource, Integer> cardCost;
        HashMap<Resource, Integer> prodIn;
        HashMap<Resource, Integer> prodOut;
        cardCost = new HashMap<>();
        cardCost.put(Resource.SERVANT, 1);
        cardCost.put(Resource.COIN, 1);

        prodIn = new HashMap<>();
        prodIn.put(Resource.SERVANT, 1);
        prodIn.put(Resource.COIN, 1);

        prodOut = new HashMap<>();
        prodOut.put(Resource.SHIELD, 1);
        prodOut.put(Resource.FAITH, 1);

        DevelopmentCard card1 = new DevelopmentCard(2, CardColor.YELLOW, Level.FIRST, cardCost, prodIn, prodOut);
        DevelopmentCard card2 = new DevelopmentCard(2, CardColor.GREEN, Level.FIRST, cardCost, prodIn, prodOut);
        player1.addCardInDevCardSlot(1, card1);
        player1.addCardInDevCardSlot(2, card2);

        List<Integer> slots = new ArrayList<>();
        slots.add(1);
        slots.add(2);
        List<Resource> resources = new ArrayList();
        resources.add(Resource.COIN);
        resources.add(Resource.COIN);
        player1.putWarehouseResources(2, resources);
        resources.clear();
        resources.add(Resource.SERVANT);
        resources.add(Resource.SERVANT);
        player1.putWarehouseResources(3, resources);
        turnManager.activateProduction(player1, slots, null);
        resources.clear();
        resources.add(Resource.SHIELD);
        resources.add(Resource.SHIELD);
        //assertEquals(resources, player1.getCofferResource());
        assertEquals(java.util.Optional.of(2), java.util.Optional.of(player1.getFaithPathPosition()));
    }

    @Test
    public void activatePersonalProductionTest() throws StorageOutOfBoundsException, IllegalInsertionException {
        List<LeaderCard> lc = new ArrayList();
        lc.add(discountLeaderCard);
        lc.add(storageLeaderCard);
        player1.setLeaderCards(lc);
        List<Resource> resources = new ArrayList<>();
        resources.add(Resource.SHIELD);
        player1.putWarehouseResources(1, resources);
        resources.clear();
        resources.add(Resource.STONE);
        player1.putWarehouseResources(2, resources);
        resources.clear();
        resources.add(Resource.COIN);
        player1.putWarehouseResources(3, resources);
        resources.add(Resource.COIN);
        turnManager.activatePersonalProduction(player1, Resource.SHIELD, Resource.STONE, Resource.COIN, null, false, true);
        assertEquals(resources, player1.getTotalResource());

    }

    @Test
    public void activateLeaderCardProductionTest() throws AlreadyActivatedLeaderCardException, InsufficientResourcesException, AlreadyDiscardedLeaderCardException, IllegalInsertionException, StorageOutOfBoundsException {
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

        DevelopmentCard card2 = new DevelopmentCard(2, CardColor.YELLOW, Level.SECOND, cardCost, prodIn, prodOut);
        DevelopmentCard card1 = new DevelopmentCard(2, CardColor.YELLOW, Level.FIRST, cardCost, prodIn, prodOut);
        player1.addCardInDevCardSlot(1, card1);
        player1.addCardInDevCardSlot(1, card2);


        List<LeaderCard> lc = new ArrayList();
        lc.add(prodPowerLeaderCard);
        lc.add(storageLeaderCard);
        player1.setLeaderCards(lc);
        player1.activateLeaderCard(0);

        List<Resource> resources = new ArrayList<>();
        resources.add(Resource.SHIELD);
        player1.putWarehouseResources(1, resources);

        assertEquals(true, turnManager.activateLeaderCardProduction(player1, resources));
        assertEquals(resources, player1.getCofferResource());
        assertEquals(java.util.Optional.of(1), java.util.Optional.of(player1.getFaithPathPosition()));
        resources.add(Resource.SHIELD);
        assertEquals(false, turnManager.containsNeededResources(player1, resources));
    }

    @Test
    public void discardLeaderCardTest() {
        List<LeaderCard> lc = new ArrayList();
        lc.add(prodPowerLeaderCard);
        lc.add(storageLeaderCard);
        player1.setLeaderCards(lc);
        turnManager.selectLeaderCard(player1, 0, 1);
        turnManager.discardLeaderCard(player1, 0);
        turnManager.discardLeaderCard(player1, 1);
        assertEquals(java.util.Optional.of(2), java.util.Optional.of(player1.getFaithPathPosition()));
    }

    @Test
    public void discardLeaderCardTest2() {
        List<LeaderCard> lc = new ArrayList();
        lc.add(prodPowerLeaderCard);
        lc.add(storageLeaderCard);
        player1.setLeaderCards(lc);
        List<Resource> resourcesIn = new ArrayList<>();
        resourcesIn.add(Resource.COIN);
        resourcesIn.add(Resource.COIN);
        resourcesIn.add(Resource.COIN);
        resourcesIn.add(Resource.COIN);
        resourcesIn.add(Resource.COIN);
        player1.putCofferResources(resourcesIn);
        turnManager.activateLeaderCard(player1, 1);
        assertEquals(true, ((LeaderResultMessage) turnManager.discardLeaderCard(player1, 1)).getError());
    }

    @Test
    public void discardLeaderCardTest3() {
        List<LeaderCard> lc = new ArrayList();
        lc.add(prodPowerLeaderCard);
        lc.add(storageLeaderCard);
        player1.setLeaderCards(lc);
        turnManager.discardLeaderCard(player1, 1);
        assertEquals(true, ((LeaderResultMessage) turnManager.discardLeaderCard(player1, 1)).getError());
    }

    @Test
    public void discardLeaderCardTest4() {
        List<LeaderCard> lc = new ArrayList();
        lc.add(prodPowerLeaderCard);
        lc.add(storageLeaderCard);
        player1.setLeaderCards(lc);
        assertEquals(true, ((LeaderResultMessage) turnManager.discardLeaderCard(player1, 5)).getError());
    }

    @Test
    public void discardDevelopmentCardsTest(){
        turnManager.discardDevelopmentCards(CardColor.BLUE);
        turnManager.discardDevelopmentCards(CardColor.BLUE);
        turnManager.discardDevelopmentCards(CardColor.BLUE);
        turnManager.discardDevelopmentCards(CardColor.BLUE);
        turnManager.discardDevelopmentCards(CardColor.BLUE);
        turnManager.discardDevelopmentCards(CardColor.BLUE);
        assertEquals(true, turnManager.discardDevelopmentCards(CardColor.BLUE));
        turnManager.discardDevelopmentCards(CardColor.GREEN);
        turnManager.discardDevelopmentCards(CardColor.GREEN);
        turnManager.discardDevelopmentCards(CardColor.GREEN);
        turnManager.discardDevelopmentCards(CardColor.GREEN);
        turnManager.discardDevelopmentCards(CardColor.GREEN);
        turnManager.discardDevelopmentCards(CardColor.GREEN);
        assertEquals(true, turnManager.discardDevelopmentCards(CardColor.GREEN));
        turnManager.discardDevelopmentCards(CardColor.YELLOW);
        turnManager.discardDevelopmentCards(CardColor.YELLOW);
        turnManager.discardDevelopmentCards(CardColor.YELLOW);
        turnManager.discardDevelopmentCards(CardColor.YELLOW);
        turnManager.discardDevelopmentCards(CardColor.YELLOW);
        turnManager.discardDevelopmentCards(CardColor.YELLOW);
        assertEquals(true, turnManager.discardDevelopmentCards(CardColor.YELLOW));
        turnManager.discardDevelopmentCards(CardColor.VIOLET);
        turnManager.discardDevelopmentCards(CardColor.VIOLET);
        turnManager.discardDevelopmentCards(CardColor.VIOLET);
        turnManager.discardDevelopmentCards(CardColor.VIOLET);
        turnManager.discardDevelopmentCards(CardColor.VIOLET);
        turnManager.discardDevelopmentCards(CardColor.VIOLET);
        assertEquals(true, turnManager.discardDevelopmentCards(CardColor.VIOLET));
    }

    @Test
    public void activateLeaderCardTest(){
        List<LeaderCard> lc = new ArrayList();
        lc.add(prodPowerLeaderCard);
        lc.add(storageLeaderCard);
        player1.setLeaderCards(lc);
        assertEquals(true, ((LeaderResultMessage) turnManager.activateLeaderCard(player1, 4)).getError());
    }

    @Test
    public void activateLeaderCardTest2(){
        List<LeaderCard> lc = new ArrayList();
        lc.add(prodPowerLeaderCard);
        lc.add(storageLeaderCard);
        player1.setLeaderCards(lc);
        assertEquals(true, ((LeaderResultMessage) turnManager.activateLeaderCard(player1, 0)).getError());
    }

    @Test
    public void activateLeaderCardTest3() throws IndexOutOfBoundsException{
        List<LeaderCard> lc = new ArrayList();
        lc.add(prodPowerLeaderCard);
        lc.add(storageLeaderCard);
        player1.setLeaderCards(lc);
        List<Resource> resourcesIn = new ArrayList<>();
        resourcesIn.add(Resource.COIN);
        resourcesIn.add(Resource.COIN);
        resourcesIn.add(Resource.COIN);
        resourcesIn.add(Resource.COIN);
        resourcesIn.add(Resource.COIN);
        player1.putCofferResources(resourcesIn);
        turnManager.discardLeaderCard(player1, 1);
        assertEquals(true, ((LeaderResultMessage) turnManager.activateLeaderCard(player1, 1)).getError());
    }

    @Test
    public void activateLeaderCardTest4() throws IndexOutOfBoundsException{
        List<LeaderCard> lc = new ArrayList();
        lc.add(prodPowerLeaderCard);
        lc.add(storageLeaderCard);
        player1.setLeaderCards(lc);
        List<Resource> resourcesIn = new ArrayList<>();
        resourcesIn.add(Resource.COIN);
        resourcesIn.add(Resource.COIN);
        resourcesIn.add(Resource.COIN);
        resourcesIn.add(Resource.COIN);
        resourcesIn.add(Resource.COIN);
        player1.putCofferResources(resourcesIn);
        turnManager.activateLeaderCard(player1, 1);
        assertEquals(true, ((LeaderResultMessage) turnManager.activateLeaderCard(player1, 1)).getError());
    }

    @Test
    public void insertWarehouseResources(){
        List<LeaderCard> lc = new ArrayList();
        lc.add(prodPowerLeaderCard);
        lc.add(storageLeaderCard);
        player1.setLeaderCards(lc);
        player2.setLeaderCards(lc);
        player3.setLeaderCards(lc);
        player4.setLeaderCards(lc);
        List<Resource> resourceIn = new ArrayList<>();

        for(int i = 0; i < 24; i++)
            resourceIn.add(Resource.SERVANT);

        turnManager.insertResourcesInWarehouse(player1, 1, resourceIn, true);
        assertEquals(java.util.Optional.of(24), java.util.Optional.of(player2.getFaithPathPosition()));
        assertEquals(java.util.Optional.of(24), java.util.Optional.of(player3.getFaithPathPosition()));
        assertEquals(java.util.Optional.of(24), java.util.Optional.of(player4.getFaithPathPosition()));
        assertEquals(java.util.Optional.of(0), java.util.Optional.of(player1.getFaithPathPosition()));
        assertEquals(true, turnManager.reachedFaithPathEnd());
    }

    @Test
    public void insertWarehouseResources2(){
        List<LeaderCard> lc = new ArrayList();
        lc.add(prodPowerLeaderCard);
        lc.add(storageLeaderCard);
        player.setLeaderCards(lc);

        List<Resource> resourceIn = new ArrayList<>();

        for(int i = 0; i < 24; i++)
            resourceIn.add(Resource.SERVANT);

        singlePlayerTurnManager.insertResourcesInWarehouse(player, 1, resourceIn, true);
        assertEquals(java.util.Optional.of(24), java.util.Optional.of(player.getLorenzoPosition()));
        assertEquals(true, ((SinglePlayer) player).lorenzoWins());
    }

    @Test
    public void threadingTest(){
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                turnManager.lock();
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                turnManager.clientDone();
            }
        };
        Runnable r3 = new Runnable() {
            @Override
            public void run() {
                turnManager.clientDone();
            }
        };
        Runnable r4 = new Runnable() {
            @Override
            public void run() {
                turnManager.clientDone();
            }
        };
        Runnable r5 = new Runnable() {
            @Override
            public void run() {
                turnManager.clientDone();
            }
        };
        Thread t1 = new Thread(r1);
        t1.start();
        Thread t2 = new Thread(r2);
        t2.start();
        Thread t3 = new Thread(r3);
        t3.start();
        Thread t4 = new Thread(r4);
        t4.start();
        Thread t5 = new Thread(r5);
        t5.start();
    }

    @Test
    public void threadingTest2(){
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                turnManager.lock();
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                turnManager.turnDone();
            }
        };

        Thread t1 = new Thread(r1);
        t1.start();
        Thread t2 = new Thread(r2);
        t2.start();

    }

}
