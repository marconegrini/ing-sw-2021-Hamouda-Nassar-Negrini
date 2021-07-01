package it.polimi.ingsw.exceptions;

/**
 * Exception class thrown when the user tries to perform a move in warehouse not allowed
 */
public class IllegalMoveException extends Exception{
    public IllegalMoveException() {
        System.err.println("Exception: Illegal move");
    }
}
