package it.polimi.ingsw.model.enumerations;


public enum ASCII_Resources {

    COIN("\u001B[33m" + "⛂" +  "\u001B[0m"),
    SHIELD("\u001b[34;1m" + "\uD83D\uDEE1️" + "\u001B[0m"),
    STONE("\u001b[37;1m" + "⛘" + "\u001B[0m"),
    SERVANT("\u001B[35m" + "⛹" + "\u001B[0m"),
    FAITH_POINT("\u001b[31m" + "✢" +"\u001B[0m");



private final String shape;

    public static ASCII_Resources getShape(String value) {
        if (value.toUpperCase().equals("COIN"))
            return ASCII_Resources.COIN;
        else if (value.toUpperCase().equals("SHIELD"))
            return ASCII_Resources.SHIELD;
        else if (value.toUpperCase().equals("STONE"))
            return ASCII_Resources.STONE;
        else if (value.toUpperCase().equals("SERVANT"))
            return ASCII_Resources.SERVANT;
        else return ASCII_Resources.FAITH_POINT;
    }

    ASCII_Resources(String shape){this.shape=shape;}
    public String toString(){
        return shape;
    }

}

