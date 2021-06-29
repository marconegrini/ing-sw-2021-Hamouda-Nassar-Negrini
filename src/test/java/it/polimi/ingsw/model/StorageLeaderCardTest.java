package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.StorageLeaderCard;
import it.polimi.ingsw.enumerations.CardType;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.exceptions.EmptySlotException;
import it.polimi.ingsw.exceptions.IllegalInsertionException;
import it.polimi.ingsw.exceptions.WrongCardTypeException;
import it.polimi.ingsw.model.parser.LeaderCardParser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.*;

public class StorageLeaderCardTest {

    List<LeaderCard> StorageLeaderCards;
    List<Resource> resourceIn;

    @Before
    public void setUp() throws Exception {

        LeaderCardParser leaderCardParser = new LeaderCardParser();
        List<LeaderCard> leaderCards = leaderCardParser.getLeaderCardsDeck();
        leaderCardParser.close();
        resourceIn = new ArrayList<>();

        StorageLeaderCards = leaderCards.stream()
                .filter(card->(card.getCardType().equals(CardType.STORAGE)))
                .collect(Collectors.toList());
    }

    @Test (expected = IllegalInsertionException.class)
    public void Test() throws WrongCardTypeException, IllegalInsertionException {
        resourceIn.add(Resource.COIN);
        resourceIn.add(Resource.COIN);
        resourceIn.add(Resource.COIN);
        for(LeaderCard lc : StorageLeaderCards){
            if (lc.getStorageCardActivationCostResources().containsKey(Resource.COIN)) {
                ((StorageLeaderCard) lc).putResourceInCardStorage(resourceIn, null);
            }
        }
    }


        @Test(expected = IllegalInsertionException.class)
        public void Test1 () throws WrongCardTypeException, IllegalInsertionException {
        resourceIn.add(Resource.COIN);
        resourceIn.add(Resource.SERVANT);
        resourceIn.add(Resource.COIN);
        for (LeaderCard lc : StorageLeaderCards) {
            if (lc.getStorageCardActivationCostResources().containsKey(Resource.COIN)) {
                ((StorageLeaderCard) lc).putResourceInCardStorage(resourceIn, null);
            }
        }
    }


    @Test (expected = IllegalInsertionException.class)
    public void Test2() throws WrongCardTypeException, IllegalInsertionException {
        resourceIn.add(Resource.COIN);
        resourceIn.add(Resource.COIN);
        for(LeaderCard lc : StorageLeaderCards){
            if (lc.getStorageCardActivationCostResources().containsKey(Resource.COIN)) {
                ((StorageLeaderCard) lc).putResourceInCardStorage(resourceIn, null);
            }
        }
        resourceIn.add(Resource.SERVANT);
        for(LeaderCard lc : StorageLeaderCards){
            if (lc.getStorageCardActivationCostResources().containsKey(Resource.COIN)) {
                ((StorageLeaderCard) lc).putResourceInCardStorage(resourceIn, null);
            }
        }
    }

    @Test (expected = EmptySlotException.class)
    public void Test3() throws WrongCardTypeException, EmptySlotException {
        for(LeaderCard lc : StorageLeaderCards){
            if (lc.getStorageCardActivationCostResources().containsKey(Resource.COIN)) {
                ((StorageLeaderCard) lc).pullResource();
            }
        }
    }

    @Test
    public void Test4() throws WrongCardTypeException, IllegalInsertionException {
        HashMap<Resource, Integer> storage = new HashMap();
        storage.put(Resource.STONE, 2);
        resourceIn.add(Resource.STONE);
        resourceIn.add(Resource.STONE);
        for(LeaderCard lc : StorageLeaderCards){
            if (lc.getStorageCardActivationCostResources().containsKey(Resource.COIN)) {
                ((StorageLeaderCard) lc).putResourceInCardStorage(resourceIn, null);
                assertEquals(storage, lc.getLeaderCardPower());
                assertEquals(resourceIn, ((StorageLeaderCard) lc).getStoredResources());
            }
        }
    }


}
