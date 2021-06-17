package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Color;

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
