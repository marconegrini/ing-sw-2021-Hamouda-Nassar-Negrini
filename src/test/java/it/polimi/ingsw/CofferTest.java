package it.polimi.ingsw;
import it.polimi.ingsw.model.Coffer;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.InsufficientResourcesException;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

public class CofferTest {

    private Coffer test;
    private List<Resource> resourceIn;

    @Before
    public void setUp(){
        test = new Coffer();
        resourceIn = new ArrayList();
    }

    @Test
    public void testPutResources(){
        resourceIn.add(Resource.SHIELD);
        resourceIn.add(Resource.STONE);
        resourceIn.add(Resource.COIN);
        resourceIn.add(Resource.SERVANT);
        test.putResource(resourceIn);
        assertEquals(true, test.checkAvailability(resourceIn));
    }

    @Test
    public void pullResources() throws InsufficientResourcesException {
        resourceIn.add(Resource.SHIELD);
        resourceIn.add(Resource.STONE);
        resourceIn.add(Resource.COIN);
        resourceIn.add(Resource.SERVANT);
        test.putResource(resourceIn);
        resourceIn.clear();
        resourceIn.add(Resource.COIN);
        resourceIn.add(Resource.SERVANT);
        test.pullResource(resourceIn);
        assertEquals(false, test.checkAvailability(resourceIn));
        resourceIn.clear();
        resourceIn.add(Resource.SHIELD);
        resourceIn.add(Resource.STONE);
        assertEquals(true, test.checkAvailability(resourceIn));
    }

    @Test
    public void testGetTotalResources(){
        resourceIn.add(Resource.SHIELD);
        resourceIn.add(Resource.STONE);
        test.putResource(resourceIn);
        assertEquals(resourceIn, test.getTotalResources());
    }


    @After
    public void clear(){
        test = null;
        resourceIn = null;
    }

}
