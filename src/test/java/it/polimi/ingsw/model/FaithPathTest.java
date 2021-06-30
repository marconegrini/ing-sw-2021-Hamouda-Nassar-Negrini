package it.polimi.ingsw.model;

import it.polimi.ingsw.model.FaithPath;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Class that tests faith path methods
 */
public class FaithPathTest {

    private FaithPath faithPath;

    @Before
    public void setUp(){
        faithPath = new FaithPath();
    }

    @Test
    public void testFaithPath(){
        faithPath.incrementUserPosition();
        faithPath.update(8);
        assertEquals(java.util.Optional.of(0), java.util.Optional.of(faithPath.getVictoryPoints()));
    }

    @Test
    public void testFaithPath2(){
        faithPath.incrementUserPosition();
        faithPath.incrementUserPosition();
        faithPath.incrementUserPosition();
        faithPath.incrementUserPosition();
        faithPath.incrementUserPosition();
        faithPath.incrementUserPosition();
        faithPath.update(8);
        assertEquals(java.util.Optional.of(5), java.util.Optional.of(faithPath.getVictoryPoints()));
    }

    @Test
    public void testFaithPath3(){
        assertEquals(java.util.Optional.of(24), java.util.Optional.of(faithPath.getEnd()));
    }

    @Test
    public void testFaithPath4(){
        faithPath.incrementUserPosition();
        faithPath.incrementUserPosition();
        faithPath.incrementUserPosition();
        faithPath.incrementUserPosition();
        faithPath.incrementUserPosition();
        faithPath.incrementUserPosition();
        faithPath.update(8);
        assertEquals(true, faithPath.getVaticanSections().get(0).isCardFlipped());
        assertEquals(java.util.Optional.of(5), java.util.Optional.of(faithPath.getVictoryPoints()));
        faithPath.incrementUserPosition();
        faithPath.incrementUserPosition();
        faithPath.incrementUserPosition();
        faithPath.update(16);
        assertEquals(false, faithPath.getVaticanSections().get(1).isCardFlipped());
        assertEquals(java.util.Optional.of(9), java.util.Optional.of(faithPath.getVictoryPoints()));

    }
}
