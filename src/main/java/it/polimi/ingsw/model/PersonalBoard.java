package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.ArrayList;

public class PersonalBoard {
    private Warehouse warehouse;
    private Coffer coffer;
    private DevCardSlots devCardSlots;
    private ArrayList<LeaderCard> leaderCards;

    public PersonalBoard(){
        warehouse = new Warehouse();
        coffer = new Coffer();
        devCardSlots = new DevCardSlots();
        leaderCards = new ArrayList<>(0);
    }
}
