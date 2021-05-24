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
        for(int i = 0; i < leaderCards.size(); i++)
            if(i!=index1 || i!= index2)
                leaderCards.remove(i);
    }

    public List<LeaderCard> getLeaderCards() {
        return this.leaderCards;
    }
}
