package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.model.cards.LeaderCardCost;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class CardsCompositionMethodsTest {
    private HashMap<LeaderCardCost,Integer> actCost;
    private ArrayList<LeaderCardCost> cardsIn;
    private HashMap<Resource,Integer> TempHash = new HashMap<>();



    private WhiteMarbleLeaderCard WhiteMarbleCard;
    @Before
    public void setUp() throws Exception {

        TempHash.put(Resource.SHIELD,2);
        actCost = new HashMap<LeaderCardCost,Integer>();
        cardsIn = new ArrayList<>();

    }


    @Test
    public void verifyToActivate() {
        actCost.put(new LeaderCardCost(CardColor.GREEN,Level.FIRST ),2);
        actCost.put(new LeaderCardCost(CardColor.YELLOW,Level.FIRST ),1);
        actCost.put(new LeaderCardCost(CardColor.BLUE,Level.FIRST ),1);

        WhiteMarbleCard = new WhiteMarbleLeaderCard(5, actCost, TempHash);

        cardsIn.add(new LeaderCardCost (CardColor.BLUE,Level.FIRST ));
        cardsIn.add(new LeaderCardCost (CardColor.GREEN,Level.FIRST ));

        assertFalse("wrong return verifyToActivate method", WhiteMarbleCard.verifyToActivate(cardsIn));

        cardsIn.add(new LeaderCardCost (CardColor.YELLOW,Level.FIRST ));
        assertFalse("wrong return", WhiteMarbleCard.verifyToActivate(cardsIn));

        cardsIn.add(new LeaderCardCost (CardColor.GREEN,Level.FIRST ));
        assertTrue("wrong return", WhiteMarbleCard.verifyToActivate(cardsIn));

    }


    @After
    public void tearDown() throws Exception {
//        activationCost=null;
//        cardsIn = null;
    }

}