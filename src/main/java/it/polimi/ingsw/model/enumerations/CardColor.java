package it.polimi.ingsw.model.enumerations;

public enum CardColor {
    GREEN,BLUE,YELLOW,VIOLET;

    public static CardColor getEnum(String value) {
        if (value.toUpperCase().equals(CardColor.GREEN.toString()))
            return CardColor.GREEN;
        else if (value.toUpperCase().equals(CardColor.BLUE.toString()))
            return CardColor.BLUE;
        else if (value.toUpperCase().equals(CardColor.YELLOW.toString()))
            return CardColor.YELLOW;
        else return CardColor.VIOLET;
    }
}
