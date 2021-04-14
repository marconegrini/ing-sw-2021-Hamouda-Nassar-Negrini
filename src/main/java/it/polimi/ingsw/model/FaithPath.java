package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class FaithPath {

    private Integer userPosition;

    private Integer pathId;

    private ArrayList<VaticanSection> vaticanSections;

    private HashMap<Integer, Integer> victoryPoints;

    public FaithPath(Integer pathId){

        userPosition = 0;
        this.pathId = pathId;
        vaticanSections = new ArrayList<>();

        Integer startPos = 0;
        Integer spazioPapa = 0;
        Integer victoryPoints = 0;

        for(int i = 0; i <= 3; i++)
            //TODO integrazione file json per valori esatti di inzio e fine dei rapporti in vaticano
            vaticanSections.add(new VaticanSection(startPos, spazioPapa, victoryPoints));

        //TODO integrazione file json per istanziare HashMap victoryPoints
    }

    public void incrementUserPosition(){
        userPosition++;
    }

    public Integer getPathId(){
        return this.pathId;
    }

    public Integer getUserPosition(){
        return this.userPosition;
    }

    public void update(Player player, Integer newPlayerPosition) {
        for(VaticanSection vs : vaticanSections){
            if(vs.rapportoVaticano(newPlayerPosition)){
                vs.activate(this.userPosition);
            }
        }
    }

    public Integer getVictoryPoints(){
        Integer result = 0;
        for(VaticanSection vs : vaticanSections){
            result += vs.getVictoryPoints();
        }

        //TODO sommare a result i punti vittoria guardando l'Hashmap

        return result;

    }


}
