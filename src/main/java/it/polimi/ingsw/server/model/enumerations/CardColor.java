package it.polimi.ingsw.server.model.enumerations;

public enum CardColor {
    GREEN, BLUE, YELLOW, VIOLET;

    public static CardColor getEnum(String value) {
        return Enum.valueOf(CardColor.class, value.toUpperCase());
    }
}
