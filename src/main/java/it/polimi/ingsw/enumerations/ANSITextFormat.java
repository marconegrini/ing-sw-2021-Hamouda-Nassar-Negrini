package it.polimi.ingsw.enumerations;

/**
 * Text format
 */
public enum ANSITextFormat {

        BOLD("\u001b[1m"),
        ITALIC("\u001b[3m"),
        UNDERLINE("\u001b[4m"),
        BOLD_ITALIC(BOLD.toString()+ITALIC.toString()),

        GREEN_BACK_GROUND("\u001b[48;5;28m"),
        GREEN_COLOR("\u001b[32m"),
        RED_COLOR("\u001b[31;1m"),
        RESET("\u001B[0m");


        private final String shape;
    ANSITextFormat(String s) {
      this.shape = s;
    }
    @Override
    public String toString() {
        return shape;
    }

}
