package it.polimi.ingsw;
import it.polimi.ingsw.model.Coffer;
import java.util.HashMap;

import it.polimi.ingsw.model.enumerations.Resource;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;

import static org.junit.Assert.*;

public class CofferTest {

    private Coffer test;
    private HashMap<Resource, Integer> resourceIn;

    @Before
    public void setUp(){
        test = new Coffer();
        resourceIn = new HashMap<>();
    }

    @Test
    public void testPutResources(){
        resourceIn.put(Resource.STONE, 2);
        resourceIn.put(Resource.COIN, 2);
        test.putResource(resourceIn);
        assertTrue("Test 1 not passed", test.checkAvailability(resourceIn));
        resourceIn.put(Resource.SERVANT, 2);
        assertFalse("Test 2 not passed", test.checkAvailability(resourceIn));
    }

    @Test
    public void testPullResource(){
        resourceIn.put(Resource.STONE, 2);
        resourceIn.put(Resource.COIN, 2);
        test.putResource(resourceIn);
        test.pullResource(resourceIn);
        assertFalse("Test 3 not passed", test.checkAvailability(resourceIn));
    }

    @After
    public void clear(){
        test = null;
        resourceIn = null;
    }

}
