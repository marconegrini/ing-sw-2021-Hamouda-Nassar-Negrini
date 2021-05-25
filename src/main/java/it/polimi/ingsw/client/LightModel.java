package it.polimi.ingsw.client;

import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.ArrayList;
import java.util.List;

public class LightModel{

    private List<LeaderCard> leaderCards;

    public LightModel(){
        leaderCards = new ArrayList<>();
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

    public List<LeaderCard> getLeaderCards() {
        return this.leaderCards;
    }
}
