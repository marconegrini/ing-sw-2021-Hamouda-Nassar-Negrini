package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.fromServer.BuyDVCardError;
import it.polimi.ingsw.messages.fromServer.activateProduction.ProductionResultMessage;
import it.polimi.ingsw.messages.fromServer.leadercard.LeaderResultMessage;
import it.polimi.ingsw.messages.fromServer.storeResources.ResourcesToStoreMessage;
import it.polimi.ingsw.messages.fromServer.warehouse.MoveResourcesResultMessage;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.CardType;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.parser.LeaderCardParser;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;
import it.polimi.ingsw.server.controller.TurnManager;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerTest1 {
    TurnManager turnManager;
    CardsDeck cardsDeck;
    MarketBoard marketBoard;
    SinglePlayer player;
    List<LeaderCard> discountLeaderCards;

    @Before
    public void setUp(){
        cardsDeck = new CardsDeck();
        cardsDeck.initializeCardsDeck();
        marketBoard = new MarketBoard();
        turnManager= new TurnManager(cardsDeck, marketBoard);
        player = new SinglePlayer("Marco");
        LeaderCardParser leaderCardParser = new LeaderCardParser("src/main/java/it/polimi/ingsw/model/jsonFiles/LeaderCardJson.json");
        List<LeaderCard> leaderCards = leaderCardParser.getLeaderCardsDeck();
        leaderCardParser.close();
        discountLeaderCards = leaderCards.stream()
                .filter(card->(card.getCardType().equals(CardType.DISCOUNT)))
                .collect(Collectors.toList());
        player.setLeaderCards(discountLeaderCards);
        turnManager.selectLeaderCard(player,0, 1);
        turnManager.setMultiplayer(false);
        turnManager.setPlayer(player);
    }

    @Test
    public void pickResourcesTest(){
        turnManager.pickResources(player, true, 1, false);
        assertEquals(ResourcesToStoreMessage.class, turnManager.pickResources(player, true, 1, false).getClass());
    }

    @Test
    public void insertResourceInWarehouseTest(){
        List<Resource> resourceIn = new ArrayList<>();
        resourceIn.add(Resource.SHIELD);
        turnManager.insertResourcesInWarehouse(player, 2, resourceIn, false);
        assertEquals(ResourcesToStoreMessage.class, turnManager.insertResourcesInWarehouse(player, 2, resourceIn, false).getClass());
    }

    @Test
    public void moveResourceInWarehouseTest(){
        List<Resource> resourceIn = new ArrayList<>();
        resourceIn.add(Resource.SHIELD);
        turnManager.insertResourcesInWarehouse(player, 1, resourceIn, false);
        turnManager.moveResourcesInWarehouse(player, 1, 2);
        assertEquals(MoveResourcesResultMessage.class, turnManager.moveResourcesInWarehouse(player, 1, 2).getClass());
    }

    @Test
    public void buyDEvCardTest(){
        assertEquals(BuyDVCardError.class, turnManager.buyDevelopmentCard(player, 1, 1, 0).getClass());
    }

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
        assertEquals(LeaderResultMessage.class, turnManager.discardLeaderCard(player, 0));
        assertEquals(LeaderResultMessage.class, turnManager.discardLeaderCard(player, 1));
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
}
