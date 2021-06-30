package it.polimi.ingsw.enumerations;

import java.util.function.Predicate;

/**
 * Enumeration class for resources
 */
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