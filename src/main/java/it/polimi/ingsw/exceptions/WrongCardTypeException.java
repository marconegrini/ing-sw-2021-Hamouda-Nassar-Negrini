package it.polimi.ingsw.exceptions;

/**
 * Exception class thrown when the leader card method invoked on a card is of a different card type
 */
public class WrongCardTypeException extends Exception{
    public WrongCardTypeException() {
        System.err.println("Exception: wrong card type (might be a wrong casting for a leader card)");
    }
}
