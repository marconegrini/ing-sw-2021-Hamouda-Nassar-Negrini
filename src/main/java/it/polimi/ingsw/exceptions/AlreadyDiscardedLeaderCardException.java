package it.polimi.ingsw.exceptions;

/**
 * Exception class thrown when the user tries to activate or discard a leader card already discarded
 */
public class AlreadyDiscardedLeaderCardException extends Exception{
    public AlreadyDiscardedLeaderCardException(){
        System.err.println("Exception: Leader Card already discarded");
    }

}
