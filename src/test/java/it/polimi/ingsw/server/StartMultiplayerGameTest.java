package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.MaxPlayersException;
import org.junit.Test;

import java.io.IOException;

/**
 * Class that test the start of the multiplayer game
 */
public class StartMultiplayerGameTest {

    @Test
    public void testStartMultiplayer() throws IOException, MaxPlayersException {
        Server.startMultiplayerGame();
    }
}
