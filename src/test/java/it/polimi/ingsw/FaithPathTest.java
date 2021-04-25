package it.polimi.ingsw;

import it.polimi.ingsw.model.FaithPath;
import org.junit.Before;
import org.junit.Test;

public class FaithPathTest {

    private FaithPath faithPath;

    @Before
    public void setUp(){
        faithPath = new FaithPath();
    }

    @Test
    public void testFaithPath(){
        faithPath.incrementUserPosition();
        faithPath.update(1);

    }
}
