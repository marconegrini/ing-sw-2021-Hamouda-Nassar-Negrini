package it.polimi.ingsw.model.enumerations;

public enum Level {
    FIRST, SECOND, THIRD, ANY;

    public static Level getEnum(String value) {
        if (value.toUpperCase().equals(Level.FIRST.toString()))
            return Level.FIRST;
        else if (value.toUpperCase().equals(Level.SECOND.toString()))
            return Level.SECOND;
        else if (value.toUpperCase().equals(Level.THIRD.toString()))
            return Level.THIRD;
        else return Level.ANY;
    }
}
