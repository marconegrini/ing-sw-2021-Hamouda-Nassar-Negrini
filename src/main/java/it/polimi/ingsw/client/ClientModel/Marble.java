package it.polimi.ingsw.client.ClientModel;

import it.polimi.ingsw.model.enumerations.Color;

public class Marble {

    private final Color color;

    public Marble(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }


}
