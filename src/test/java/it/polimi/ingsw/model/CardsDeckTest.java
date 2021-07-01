package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.CardType;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.exceptions.IllegalInsertionException;
import it.polimi.ingsw.exceptions.StorageOutOfBoundsException;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.model.parser.DevelopmentCardParser;
import it.polimi.ingsw.model.parser.LeaderCardParser;
import it.polimi.ingsw.server.controller.TurnManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Class that tests Cards deck methods
 */
public class CardsDeckTest {
    private CardsDeck cardsDeck;
    private MarketBoard marketBoard;
    private DevelopmentCardParser parser;
    private Player player;
    List<LeaderCard> StorageLeaderCards;
    List<LeaderCard> DiscountLeaderCards;
    List<LeaderCard> WhiteMarbleLeaderCards;
    List<LeaderCard> ProdPowerLeaderCards;
    LeaderCard discountLeaderCard;
    LeaderCard storageLeaderCard;
    LeaderCard whiteMarbleLeaderCard;
    LeaderCard prodPowerLeaderCard;
    TurnManager turnManager;

    @Before
    public void setUp(){
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
        cardsDeck = new CardsDeck();

        player = new MultiPlayer("Marco");

        List<LeaderCard> lc = new ArrayList();
        lc.add(discountLeaderCard);
        lc.add(storageLeaderCard);
        player.setLeaderCards(lc);

        cardsDeck.initializeCardsDeck();
        marketBoard = new MarketBoard();
        turnManager = new TurnManager(cardsDeck, marketBoard);
    }

    @Test
    public void testDeckInitialization(){
        cardsDeck.initializeCardsDeck();
        DevelopmentCard card = cardsDeck.peekCard(0, 0);
        assertEquals(card.getLevel(), Level.FIRST);

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                while(!cardsDeck.isEmptyDeck(i, j)) {
                    card = cardsDeck.popCard(i, j);
                    if(i == 0) assertEquals(card.getLevel(), Level.FIRST);
                    if(i == 1) assertEquals(card.getLevel(), Level.SECOND);
                    if(i == 2) assertEquals(card.getLevel(), Level.THIRD);
                    if(j == 0) assertEquals(card.getColor(), CardColor.GREEN);
                    if(j == 1) assertEquals(card.getColor(), CardColor.BLUE);
                    if(j == 2) assertEquals(card.getColor(), CardColor.YELLOW);
                    if(j == 3) assertEquals(card.getColor(), CardColor.VIOLET);
                }
            }
        }
    }

    @Test
    public void buyDevelopmentCardTest() throws StorageOutOfBoundsException, IllegalInsertionException {
        List<Resource> resources = new ArrayList<>();
        resources.add(Resource.COIN);
        player.putWarehouseResources(2, resources);
        resources.clear();
        resources.add(Resource.STONE);
        resources.add(Resource.STONE);
        player.putWarehouseResources(3, resources);
        resources.add(Resource.COIN);
        resources.add(Resource.COIN);
        assertEquals(false, turnManager.containsNeededResources(player, resources));
        resources.remove(Resource.COIN);
        assertEquals(true, turnManager.containsNeededResources(player, resources));
    }


}
