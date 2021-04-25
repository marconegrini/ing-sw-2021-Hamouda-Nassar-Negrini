package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Color;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class MarketBoard_Test {

    private MarketBoard market;
    private Marble[][] oldMarket;

    @Before
    public void setUp(){
        market = new MarketBoard();
        oldMarket = market.getMarketBoardMarbles();
    }

    @After
    public void tearDown(){
    }

    @Test
    public void insertMarble() {

        Color oldExternalMarble = market.getExternalMarbleColor();

        market.insertMarble(true, 0);
        //System.out.println("Afte);

        assertNotEquals(oldExternalMarble, market.getExternalMarbleColor());

        oldExternalMarble = market.getExternalMarbleColor();
        market.insertMarble(false, 3);
        assertNotEquals(oldExternalMarble, market.getExternalMarbleColor());


    }

    @Test
    public void getMarketBoard(){
        for (int i=0; i<3; i++){
            List<Marble> newMarket = Arrays.asList( market.getMarketBoardMarbles()[i]);
            assertEquals(newMarket, Arrays.asList(market.getMarketBoardMarbles()[i]));

        }
        //assertNotEquals();
    }
}