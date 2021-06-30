package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.CardType;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.model.parser.LeaderCardParser;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Class that tests multi player and single player methods
 */
public class PlayerTest {

    Player playerMulti;
    Player playerSingle;
    Stack<LeaderCard> leaderCardList;
    List<LeaderCard> leaderCardsDeck;
    List<LeaderCard> leaderCardsDeck2;
    List<LeaderCard> leaderCardsDeck3;
    List<LeaderCard> leaderCardsDeck4;
    List<LeaderCard> storageLeaderCard;
    private DevCardSlots test;
    HashMap<Resource, Integer> cardCost;
    HashMap<Resource, Integer> prodIn;
    HashMap<Resource, Integer> prodOut;


    @Before
    public void setUp(){
        playerMulti = new MultiPlayer("Marco");
        playerSingle = new SinglePlayer("Marco");
        LeaderCardParser lcp = new LeaderCardParser();
        leaderCardList = new Stack();
        leaderCardsDeck = new ArrayList<>();
        leaderCardsDeck2 = new ArrayList<>();
        leaderCardsDeck3 = new ArrayList<>();
        leaderCardsDeck4 = new ArrayList<>();
        leaderCardList = lcp.getLeaderCardsDeck();
        storageLeaderCard = leaderCardList.stream()
                .filter(card->(card.getCardType().equals(CardType.DISCOUNT)))
                .collect(Collectors.toList());

        for(int i = 0; i < 4; i++)
            leaderCardsDeck.add(leaderCardList.pop());
        for(int i = 0; i < 4; i++)
            leaderCardsDeck2.add(leaderCardList.pop());
        for(int i = 0; i < 4; i++)
            leaderCardsDeck3.add(leaderCardList.pop());
        for(int i = 0; i < 4; i++)
            leaderCardsDeck4.add(leaderCardList.pop());
        playerMulti.setLeaderCards(leaderCardsDeck);

        test = new DevCardSlots();

        cardCost = new HashMap<>();
        cardCost.put(Resource.SERVANT, 2);
        cardCost.put(Resource.COIN, 1);

        prodIn = new HashMap<>();
        prodIn.put(Resource.SERVANT, 2);

        prodOut = new HashMap<>();
        prodOut.put(Resource.COIN, 1);
        DevelopmentCard card1 = new DevelopmentCard(2, CardColor.BLUE, Level.FIRST, cardCost, prodIn, prodOut);

    }


    @Test
            (expected = InsufficientResourcesException.class)
    public void testPlayer() throws AlreadyActivatedLeaderCardException, InsufficientResourcesException, AlreadyDiscardedLeaderCardException {
        playerMulti.setLeaderCards(leaderCardsDeck);
        playerMulti.updateFaithPath(8);
        playerMulti.isRapportoInVaticano(4);
        playerMulti.chooseLeaderCard(0,2);
        assertFalse(playerMulti.isLeaderCardActivated(CardType.MARBLE));
        playerMulti.discardLeaderCard(1);
        assertFalse(playerMulti.isLeaderCardActivatable(0));
        assertFalse(playerMulti.isLeaderCardActivatable(1));
        playerMulti.setLeaderCards(leaderCardsDeck2);
        assertFalse(playerMulti.isLeaderCardActivatable(0));
        assertFalse(playerMulti.isLeaderCardActivatable(1));
        playerMulti.setLeaderCards(leaderCardsDeck3);
        assertFalse(playerMulti.isLeaderCardActivatable(0));
        assertFalse(playerMulti.isLeaderCardActivatable(1));
        playerMulti.setLeaderCards(leaderCardsDeck4);
        assertFalse(playerMulti.isLeaderCardActivatable(0));
        assertFalse(playerMulti.isLeaderCardActivatable(1));
        playerMulti.getLeaderCardsPower(CardType.MARBLE);
        playerMulti.activateLeaderCard(0);
    }

    @Test
    public void testPlayer2() throws AlreadyActivatedLeaderCardException, InsufficientResourcesException, AlreadyDiscardedLeaderCardException, StorageOutOfBoundsException, IllegalInsertionException {
        List<Resource> resourcesIn = new ArrayList<>();
        resourcesIn.add(Resource.STONE);
        playerMulti.putCofferResources(resourcesIn);
        assertEquals(resourcesIn, playerMulti.getCofferResource());
        playerMulti.putWarehouseResources(1, resourcesIn);
        assertEquals(resourcesIn, playerMulti.getWarehouseResource());
        resourcesIn.add(Resource.STONE);
        assertEquals(resourcesIn, playerMulti.getTotalResource());
    }

    @Test
    public void testPlayer3() throws AlreadyActivatedLeaderCardException, AlreadyDiscardedLeaderCardException {
        playerMulti.setLeaderCards(storageLeaderCard);
        for(LeaderCard lc : playerMulti.getLeaderCards())
            lc.activate();
        HashMap<Resource, Integer> storage = new HashMap<>();
        storage.put(Resource.SHIELD, 1);
        storage.put(Resource.COIN, 1);
        storage.put(Resource.SERVANT, 1);
        storage.put(Resource.STONE, 1);
        assertEquals(storage, playerMulti.getLeaderCardsPower(CardType.DISCOUNT));
    }
}
