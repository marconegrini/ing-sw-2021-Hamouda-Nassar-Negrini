package it.polimi.ingsw.server;

import it.polimi.ingsw.model.exceptions.MaxPlayersException;
import org.junit.Test;

import java.io.IOException;

public class StartMultiplayerGameTest {

    @Test
    public void testStartMultiplayer() throws IOException, MaxPlayersException {
        Server.startMultiplayerGame();
    }
}
