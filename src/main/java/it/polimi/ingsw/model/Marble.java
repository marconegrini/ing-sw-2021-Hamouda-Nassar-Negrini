package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.Color;

/**
 * Enum class for marbles used in market board
 */
public class Marble {

    private final Color color;

    public Marble (Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }


    @Override
    public String toString() {
        return "Marble{" +
                "color=" + color +
                '}';
    }
}
