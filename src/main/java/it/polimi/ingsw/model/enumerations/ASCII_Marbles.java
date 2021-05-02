package it.polimi.ingsw.model.enumerations;

public enum ASCII_Marbles {


    YELLOW("\u001B[33m" + "⬤" +  "\u001B[0m"),
    VIOLET("\u001B[35m" + "⬤" + "\u001B[0m"),
    WHITE("\u001b[30;1m" + "⬤" + "\u001B[0m"),
    GREY("\u001b[37;1m" + "⬤" + "\u001B[0m"),
    RED("\u001b[31;1m" + "⬤" + "\u001B[0m"),
    BLUE("\u001b[34;1m" + "⬤" + "\u001B[0m");



    private final String marble;

    public static ASCII_Marbles getShape(String value) {
        if (value.toUpperCase().equals("YELLOW"))
            return ASCII_Marbles.YELLOW;
        else if (value.toUpperCase().equals("VIOLET"))
            return ASCII_Marbles.VIOLET;
        else if (value.toUpperCase().equals("GREY"))
            return ASCII_Marbles.GREY;
        else if (value.toUpperCase().equals("WHITE"))
            return ASCII_Marbles.WHITE;
        else if (value.toUpperCase().equals("RED"))
            return ASCII_Marbles.RED;
        else return ASCII_Marbles.BLUE;

    }

    ASCII_Marbles(String shape){this.marble=shape;}
    public String toString(){
        return marble;
    }


}
