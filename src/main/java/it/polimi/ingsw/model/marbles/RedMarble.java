package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.Optional;

public class RedMarble implements Marble{

    private final Color color;

    public RedMarble(){
        this.color = Color.RED;
    }

    @Override
    public Optional<Resource> onMarblePickup(){
        return Optional.of(Resource.FAITH);
    }

    @Override
    public Color getColor(){
        return Color.RED;
    }
}
