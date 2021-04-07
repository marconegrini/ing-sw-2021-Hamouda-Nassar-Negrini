package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.resources.Resource;

import it.polimi.ingsw.model.enumerations.Color;

public interface Marble {
    Resource onMarblePickup();
    Color getColor();
}
