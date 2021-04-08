package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.Optional;

public class VioletMarble implements Marble{

    private final Color color;

    public VioletMarble(){
        this.color = Color.VIOLET;
    }

    @Override
    public Optional<Resource> onMarblePickup(){
        return Optional.of(Resource.SERVANT);
    }

    @Override
    public Color getColor(){
        return Color.VIOLET;
    }
}
