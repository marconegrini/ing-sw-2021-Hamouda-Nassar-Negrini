package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.EmptySlotException;
import it.polimi.ingsw.model.exceptions.IllegalInsertionException;
import it.polimi.ingsw.model.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.model.exceptions.StorageOutOfBoundsException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.Assert.assertEquals;


public class PersonalBoardTest {

    private Warehouse warehouse;
    private Coffer coffer;
    private DevCardSlots devCardSlots;
    private PersonalBoard personalBoard;
    private List<Resource> resourceIn;
    private DevelopmentCard test;
    HashMap<Resource, Integer> cardCost;
    HashMap<Resource, Integer> prodIn;
    HashMap<Resource, Integer> prodOut;
    List<Resource> productionIn;
    List<Resource> productionOut;

    @Before
    public void setUp(){
        warehouse = new Warehouse();
        coffer = new Coffer();
        personalBoard = new PersonalBoard();
        resourceIn = new ArrayList<>();
        productionIn = new ArrayList<>();
        productionOut = new ArrayList<>();

        cardCost = new HashMap<>();
        cardCost.put(Resource.SERVANT, 2);
        cardCost.put(Resource.COIN, 1);

        prodIn = new HashMap<>();
        prodIn.put(Resource.SERVANT, 2);
        productionIn.add(Resource.SERVANT);
        productionIn.add(Resource.SERVANT);

        prodOut = new HashMap<>();
        prodOut.put(Resource.COIN, 1);
        productionOut.add(Resource.COIN);

        test = new DevelopmentCard(2, CardColor.BLUE, Level.FIRST, cardCost, prodIn, prodOut);


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

    @Test
    public void testPersonalBoard() throws StorageOutOfBoundsException, IllegalInsertionException, InsufficientResourcesException {
        resourceIn.add(Resource.SERVANT);
        resourceIn.add(Resource.SERVANT);
        personalBoard.putWarehouseResource(2, resourceIn);
        personalBoard.putCofferResource(resourceIn);
        resourceIn.add(Resource.SERVANT);
        resourceIn.add(Resource.SERVANT);
        assertEquals(resourceIn, personalBoard.getTotalResource());
        resourceIn.remove(Resource.SERVANT);
        resourceIn.remove(Resource.SERVANT);
        resourceIn.remove(Resource.SERVANT);
        personalBoard.pullCofferResource(resourceIn);
        personalBoard.pullWarehouseResource(resourceIn);
        resourceIn.add(Resource.SERVANT);
        assertEquals(resourceIn, personalBoard.getTotalResource());
    }

    @Test
    public void testDevelopmentCard() throws IllegalInsertionException, EmptySlotException {
        personalBoard.addCardInDevCardSlot(1, test);
        assertEquals(productionIn, personalBoard.devCardSlotProductionIn(1));
        assertEquals(productionOut, personalBoard.devCardSlotProductionOut(1));
        assertEquals(java.util.Optional.of(2), java.util.Optional.of(personalBoard.getVictoryPoints()));
    }
}
