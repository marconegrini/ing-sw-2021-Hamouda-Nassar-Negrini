package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.IllegalInsertionException;
import it.polimi.ingsw.model.exceptions.StorageOutOfBoundsException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;


public class PersonalBoardTest {

    private Warehouse warehouse;
    private Coffer coffer;
    private List<Resource> resourceIn;

    @Before
    public void setUp(){
        warehouse = new Warehouse();
        coffer = new Coffer();
        resourceIn = new ArrayList<>();
    }

    @Test
    public void testTotalResource() throws StorageOutOfBoundsException, IllegalInsertionException {
        resourceIn.add(Resource.SERVANT);
        resourceIn.add(Resource.SERVANT);
        warehouse.putResource(2, resourceIn);
        coffer.putResource(resourceIn);
        resourceIn.add(Resource.SERVANT);
        resourceIn.add(Resource.SERVANT);
        List<Resource> totalResource = warehouse.getTotalResources();
        totalResource.addAll(coffer.getTotalResources());
        assertEquals(resourceIn, totalResource);
    }
}
