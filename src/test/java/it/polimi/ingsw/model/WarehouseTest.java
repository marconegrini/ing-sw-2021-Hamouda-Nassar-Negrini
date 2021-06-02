package it.polimi.ingsw.model;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.IllegalInsertionException;
import it.polimi.ingsw.model.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.exceptions.StorageOutOfBoundsException;
import it.polimi.ingsw.model.exceptions.InsufficientResourcesException;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WarehouseTest{

    private Warehouse test;
    private List<Resource> resourcesIn;
    private HashMap<Resource, Integer> cost;

    @Before
    public void setUp(){

        test = new Warehouse();
        resourcesIn = new ArrayList<>();
        cost = new HashMap<>();
    }

    @Test
            (expected = IllegalInsertionException.class)
    public void testPutResources1() throws IllegalInsertionException, StorageOutOfBoundsException {
        resourcesIn.add(Resource.STONE);
        resourcesIn.add(Resource.COIN);
        test.putResource(3, resourcesIn);
    }

    @Test
            (expected = IllegalInsertionException.class)
    public void testPutResources2() throws IllegalInsertionException, StorageOutOfBoundsException {
        resourcesIn.add(Resource.COIN);
        test.putResource(1, resourcesIn);
        resourcesIn.add(Resource.COIN);
        test.putResource(2, resourcesIn);
    }

    @Test
            (expected = IllegalInsertionException.class)
    public void testPutResources3() throws IllegalInsertionException, StorageOutOfBoundsException {
        resourcesIn.add(Resource.COIN);
        test.putResource(1, resourcesIn);
        test.putResource(1, resourcesIn);
    }

    @Test
            (expected = IllegalInsertionException.class)
    public void testPutResources4() throws IllegalInsertionException, StorageOutOfBoundsException {
        resourcesIn.add(Resource.COIN);
        test.putResource(2, resourcesIn);
        resourcesIn.clear();
        resourcesIn.add(Resource.STONE);
        test.putResource(2, resourcesIn);
    }

    @Test
            (expected = IllegalInsertionException.class)
    public void testPutResources5() throws IllegalInsertionException, StorageOutOfBoundsException {
        resourcesIn.add(Resource.COIN);
        resourcesIn.add(Resource.COIN);
        resourcesIn.add(Resource.COIN);
        test.putResource(2, resourcesIn);
    }

    @Test
            (expected = IllegalInsertionException.class)
    public void testPutResources6() throws IllegalInsertionException, StorageOutOfBoundsException {
        resourcesIn.add(Resource.COIN);
        test.putResource(2, resourcesIn);
        resourcesIn.add(Resource.COIN);
        test.putResource(2, resourcesIn);
    }

    @Test
            (expected = StorageOutOfBoundsException.class)
    public void testMoveResource1() throws StorageOutOfBoundsException, IllegalMoveException {
        test.moveResource(1, 5);
    }

    @Test
    public void testMoveResource2() throws StorageOutOfBoundsException, IllegalMoveException, IllegalInsertionException {
        resourcesIn.add(Resource.STONE);
        resourcesIn.add(Resource.STONE);
        test.putResource(3, resourcesIn);
        resourcesIn.clear();
        resourcesIn.add(Resource.COIN);
        test.putResource(2, resourcesIn);
        test.moveResource(2,3);
    }

    @Test
            (expected = IllegalMoveException.class)
    public void testMoveResource3() throws StorageOutOfBoundsException, IllegalMoveException, IllegalInsertionException {
        resourcesIn.add(Resource.STONE);
        resourcesIn.add(Resource.STONE);
        test.putResource(2, resourcesIn);
        resourcesIn.clear();
        resourcesIn.add(Resource.COIN);
        test.putResource(1, resourcesIn);
        test.moveResource(2,1);
    }

    @Test
    public void testCheckAvailability1() throws StorageOutOfBoundsException, IllegalInsertionException {
        resourcesIn.add(Resource.COIN);
        test.putResource(1, resourcesIn);
        resourcesIn.add(Resource.STONE);
        assertEquals(false, test.checkAvailability(resourcesIn));
    }

    @Test
    public void testCheckAvailability2() throws StorageOutOfBoundsException, IllegalInsertionException {
        resourcesIn.add(Resource.COIN);
        test.putResource(1, resourcesIn);
        assertEquals(true, test.checkAvailability(resourcesIn));
    }

    @Test
    public void testPullResource1() throws StorageOutOfBoundsException, IllegalInsertionException, InsufficientResourcesException {
        resourcesIn.add(Resource.STONE);
        resourcesIn.add(Resource.STONE);
        test.putResource(3, resourcesIn);
        resourcesIn.clear();
        resourcesIn.add(Resource.SHIELD);
        test.putResource(2, resourcesIn);
        resourcesIn.add(Resource.STONE);
        test.pullResource(resourcesIn);
        resourcesIn.remove(Resource.SHIELD);
        assertEquals(true, test.checkAvailability(resourcesIn));
        resourcesIn.add(Resource.SHIELD);
        assertEquals(false, test.checkAvailability(resourcesIn));
    }

    @Test
    public void testGetTotalResources() throws StorageOutOfBoundsException, IllegalInsertionException {
        resourcesIn.add(Resource.STONE);
        resourcesIn.add(Resource.STONE);
        test.putResource(2, resourcesIn);
        resourcesIn.clear();
        resourcesIn.add(Resource.SHIELD);
        test.putResource(1, resourcesIn);
        resourcesIn.add(Resource.STONE);
        resourcesIn.add(Resource.STONE);
        assertEquals(resourcesIn, test.getTotalResources());
    }


    @After
    public void clear(){
        test = null;
    }

}
