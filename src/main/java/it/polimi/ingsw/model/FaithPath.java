package it.polimi.ingsw.model;

import it.polimi.ingsw.model.parser.FaithPathParser;

import java.util.*;

public class FaithPath {

    private Integer userPosition;

    private ArrayList<VaticanSection> vaticanSections;

    private HashMap<Integer, Integer> victoryPoints;

    private Integer end;

    public FaithPath(){

        this.userPosition = 0;
        this.vaticanSections = new ArrayList<>();

        Integer startPos = 0;
        Integer spazioPapa = 0;
        Integer victoryPoints = 0;

        FaithPathParser parser = new FaithPathParser("src/main/java/it/polimi/ingsw/model/jsonFiles/faithPathInfoJson.json");

        this.vaticanSections.addAll(parser.getVaticanSections());

        this.victoryPoints = parser.getFaithPathVictoryPoints();

        this.end = parser.getEnd();

        parser.close();

    }

    public void incrementUserPosition(){
        userPosition++;
    }

    public Integer getUserPosition(){
        return this.userPosition;
    }

    public void update(Integer newPlayingUserPos) {
        for(VaticanSection vs : vaticanSections)
            if(vs.rapportoVaticano(newPlayingUserPos))
                vs.activate(this.userPosition);
    }

    public Integer getVictoryPoints(){
        Integer result = 0;
        for(VaticanSection vs : vaticanSections){
            result += vs.getVictoryPoints();
        }

        Set<Integer> victoryPointsPositions = this.victoryPoints.keySet();
        for(Integer position : victoryPointsPositions)
            if(position <= this.userPosition)
                result += victoryPoints.get(position);

        return result;

    }

    public Integer getEnd(){
        return this.end;
    }

    public boolean ended(){
        if(userPosition.equals(this.end)) return true;
        return false;
    }
}
