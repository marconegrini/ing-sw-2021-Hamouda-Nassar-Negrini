package it.polimi.ingsw.server.model.exceptions;

public class IllegalMoveException extends Exception{
    public IllegalMoveException() {
        System.err.println("Exception: Illegal move");
    }
}
