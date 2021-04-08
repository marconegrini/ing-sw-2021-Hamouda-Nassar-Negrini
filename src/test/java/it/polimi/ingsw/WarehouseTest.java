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

import java.util.ArrayList;

public class WarehouseTest{

    private Warehouse test;

    @Before
    public void setUp(){
        test = new Warehouse();
    }

    @Test (expected = StorageOutOfBoundsException.class)
    public void testStorageOutOfBounds() throws StorageOutOfBoundsException,
            BadInputFormatException, IllegalInsertionException {

        ArrayList<Resource> resourcesIn = new ArrayList<>();
        resourcesIn.add(Resource.STONE);
        test.putResource(4, resourcesIn);
    }

    @Test (expected = BadInputFormatException.class)
    public void testBadInputFormatException1() throws StorageOutOfBoundsException,
            BadInputFormatException, IllegalInsertionException {

        ArrayList<Resource> resourcesIn = new ArrayList<>();
        resourcesIn.add(Resource.STONE);
        resourcesIn.add(Resource.STONE);
        test.putResource(1, resourcesIn);
        resourcesIn.add(Resource.COIN);
        test.putResource(3, resourcesIn);
    }

    @Test (expected = BadInputFormatException.class)
    public void testBadInputFormatException2() throws StorageOutOfBoundsException,
            BadInputFormatException, IllegalInsertionException {

        ArrayList<Resource> resourcesIn = new ArrayList<>();
        resourcesIn.add(Resource.STONE);
        resourcesIn.add(Resource.STONE);
        resourcesIn.add(Resource.COIN);
        test.putResource(3, resourcesIn);
    }

    @Test
    public void testMoveResource1() throws StorageOutOfBoundsException,
            IllegalInsertionException, BadInputFormatException, IllegalMoveException {

        ArrayList<Resource> resourcesIn = new ArrayList<>();
        resourcesIn.add(Resource.STONE);
        resourcesIn.add(Resource.STONE);
        test.putResource(3, resourcesIn);
        test.moveResource(3, 2);

    }

    @Test (expected = IllegalMoveException.class)
    public void testMoveResource2() throws StorageOutOfBoundsException,
            IllegalInsertionException, BadInputFormatException, IllegalMoveException {

        ArrayList<Resource> resourcesIn = new ArrayList<>();
        resourcesIn.add(Resource.STONE);
        resourcesIn.add(Resource.STONE);
        test.putResource(2, resourcesIn);
        test.moveResource(2, 1);

    }

    @After
    public void clear(){
        test = null;
    }

}
