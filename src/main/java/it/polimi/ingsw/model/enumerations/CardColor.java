package it.polimi.ingsw.model.enumerations;

import java.util.Locale;

public enum CardColor {
    GREEN, BLUE, YELLOW, VIOLET;

    public static CardColor getEnum(String value) {
        return Enum.valueOf(CardColor.class, value.toUpperCase());
    }
}
