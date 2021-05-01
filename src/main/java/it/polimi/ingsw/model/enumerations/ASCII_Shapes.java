package it.polimi.ingsw.model.enumerations;


public enum ASCII_Shapes {

    COIN("\u001B[33m" + "⛂" +  "\u001B[0m"),
    SHIELD("\u001b[34;1m" + "\uD83D\uDEE1️" + "\u001B[0m"),
    STONE("\u001b[30;1m" + "⛘" + "\u001B[0m"),
    SERVANT("\u001B[35m" + "⛹" + "\u001B[0m");



private final String shape;

    public static ASCII_Shapes getShape(String value) {
        if (value.toUpperCase().equals("COIN"))
            return ASCII_Shapes.COIN;
        else if (value.toUpperCase().equals("SHIELD"))
            return ASCII_Shapes.SHIELD;
        else if (value.toUpperCase().equals("STONE"))
            return ASCII_Shapes.STONE;
        else return ASCII_Shapes.SERVANT;

    }

    ASCII_Shapes(String shape){this.shape=shape;}
    public String toString(){
        return shape;
    }

}

