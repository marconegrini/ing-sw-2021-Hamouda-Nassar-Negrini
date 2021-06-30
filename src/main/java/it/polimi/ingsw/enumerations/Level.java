package it.polimi.ingsw.enumerations;

/**
 * Enumeration class for Level
 */
public enum Level {
    FIRST, SECOND, THIRD, ANY;

    public static Level getEnum(String value) {
        return Enum.valueOf(Level.class, value.toUpperCase());
    }
}
