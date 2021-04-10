package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.Optional;

public class GreyMarble implements Marble{

    private final Color color;

    public GreyMarble(){
        this.color = Color.GREY;
    }

    @Override
    public Optional<Resource> onMarblePickup(){
        return Optional.of(Resource.STONE);
    }

    @Override
    public Color getColor(){
        return Color.GREY;
    }
}