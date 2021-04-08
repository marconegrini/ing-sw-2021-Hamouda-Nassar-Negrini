package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.Optional;

public class YellowMarble implements Marble{

    private final Color color;

    public YellowMarble(){
        this.color = Color.YELLOW;
    }

    @Override
    public Optional<Resource> onMarblePickup(){
        return Optional.of(Resource.COIN);
    }

    @Override
    public Color getColor(){
        return Color.YELLOW;
    }
}
