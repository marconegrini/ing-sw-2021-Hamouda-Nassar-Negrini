package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.CardType;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.model.multiplayer.MultiPlayerGameInstance;
import it.polimi.ingsw.model.parser.LeaderCardParser;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
