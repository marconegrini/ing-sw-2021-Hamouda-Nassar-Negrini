package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.enumerations.CardType;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.parser.LeaderCardParser;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;
import it.polimi.ingsw.model.singleplayer.SinglePlayerGameInstance;
import static org.junit.Assert.assertEquals;

import it.polimi.ingsw.server.handlers.SinglePlayerGameHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Class that tests single player methods.
 */
public class SinglePlayerTest {

    private static final Logger logger = Logger.getLogger(SinglePlayerGameHandler.class.getName());
    SinglePlayer player;
    SinglePlayerGameInstance gameInstance;
    List<LeaderCard> StorageLeaderCards;

    @Before
    public void setUp() throws MaxPlayersException {
        player = new SinglePlayer("Marco");
        gameInstance = new SinglePlayerGameInstance();
        gameInstance.addPlayer(player);
        LeaderCardParser leaderCardParser = new LeaderCardParser();
        List<LeaderCard> leaderCards = null;
        try {
            leaderCards = leaderCardParser.getLeaderCardsDeck();
        } catch (AlreadyActivatedLeaderCardException | AlreadyDiscardedLeaderCardException e) {
            logger.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
        }
        leaderCardParser.close();
        StorageLeaderCards = leaderCards.stream()
                .filter(card->(card.getCardType().equals(CardType.STORAGE)))
                .collect(Collectors.toList());
        player.setLeaderCards(StorageLeaderCards);
    }


    @Test
    public void testPlayer() throws AlreadyActivatedLeaderCardException, InsufficientResourcesException, AlreadyDiscardedLeaderCardException {
        player.printPlayer();
        assertEquals(java.util.Optional.of(0), java.util.Optional.of(player.getFaithPathPosition()));
        player.incrementFaithPathPosition();
        assertEquals(java.util.Optional.of(1), java.util.Optional.of(player.getFaithPathPosition()));
        player.incrementLorenzoPosition();
        assertEquals(java.util.Optional.of(1), java.util.Optional.of(player.getLorenzoPosition()));
    }

    @Test
    public void testPlayer2() throws AlreadyActivatedLeaderCardException, InsufficientResourcesException, AlreadyDiscardedLeaderCardException, StorageOutOfBoundsException, IllegalInsertionException {
        player.incrementLorenzoPosition();
        player.incrementLorenzoPosition();
        player.incrementLorenzoPosition();
        player.incrementLorenzoPosition();
        player.incrementLorenzoPosition();
        player.incrementLorenzoPosition();
        player.incrementLorenzoPosition();
        player.incrementLorenzoPosition();
        player.updateFaithPath(player.getLorenzoPosition());
        assertEquals(java.util.Optional.of(0), java.util.Optional.of(player.getTotalVictoryPoints()));
    }

    @Test
    public void testPlayer3() throws AlreadyActivatedLeaderCardException, InsufficientResourcesException, AlreadyDiscardedLeaderCardException, StorageOutOfBoundsException, IllegalInsertionException {
        player.incrementLorenzoPosition();
        player.incrementLorenzoPosition();
        player.incrementLorenzoPosition();
        player.incrementLorenzoPosition();
        player.incrementLorenzoPosition();
        player.incrementLorenzoPosition();
        player.incrementLorenzoPosition();
        player.incrementLorenzoPosition();
        player.incrementFaithPathPosition();
        player.incrementFaithPathPosition();
        player.incrementFaithPathPosition();
        player.incrementFaithPathPosition();
        player.incrementFaithPathPosition();
        player.updateFaithPath(player.getLorenzoPosition());
        assertEquals(java.util.Optional.of(3), java.util.Optional.of(player.getTotalVictoryPoints()));
        player.incrementFaithPathPosition();
        assertEquals(java.util.Optional.of(5), java.util.Optional.of(player.getTotalVictoryPoints()));
        assertEquals(false, player.lorenzoWins());
    }

}
