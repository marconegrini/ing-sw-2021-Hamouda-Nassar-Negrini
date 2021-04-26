package it.polimi.ingsw.model.enumerations;

public enum CardType {

    DISCOUNT,PRODUCTION,MARBLE,STORAGE;

    public static CardType getEnum(String value) {
        if (value.toUpperCase().equals(CardType.DISCOUNT.toString()))
            return CardType.DISCOUNT;
        else if (value.toUpperCase().equals(CardType.PRODUCTION.toString()))
            return CardType.PRODUCTION;
        else if (value.toUpperCase().equals(CardType.MARBLE.toString()))
            return CardType.MARBLE;
        else return CardType.STORAGE;
    }

}
