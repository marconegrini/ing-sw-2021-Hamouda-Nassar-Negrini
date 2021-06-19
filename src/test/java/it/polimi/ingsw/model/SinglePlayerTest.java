package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.CardType;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.model.multiplayer.MultiPlayerGameInstance;
import it.polimi.ingsw.model.parser.LeaderCardParser;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;
import it.polimi.ingsw.model.singleplayer.SinglePlayerGameInstance;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

public class SinglePlayerTest {

    SinglePlayer player;
    SinglePlayerGameInstance gameInstance;
    List<LeaderCard> StorageLeaderCards;

    @Before
    public void setUp() throws MaxPlayersException {
        player = new SinglePlayer("Marco");
        gameInstance = new SinglePlayerGameInstance();
        gameInstance.addPlayer(player);
        LeaderCardParser leaderCardParser = new LeaderCardParser("src/main/java/it/polimi/ingsw/model/jsonFiles/LeaderCardJson.json");
        List<LeaderCard> leaderCards = leaderCardParser.getLeaderCardsDeck();
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
