package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.CardType;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.model.parser.LeaderCardParser;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PlayerTest {

    Player playerMulti;
    Player playerSingle;
    Stack<LeaderCard> leaderCardList;
    List<LeaderCard> leaderCardsDeck;
    List<LeaderCard> leaderCardsDeck2;
    List<LeaderCard> leaderCardsDeck3;
    List<LeaderCard> leaderCardsDeck4;


    @Before
    public void setUp(){
        playerMulti = new MultiPlayer("Marco");
        playerSingle = new SinglePlayer("Marco");
        LeaderCardParser lcp = new LeaderCardParser("src/main/java/it/polimi/ingsw/model/jsonFiles/LeaderCardJson.json");
        leaderCardList = new Stack();
        leaderCardsDeck = new ArrayList<>();
        leaderCardsDeck2 = new ArrayList<>();
        leaderCardsDeck3 = new ArrayList<>();
        leaderCardsDeck4 = new ArrayList<>();
        leaderCardList = lcp.getLeaderCardsDeck();
        for(int i = 0; i < 4; i++)
            leaderCardsDeck.add(leaderCardList.pop());
        for(int i = 0; i < 4; i++)
            leaderCardsDeck2.add(leaderCardList.pop());
        for(int i = 0; i < 4; i++)
            leaderCardsDeck3.add(leaderCardList.pop());
        for(int i = 0; i < 4; i++)
            leaderCardsDeck4.add(leaderCardList.pop());

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
}
