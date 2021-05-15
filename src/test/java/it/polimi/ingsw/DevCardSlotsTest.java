package it.polimi.ingsw;
import it.polimi.ingsw.model.DevCardSlots;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.EmptySlotException;
import it.polimi.ingsw.model.exceptions.IllegalInsertionException;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DevCardSlotsTest {

    private DevCardSlots test;
    HashMap<Resource, Integer> cardCost;
    HashMap<Resource, Integer> prodIn;
    HashMap<Resource, Integer> prodOut;

    @Before
    public void setUp(){
        test = new DevCardSlots();

        cardCost = new HashMap<>();
        cardCost.put(Resource.SERVANT, 2);
        cardCost.put(Resource.COIN, 1);

        prodIn = new HashMap<>();
        prodIn.put(Resource.SERVANT, 2);

        prodOut = new HashMap<>();
        prodOut.put(Resource.COIN, 1);
    }

    @Test
    public void testInsertCard() throws IllegalInsertionException {
        DevelopmentCard card1 = new DevelopmentCard(2, CardColor.BLUE, Level.FIRST, cardCost, prodIn, prodOut);
        test.addCard(0, card1);
        test.addCard(1, card1);
        test.addCard(2, card1);
    }


    @Test (expected = IllegalInsertionException.class)
    public void testInsertCard1() throws IllegalInsertionException {
        DevelopmentCard card1 = new DevelopmentCard(2, CardColor.BLUE, Level.SECOND, cardCost, prodIn, prodOut);
        test.addCard(2, card1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testInsertCard2() throws IllegalInsertionException {
        DevelopmentCard card1 = new DevelopmentCard(2, CardColor.BLUE, Level.FIRST, cardCost, prodIn, prodOut);
        test.addCard(3, card1);
    }

    @Test
    public void testInsertCard3() throws IllegalInsertionException, EmptySlotException {
        DevelopmentCard card1 = new DevelopmentCard(2, CardColor.BLUE, Level.FIRST, cardCost, prodIn, prodOut);
        DevelopmentCard card2 = new DevelopmentCard(2, CardColor.BLUE, Level.SECOND, cardCost, prodIn, prodOut);
        test.addCard(0, card1);
        test.addCard(0, card2);
        assertEquals(prodIn, test.resourcesProductionIn(0));
    }

    @Test
            (expected = IllegalInsertionException.class)
    public void testInsertCard4() throws IllegalInsertionException, EmptySlotException {
        DevelopmentCard card1 = new DevelopmentCard(2, CardColor.BLUE, Level.FIRST, cardCost, prodIn, prodOut);
        DevelopmentCard card2 = new DevelopmentCard(2, CardColor.BLUE, Level.THIRD, cardCost, prodIn, prodOut);
        test.addCard(0, card1);
        test.addCard(0, card2);
    }

    @Test
    public void testResourcesProductionIn() throws IllegalInsertionException, EmptySlotException {
        DevelopmentCard card1 = new DevelopmentCard(2, CardColor.BLUE, Level.FIRST, cardCost, prodIn, prodOut);
        test.addCard(1, card1);
        assertEquals(prodIn, test.resourcesProductionIn(1));
    }

    @Test (expected = EmptySlotException.class)
    public void testResourcesProductionIn1() throws IllegalInsertionException, EmptySlotException {
        DevelopmentCard card1 = new DevelopmentCard(2, CardColor.BLUE, Level.FIRST, cardCost, prodIn, prodOut);
        test.addCard(1, card1);
        assertEquals(prodIn, test.resourcesProductionIn(0));
    }

    @Test
    public void testResourcesProductionOut() throws IllegalInsertionException, EmptySlotException {
        DevelopmentCard card1 = new DevelopmentCard(2, CardColor.BLUE, Level.FIRST, cardCost, prodIn, prodOut);
        test.addCard(1, card1);
        assertEquals(prodOut, test.resourcesProductionOut(1));
    }

    @Test (expected = EmptySlotException.class)
    public void testResourcesProductionOut1() throws IllegalInsertionException, EmptySlotException {
        DevelopmentCard card1 = new DevelopmentCard(2, CardColor.BLUE, Level.FIRST, cardCost, prodIn, prodOut);
        test.addCard(1, card1);
        assertEquals(prodOut, test.resourcesProductionOut(0));
    }

    @Test
    public void testGetVictoryPoints() throws IllegalInsertionException {
        DevelopmentCard card1 = new DevelopmentCard(2, CardColor.BLUE, Level.FIRST, cardCost, prodIn, prodOut);
        DevelopmentCard card2 = new DevelopmentCard(2, CardColor.BLUE, Level.SECOND, cardCost, prodIn, prodOut);
        test.addCard(0, card1);
        test.addCard(0, card2);
        test.addCard(1, card1);
        assertEquals(6, test.getVictoryPoints());
    }


    @After
    public void clear(){
        test = null;
    }
}
