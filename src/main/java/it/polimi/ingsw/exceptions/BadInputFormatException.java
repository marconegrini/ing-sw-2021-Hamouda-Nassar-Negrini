package it.polimi.ingsw.exceptions;

/**
 * Exception class thrown when the player's input doesn't satisfy integrity parameters.
 */
public class BadInputFormatException extends Exception{
    public BadInputFormatException(){
        System.err.println("Exception: Bad input format");
    }
}
