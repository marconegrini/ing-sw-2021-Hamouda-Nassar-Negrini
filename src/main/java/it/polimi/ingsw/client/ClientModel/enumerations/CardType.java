package it.polimi.ingsw.client.ClientModel.enumerations;

public enum CardType {

    DISCOUNT,PRODUCTION,MARBLE,STORAGE;

    public static CardType getEnum(String value) {
        return Enum.valueOf(CardType.class, value.toUpperCase());
    }

}
