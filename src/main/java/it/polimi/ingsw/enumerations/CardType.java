package it.polimi.ingsw.enumerations;

/**
 * Enumeration class for leader card type
 */
public enum CardType {

    DISCOUNT,PRODUCTION,MARBLE,STORAGE;

    public static CardType getEnum(String value) {
        return Enum.valueOf(CardType.class, value.toUpperCase());
    }

}
