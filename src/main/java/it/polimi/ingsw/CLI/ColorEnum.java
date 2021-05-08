package it.polimi.ingsw.CLI;



public enum ColorEnum {
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m"),

    PURPLE_BACKGROUND("\u001b[41m"),
    GREEN_BACKGROUND("\u001b[42m"),
    YELLOW_BACKGROUND("\u001b[43m"),
    BLUE_BACKGROUND("\u001b[44m"),

    RESET("\u001B[0m");



    private final String escape;

    public static ColorEnum getColorEnum(String value) {
        if (value.toUpperCase().equals("GREEN"))
            return ColorEnum.ANSI_GREEN;
        else if (value.toUpperCase().equals("BLUE"))
            return ColorEnum.ANSI_BLUE;
        else if (value.toUpperCase().equals("YELLOW"))
            return ColorEnum.ANSI_YELLOW;
        else return ColorEnum.ANSI_PURPLE;
    }

    public static ColorEnum getBackgroundEnum(String value) {
        if (value.toUpperCase().equals("GREEN"))
            return ColorEnum.GREEN_BACKGROUND;
        else if (value.toUpperCase().equals("BLUE"))
            return ColorEnum.BLUE_BACKGROUND;
        else if (value.toUpperCase().equals("YELLOW"))
            return ColorEnum.YELLOW_BACKGROUND;
        else return ColorEnum.PURPLE_BACKGROUND;
    }


    public String getescape(){
        return escape;
    }

    @Override
    public String toString(){
        return escape;
    }

    ColorEnum(String escape) {
        this.escape = escape;
    }

}
