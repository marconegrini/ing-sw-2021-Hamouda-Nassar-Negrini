package it.polimi.ingsw.exceptions;

/**
 * Exception class thrown when the player tries to activate a leader card already activated.
 */
public class AlreadyActivatedLeaderCardException extends Exception{
    public AlreadyActivatedLeaderCardException(){
        System.err.println("Exception: Leader card already activated");
    }

}
