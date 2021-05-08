package it.polimi.ingsw.server.model.enumerations;

import java.util.function.Predicate;

public enum Resource implements Predicate<Resource> {
    COIN, SERVANT, SHIELD, STONE, FAITH;

    public static Resource getEnum(String value) {
        return Enum.valueOf(Resource.class, value.toUpperCase());
    }

    @Override
    public boolean test(Resource resource) {
        return false;
    }
}