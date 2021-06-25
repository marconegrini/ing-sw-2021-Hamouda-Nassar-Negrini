package it.polimi.ingsw.enumerations;

public enum Level {
    FIRST, SECOND, THIRD, ANY;

    public static Level getEnum(String value) {
        return Enum.valueOf(Level.class, value.toUpperCase());
    }
}
