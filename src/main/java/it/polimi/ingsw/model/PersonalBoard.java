package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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


    public List<Resource> getWarehouseResource() {
        return warehouse.getTotalResources();
    }

    public List<Resource> getCofferResource() {
        return coffer.getTotalResources();
    }

    public List<Resource> getTotalResource(){
        List<Resource> totalResource = this.getWarehouseResource();
        totalResource.addAll(this.getCofferResource());

        return totalResource;
    }

    public DevCardSlots getDevCardSlots() {
        return devCardSlots;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public Warehouse getWarehouse (){return warehouse;}

    public Integer getVictoryPoints(){

        return 0;
    }

}
