package it.polimi.ingsw.enumerations;

/**
 * Enumeration class for cards colors
 */
public enum CardColor {
    GREEN, BLUE, YELLOW, VIOLET;

    public static CardColor getEnum(String value) {
        return Enum.valueOf(CardColor.class, value.toUpperCase());
    }
}
