package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that contains only necessary data structures to save user's current state. There are no integrity rules since structures are
 * updated with model data. At the end of each turn, all Light Model classes associated with users will be updated through Update Messages (ServerMessageType)
 */
public class LightModel{

    private List<LeaderCard> leaderCards;
    private MarketBoard marketBoard;
    private HashMap<String, Integer> otherPlayersFaithPathPosition;
    private Integer faithPathPosition;

    public LightModel(){
        leaderCards = new ArrayList<>();
        marketBoard = new MarketBoard();
        otherPlayersFaithPathPosition = new HashMap<>();
        faithPathPosition = 0;
    }

    public List<LeaderCard> getLeaderCards() {
        return this.leaderCards;
    }

    public void setLeaderCards(List<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }

    public void chooseLeaderCards(Integer index1, Integer index2){
        LeaderCard lc1 = leaderCards.get(index1);
        LeaderCard lc2 = leaderCards.get(index2);
        leaderCards.clear();
        leaderCards.add(lc1);
        leaderCards.add(lc2);
    }

    public MarketBoard getMarketBoard(){
        return this.marketBoard;
    }

    public void setMarketBoard(MarketBoard marketBoard) {
        this.marketBoard = marketBoard;
    }

    public Integer getFaithPathPosition() {
        return faithPathPosition;
    }

    public void setFaithPathPosition(Integer faithPathPosition) {
        this.faithPathPosition = faithPathPosition;
    }

    public HashMap<String, Integer> getOtherPlayersFaithPathPosition() {
        return otherPlayersFaithPathPosition;
    }

    public void setOtherPlayersFaithPathPosition(HashMap<String, Integer> otherPlayersFaithPathPosition) {
        this.otherPlayersFaithPathPosition = otherPlayersFaithPathPosition;
    }

}
