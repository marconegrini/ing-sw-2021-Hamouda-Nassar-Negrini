package it.polimi.ingsw.model;

import it.polimi.ingsw.model.parser.FaithPathParser;

import java.util.*;

/**
 * Class that contains the user's pawn and his position. Keeps track of updates in papal favor cards in
 * vatican sections array list.
 */
public class FaithPath {

    private Integer userPosition;

    private ArrayList<VaticanSection> vaticanSections;

    //key: position in faith path
    //value:victory points
    private HashMap<Integer, Integer> victoryPoints;

    //last cell of faith path
    private Integer end;

    public FaithPath(){
        this.userPosition = 0;
        this.vaticanSections = new ArrayList<>();
        FaithPathParser parser = new FaithPathParser();
        this.vaticanSections.addAll(parser.getVaticanSections());
        this.victoryPoints = parser.getFaithPathVictoryPoints();
        this.end = parser.getEnd();
        parser.close();
    }

    /**
     * increments user's position in fatih path
     */
    public void incrementUserPosition(){
        if(userPosition < end)
            userPosition++;
    }

    /**
     * @return the user position in faith path
     */
    public Integer getUserPosition(){
        return this.userPosition;
    }

    /**If a rapporto in vaticano has been activated by a new user position, the corresponding vatican
     * section is activated, and eventually the papal favor card is flipped.
     * @param newPlayingUserPos someone else's position
     */
    public void update(Integer newPlayingUserPos) {
        for(VaticanSection vs : vaticanSections)
            if(vs.rapportoVaticano(newPlayingUserPos))
                vs.activate(this.userPosition);
    }

    /**
     * @param newUserPos someone else's new position
     * @return true if specified new user position activates a rapporto in vaticano, else otherwise
     */
    public boolean isRapportoInVaticano(Integer newUserPos){
        for(VaticanSection vs : vaticanSections)
            if(vs.rapportoVaticano(newUserPos))
                return true;
        return false;
    }

    /**
     * @return total victory points inside the faith path. They are based on the faith path's victory points
     * and on papal favor card victory points.
     */
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

    /**
     * @return end position of faith path
     */
    public Integer getEnd(){
        return this.end;
    }

    /**
     * @return true if the user reached the ed of faith path
     */
    public boolean ended(){
        if(userPosition.equals(this.end)) return true;
        return false;
    }

    /**
     * @return copied arraylist of vatican sections
     */
    public List<VaticanSection> getVaticanSections(){
        return List.copyOf(this.vaticanSections);
    }
}
