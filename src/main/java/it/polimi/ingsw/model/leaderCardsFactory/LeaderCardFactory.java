package it.polimi.ingsw.model.leaderCardsFactory;

import it.polimi.ingsw.model.DataIn;
import it.polimi.ingsw.model.cards.LeaderCard;

public abstract class LeaderCardFactory {
    public abstract LeaderCard construct(DataIn dataIn);

}
