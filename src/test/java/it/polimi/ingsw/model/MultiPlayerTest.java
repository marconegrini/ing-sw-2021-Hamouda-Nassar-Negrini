package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.model.multiplayer.MultiPlayerGameInstance;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Class that test multiplayer methods
 */
public class MultiPlayerTest {

    MultiPlayer player;
    MultiPlayer player2;
    MultiPlayerGameInstance gameInstance;

    @Before
    public void setUp() throws MaxPlayersException {
       player2 = new MultiPlayer();
       player = new MultiPlayer("Marco");
       gameInstance = new MultiPlayerGameInstance();
       gameInstance.addPlayer(player2);
       gameInstance.addPlayer(player);
    }


    @Test
    public void testPlayer() throws AlreadyActivatedLeaderCardException, InsufficientResourcesException, AlreadyDiscardedLeaderCardException {
        gameInstance.printGamePlayers();
        assertEquals(java.util.Optional.of(0), java.util.Optional.of(player.getFaithPathPosition()));
        player.incrementFaithPathPosition();
        assertEquals(java.util.Optional.of(1), java.util.Optional.of(player.getFaithPathPosition()));
        player.setCalamaio();
        assertEquals(true, player.hasCalamaio());
    }
}
