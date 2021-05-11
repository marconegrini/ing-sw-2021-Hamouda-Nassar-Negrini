package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.cards.LeaderCardCost;
import it.polimi.ingsw.server.model.cards.LeaderCards.WhiteMarbleLeaderCard;
import it.polimi.ingsw.server.model.enumerations.CardColor;
import it.polimi.ingsw.server.model.enumerations.CardType;
import it.polimi.ingsw.server.model.enumerations.Level;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.parser.LeaderCardParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
        LeaderCardParser leaderCardParser = new LeaderCardParser("src/main/java/it/polimi/ingsw/server/model/jsonFiles/LeaderCardJson.json");
        List<LeaderCard> leaderCards = leaderCardParser.getLeaderCardsDeck();
        leaderCardParser.close();


        //leaderCards1 contains only <Marble> and <Discount> leader cards
        List<LeaderCard> leaderCards1 = leaderCards.stream()
                .filter(x->!(x.getCardType().equals(CardType.STORAGE) |x.getCardType().equals(CardType.PRODUCTION) ))
                .collect(Collectors.toList());

        //leaderCards2 contains only <Production> leader cards
        List<LeaderCard> leaderCards2 = leaderCards.stream()
                .filter(x-> (x.getCardType().equals(CardType.PRODUCTION) ))
                .collect(Collectors.toList());

        /*
            ASSERTION:the size of the array list passed to the method "verifyToActivate(cardsIn)"
             must not be bigger than 3 element maximum (the size of the DVcards slots that a player could have in the first place on his board)
         */

        assertFalse("wrong return verifyToActivate method", leaderCards1.get(0).verifyToActivate(cardsIn));
        cardsIn.add(new LeaderCardCost(CardColor.YELLOW, Level.FIRST));
        assertTrue("wrong return verifyToActivate method", leaderCards1.get(0).verifyToActivate(cardsIn));
        cardsIn.add(new LeaderCardCost(CardColor.GREEN, Level.FIRST));
        assertTrue("wrong return verifyToActivate method", leaderCards1.get(0).verifyToActivate(cardsIn));
        assertFalse("wrong return verifyToActivate method", leaderCards1.get(1).verifyToActivate(cardsIn));
        cardsIn.add(new LeaderCardCost(CardColor.BLUE, Level.THIRD));
        assertTrue("wrong return verifyToActivate method", leaderCards1.get(2).verifyToActivate(cardsIn));

        cardsIn.clear();
        cardsIn.add(new LeaderCardCost(CardColor.YELLOW, Level.FIRST));
        assertFalse("wrong return verifyToActivate method", leaderCards1.get(3).verifyToActivate(cardsIn));
        cardsIn.add(new LeaderCardCost(CardColor.VIOLET, Level.SECOND));
        assertTrue("wrong return verifyToActivate method", leaderCards1.get(3).verifyToActivate(cardsIn));
        cardsIn.remove(cardsIn.size() - 1);
        assertFalse("wrong return verifyToActivate method", leaderCards1.get(3).verifyToActivate(cardsIn));
        assertFalse("wrong return verifyToActivate method", leaderCards1.get(4).verifyToActivate(cardsIn));

        cardsIn.clear();
        cardsIn.add(new LeaderCardCost(CardColor.BLUE, Level.FIRST));
        cardsIn.add(new LeaderCardCost(CardColor.YELLOW, Level.FIRST));
        cardsIn.add(new LeaderCardCost(CardColor.YELLOW, Level.FIRST));
        assertTrue("wrong return verifyToActivate method", leaderCards1.get(4).verifyToActivate(cardsIn));
        assertFalse("wrong return verifyToActivate method", leaderCards1.get(5).verifyToActivate(cardsIn));

        cardsIn.clear();
        cardsIn.add(new LeaderCardCost(CardColor.GREEN, Level.SECOND));
        cardsIn.add(new LeaderCardCost(CardColor.GREEN, Level.THIRD));
        cardsIn.add(new LeaderCardCost(CardColor.VIOLET, Level.SECOND));
        assertTrue("wrong return verifyToActivate method", leaderCards1.get(5).verifyToActivate(cardsIn));

        cardsIn.clear();
        cardsIn.add(new LeaderCardCost(CardColor.YELLOW, Level.SECOND));
        cardsIn.add(new LeaderCardCost(CardColor.BLUE, Level.SECOND));
        cardsIn.add(new LeaderCardCost(CardColor.BLUE, Level.SECOND));
        assertTrue("wrong return verifyToActivate method", leaderCards1.get(6).verifyToActivate(cardsIn));
        assertFalse("wrong return verifyToActivate method", leaderCards1.get(7).verifyToActivate(cardsIn));

        //<production cards> (require only second level DVcards)
        cardsIn.clear();
        assertFalse("wrong return verifyToActivate method", leaderCards2.get(0).verifyToActivate(cardsIn));
        cardsIn.add(new LeaderCardCost(CardColor.YELLOW, Level.SECOND));
        assertTrue("wrong return verifyToActivate method", leaderCards2.get(0).verifyToActivate(cardsIn));
        cardsIn.add(new LeaderCardCost(CardColor.BLUE, Level.FIRST));
        assertFalse("wrong return verifyToActivate method", leaderCards2.get(1).verifyToActivate(cardsIn));
        cardsIn.add(new LeaderCardCost(CardColor.BLUE, Level.SECOND));
        assertTrue("wrong return verifyToActivate method", leaderCards2.get(1).verifyToActivate(cardsIn));

        cardsIn.clear();
        cardsIn.add(new LeaderCardCost(CardColor.VIOLET, Level.SECOND));
        assertTrue("wrong return verifyToActivate method", leaderCards2.get(2).verifyToActivate(cardsIn));
        assertFalse("wrong return verifyToActivate method", leaderCards2.get(3).verifyToActivate(cardsIn));
        cardsIn.add(new LeaderCardCost(CardColor.GREEN, Level.SECOND));
        assertTrue("wrong return verifyToActivate method", leaderCards2.get(3).verifyToActivate(cardsIn));

    }


    @After
    public void tearDown() throws Exception {
        cardsIn = null;
    }

}