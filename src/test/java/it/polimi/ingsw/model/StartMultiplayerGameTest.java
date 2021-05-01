package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.MaxPlayersException;
import it.polimi.ingsw.server.Server;
import org.junit.Test;

import java.io.IOException;

public class StartMultiplayerGameTest {

    @Test
    public void testStartMultiplayer() throws IOException, MaxPlayersException {
        Server.startMultiplayerGame(4);
    }
}
