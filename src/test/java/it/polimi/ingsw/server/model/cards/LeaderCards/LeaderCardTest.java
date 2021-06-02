package it.polimi.ingsw.server.model.cards.LeaderCards;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardCost;
import it.polimi.ingsw.model.cards.LeaderCards.DiscountLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.StorageLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.WhiteMarbleLeaderCard;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.exceptions.AlreadyActivatedLeaderCardException;
import it.polimi.ingsw.model.exceptions.AlreadyDiscardedLeaderCardException;
import it.polimi.ingsw.model.exceptions.WrongCardTypeException;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;
import it.polimi.ingsw.model.parser.LeaderCardParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class LeaderCardTest {

    List<LeaderCard> StorageLeaderCards;
    List<LeaderCard> DiscountLeaderCards;
    List<LeaderCard> WhiteMarbleLeaderCards;
    List<LeaderCard> ProdPowerLeaderCards;

    @Before
    public void setUp() throws Exception {

        LeaderCardParser leaderCardParser = new LeaderCardParser("src/main/java/it/polimi/ingsw/model/jsonFiles/LeaderCardJson.json");
        List<LeaderCard> leaderCards = leaderCardParser.getLeaderCardsDeck();
        leaderCardParser.close();

        StorageLeaderCards = leaderCards.stream()
                .filter(card->(card.getCardType().equals(CardType.STORAGE)))
                .collect(Collectors.toList());
        DiscountLeaderCards = leaderCards.stream()
                .filter(card->(card.getCardType().equals(CardType.DISCOUNT)))
                .collect(Collectors.toList());
        WhiteMarbleLeaderCards = leaderCards.stream()
                .filter(card->(card.getCardType().equals(CardType.MARBLE)))
                .collect(Collectors.toList());
        ProdPowerLeaderCards = leaderCards.stream()
                .filter(card->(card.getCardType().equals(CardType.PRODUCTION)))
                .collect(Collectors.toList());
    }

    @Test
    public void Test() throws WrongCardTypeException, AlreadyActivatedLeaderCardException, AlreadyDiscardedLeaderCardException {
        List<Resource> resources = new ArrayList<>();
        resources.add(Resource.COIN);
        resources.add(Resource.COIN);
        resources.add(Resource.COIN);
        resources.add(Resource.COIN);
        resources.add(Resource.COIN);

        HashMap<Resource, Integer> power = new HashMap<>();
        List<LeaderCardCost> leaderCardCosts = new ArrayList<>();
        List<DevelopmentCard> developmentCards = new ArrayList<>();

        power.clear();
        power.put(Resource.STONE, 2);
        for(LeaderCard lc : this.StorageLeaderCards) {
            if (lc.getStorageCardActivationCostResources().containsKey(Resource.COIN)) {

                assertEquals(true, ((StorageLeaderCard) lc).isActivatable(resources));
                //assertEquals("Test failed", power, lc.getLeaderCardPower());
                lc.activate();
            } else {
                assertEquals(false, ((StorageLeaderCard) lc).isActivatable(resources));
                //assertNotEquals("Test failed", power, lc.getLeaderCardPower());
                lc.activate();
            }
        }

        /*
        LeaderCardCost lcc2 = new LeaderCardCost(CardColor.VIOLET, Level.ANY);
        LeaderCardCost lcc1 = new LeaderCardCost(CardColor.YELLOW, Level.ANY);
        DevelopmentCard dc1 = new DevelopmentCard(2, CardColor.YELLOW, Level.ANY, null, null, null);
        DevelopmentCard dc2 = new DevelopmentCard(2, CardColor.VIOLET, Level.ANY, null, null, null);
        leaderCardCosts.add(lcc1);
        leaderCardCosts.add(lcc2);
        developmentCards.add(dc1);
        developmentCards.add(dc2);
        for(LeaderCard lc : this.DiscountLeaderCards) {
            DiscountLeaderCard dlc = (DiscountLeaderCard) lc;
            if (leaderCardCosts.equals(dlc.getActivationCost())) {
                assertEquals(true, ((DiscountLeaderCard) lc).isActivatable(developmentCards));
                //assertEquals("Test failed", power, lc.getLeaderCardPower());
            } else {
                assertEquals(false, ((DiscountLeaderCard) lc).isActivatable(developmentCards));
                //assertNotEquals("Test failed", power, lc.getLeaderCardPower());
            }
            lc.activate();
        }
         */

        leaderCardCosts.clear();
        LeaderCardCost lcc1 = new LeaderCardCost(CardColor.YELLOW, Level.ANY);
        LeaderCardCost lcc2 = new LeaderCardCost(CardColor.YELLOW, Level.ANY);
        LeaderCardCost lcc3 = new LeaderCardCost(CardColor.BLUE, Level.ANY);
        developmentCards.clear();
        DevelopmentCard dc1 = new DevelopmentCard(2, CardColor.YELLOW, Level.ANY, null, null, null);
        DevelopmentCard dc2 = new DevelopmentCard(2, CardColor.YELLOW, Level.ANY, null, null, null);
        DevelopmentCard dc3 = new DevelopmentCard(2, CardColor.BLUE, Level.ANY, null, null, null);
        leaderCardCosts.add(lcc1);
        leaderCardCosts.add(lcc2);
        leaderCardCosts.add(lcc3);
        developmentCards.add(dc1);
        developmentCards.add(dc2);
        developmentCards.add(dc3);
        for(LeaderCard lc : this.WhiteMarbleLeaderCards) {
            WhiteMarbleLeaderCard dlc = (WhiteMarbleLeaderCard) lc;
            if (leaderCardCosts.equals(dlc.getActivationCost())) {
                assertEquals(true, dlc.isActivatable(developmentCards));
                //assertEquals("Test failed", power, lc.getLeaderCardPower());
            } else {
                assertEquals(false, dlc.isActivatable(developmentCards));
                //assertNotEquals("Test failed", power, lc.getLeaderCardPower());
            }
            lc.activate();
        }






    }
}