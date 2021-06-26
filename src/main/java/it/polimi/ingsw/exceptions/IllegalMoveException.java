package it.polimi.ingsw.exceptions;

public class IllegalMoveException extends Exception{
    public IllegalMoveException() {
        System.err.println("Exception: Illegal move");
    }
}
