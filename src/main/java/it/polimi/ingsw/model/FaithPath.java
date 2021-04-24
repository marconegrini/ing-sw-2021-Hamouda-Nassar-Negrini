package it.polimi.ingsw.model;

import it.polimi.ingsw.model.parser.FaithPathParser;
import it.polimi.ingsw.model.parser.Parser;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class FaithPath {

    private Integer userPosition;

    private ArrayList<VaticanSection> vaticanSections;

    private HashMap<Integer, Integer> victoryPoints;

    private Integer end;

    public FaithPath(){

        this.userPosition = 0;
        this.vaticanSections = new ArrayList<>();
        end = 20;

        Integer startPos = 0;
        Integer spazioPapa = 0;
        Integer victoryPoints = 0;

        FaithPathParser parser = new FaithPathParser("src/main/java/it/polimi/ingsw/model/jsonFiles/faithPathInfoJson.json");

        this.vaticanSections.addAll(parser.getVaticanSections());

        this.victoryPoints = parser.getFaithPathVictoryPoints();


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

        //TODO sommare a result i punti vittoria guardando l'Hashmap

        return result;

    }

    public Integer getEnd(){
        return this.end;
    }

    public boolean ended(){
        if(userPosition.equals(this.end)) return true;
        return false;
    }

    public static void main(String[] args) {
        FaithPath faithPath = new FaithPath();

    }

}
