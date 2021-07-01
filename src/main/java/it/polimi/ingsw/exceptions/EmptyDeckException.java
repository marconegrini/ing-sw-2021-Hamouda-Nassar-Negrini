package it.polimi.ingsw.exceptions;

/**
 * Exception class thrown when a development cards deck is empty
 */
public class EmptyDeckException extends Exception {
    public EmptyDeckException(){
        System.err.println("Exception: Empty Deck");
    }
}
