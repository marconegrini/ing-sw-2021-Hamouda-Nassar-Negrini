package it.polimi.ingsw;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.BadInputFormatException;
import it.polimi.ingsw.model.exceptions.IllegalInsertionException;
import it.polimi.ingsw.model.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.exceptions.StorageOutOfBoundsException;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class WarehouseTest{

    private Warehouse test;
    private ArrayList<Resource> resourcesIn;
    private HashMap<Resource, Integer> cost;

    @Before
    public void setUp(){

        test = new Warehouse();
        resourcesIn = new ArrayList<>();
        cost = new HashMap<>();
    }

    @Test (expected = StorageOutOfBoundsException.class)
    public void testStorageOutOfBounds() throws StorageOutOfBoundsException,
            BadInputFormatException, IllegalInsertionException {

        resourcesIn.add(Resource.STONE);
        test.putResource(4, resourcesIn);
    }

    @Test (expected = BadInputFormatException.class)
    public void testBadInputFormatException1() throws StorageOutOfBoundsException,
            BadInputFormatException, IllegalInsertionException {

        resourcesIn.clear();
        resourcesIn.add(Resource.STONE);
        resourcesIn.add(Resource.STONE);
        test.putResource(1, resourcesIn);
        resourcesIn.add(Resource.COIN);
        test.putResource(3, resourcesIn);
    }

    @Test (expected = BadInputFormatException.class)
    public void testBadInputFormatException2() throws StorageOutOfBoundsException,
            BadInputFormatException, IllegalInsertionException {

        resourcesIn.clear();
        resourcesIn.add(Resource.STONE);
        resourcesIn.add(Resource.STONE);
        resourcesIn.add(Resource.COIN);
        test.putResource(3, resourcesIn);
    }

    @Test
    public void testMoveResource1() throws StorageOutOfBoundsException,
            IllegalInsertionException, BadInputFormatException, IllegalMoveException {

        resourcesIn.clear();
        resourcesIn.add(Resource.STONE);
        resourcesIn.add(Resource.STONE);
        test.putResource(3, resourcesIn);
        test.moveResource(3, 2);

    }

    @Test (expected = IllegalMoveException.class)
    public void testMoveResource2() throws StorageOutOfBoundsException,
            IllegalInsertionException, BadInputFormatException, IllegalMoveException {

        resourcesIn.clear();
        resourcesIn.add(Resource.STONE);
        resourcesIn.add(Resource.STONE);
        test.putResource(2, resourcesIn);
        test.moveResource(2, 1);
    }

    @Test
    public void testCheckAvailability() throws IllegalInsertionException,
            BadInputFormatException, StorageOutOfBoundsException {

        resourcesIn.clear();
        resourcesIn.add(Resource.STONE);
        resourcesIn.add(Resource.STONE);
        test.putResource(2, resourcesIn);
        this.cost.put(Resource.STONE, 2);
        assertTrue("Test passed", test.checkAvailability(cost));
    }

    @After
    public void clear(){
        test = null;
    }

}
