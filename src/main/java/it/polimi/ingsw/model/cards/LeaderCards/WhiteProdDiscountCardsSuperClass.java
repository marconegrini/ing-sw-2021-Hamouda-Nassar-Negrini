package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;
import java.util.Set;

public abstract class WhiteProdDiscountCardsSuperClass extends LeaderCard {

    protected HashMap<CardColor, Integer> activationCost;

}
