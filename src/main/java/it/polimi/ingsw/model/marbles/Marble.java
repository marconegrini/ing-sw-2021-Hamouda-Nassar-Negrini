package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.enumerations.Resource;

import it.polimi.ingsw.model.enumerations.Color;
import java.util.Optional;

public interface Marble {
    Optional<Resource> onMarblePickup();
    Color getColor();
}
