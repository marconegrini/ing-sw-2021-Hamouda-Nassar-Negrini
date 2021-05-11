package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Color;

public class Marble {

    private final Color color;

    public Marble (Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }


}
