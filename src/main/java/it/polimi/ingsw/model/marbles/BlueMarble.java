package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.Optional;

public class BlueMarble implements Marble{

    private final Color color;

    public BlueMarble(){
        this.color = Color.BLUE;
    }

    @Override
    public Optional<Resource> onMarblePickup(){
        return Optional.of(Resource.SHIELD);
    }

    @Override
    public Color getColor(){
        return Color.BLUE;
    }
}
