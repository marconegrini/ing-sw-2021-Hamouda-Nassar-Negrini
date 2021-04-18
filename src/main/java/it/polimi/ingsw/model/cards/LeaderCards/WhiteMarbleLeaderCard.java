package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;

public class WhiteMarbleLeaderCard extends LeaderCard {
    private final HashMap <Resource, Integer> OutProductionResource;
    protected final HashMap<CardColor, Level> activationCost;

    public WhiteMarbleLeaderCard(int vp, HashMap<CardColor, Level> activationCost, HashMap <Resource, Integer> OutProductionResource) {
        this.Vp = vp;
        this.isFlipped = false;

        this.activationCost=activationCost;
        this.OutProductionResource=OutProductionResource;
    }


    //getters
    public HashMap<CardColor, Level> getActivationCost() {
        return activationCost;
    }

    public HashMap<Resource, Integer> getOutProductionResource() {
        return OutProductionResource;
    }
}
