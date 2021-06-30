package it.polimi.ingsw.exceptions;

/**
 * Exception class thrown when the player doesn't have resources needed to activate or buy a specified object.
 */
public class InsufficientResourcesException extends Exception{
    public InsufficientResourcesException(){
        System.err.println("Exception: Player doesn't have enough resources");
    }
}
