package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardCost;
import it.polimi.ingsw.model.enumerations.CardType;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StorageLeaderCard extends LeaderCard {

    private final HashMap<Resource,Integer> activationCost;
    private final HashMap<Resource, Integer> storage;

    public StorageLeaderCard(
            CardType cardType,
            int victoryPoints,
            HashMap<Resource,Integer> activationCost,
            HashMap<Resource, Integer> storage)
    {
        this.cardType = cardType;
        this.Vp = victoryPoints;
        this.isFlipped = false;
        this.activationCost = activationCost;
        this.storage = storage;
    }

//TODO complete this method
    public boolean verifyToActivate( HashMap<Resource,Integer> resourceIn) {
return true;
    }

}
