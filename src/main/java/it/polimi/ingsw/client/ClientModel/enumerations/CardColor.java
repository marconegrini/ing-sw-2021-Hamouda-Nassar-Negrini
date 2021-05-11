package it.polimi.ingsw.client.ClientModel.enumerations;

public enum CardColor {
    GREEN, BLUE, YELLOW, VIOLET;

    public static CardColor getEnum(String value) {
        return Enum.valueOf(CardColor.class, value.toUpperCase());
    }
}
