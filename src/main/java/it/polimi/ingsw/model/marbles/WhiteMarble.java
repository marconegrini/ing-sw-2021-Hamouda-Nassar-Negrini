package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.Optional;

public class WhiteMarble implements Marble{

    private final Color color;

    public WhiteMarble(){
        this.color = Color.WHITE;
    }

    @Override
    public Optional<Resource> onMarblePickup(){
        //TODO creare un metodo che fa il check se è stata attivata una leader card con il potere sulla whiteMarble
        return Optional.of(null);
    }

    @Override
    public Color getColor(){
        return Color.WHITE;
    }
}