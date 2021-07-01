package it.polimi.ingsw.exceptions;

/**
 * Exception class thrown when an insertion performed by the player is not allowed
 */
public class IllegalInsertionException extends Exception{
    public IllegalInsertionException(){
        System.err.println("Exception: Illegal insertion");
    }
}
