package it.polimi.ingsw.server.model.cards.LeaderCards;

import it.polimi.ingsw.server.controller.MultiPlayerManager;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.cards.LeaderCardCost;
import it.polimi.ingsw.server.model.enumerations.CardType;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.multiplayer.MultiPlayer;

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

    @Override
    public boolean verifyToActivate(Player player,HashMap<Resource, Integer> activationCost) {
        return MultiPlayerManager.verifyToActivateLeaderCard(player,activationCost);

    }

    @Override
    public String toString() {
        return  "\nCard type: " + this.cardType +
                "\nVictory points: " + this.Vp +
                "\nAvailable storage: " + storage.toString() +
                "\nActivation cost: " + activationCost.toString();
    }
}