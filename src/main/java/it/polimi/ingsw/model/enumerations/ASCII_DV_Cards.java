package it.polimi.ingsw.model.enumerations;

public enum ASCII_DV_Cards {

    GREEN("\u001B[32m" + "⛊" + "\u001B[0m"),
    YELLOW("\u001B[33m" + "⛊" + "\u001B[0m"),
    BLUE("\u001B[34m" + "⛊" + "\u001B[0m"),
    VIOLET("\u001B[35m" + "⛊" + "\u001B[0m"),


    RESET("\u001B[0m");

    private final String shape;


    public static ASCII_DV_Cards getDVShape(String value) {
        if (value.toUpperCase().equals("GREEN"))
            return ASCII_DV_Cards.GREEN;
        else if (value.toUpperCase().equals("BLUE"))
            return ASCII_DV_Cards.BLUE;
        else if (value.toUpperCase().equals("YELLOW"))
            return ASCII_DV_Cards.YELLOW;
        else return ASCII_DV_Cards.VIOLET;
    }


    public String getShape() {
        return shape;
    }

    @Override
    public String toString() {
        return shape;
    }

    ASCII_DV_Cards(String escape) {
        this.shape = escape;
    }

}
