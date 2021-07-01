package it.polimi.ingsw.exceptions;

/**
 * Exception class thrown when players in multiplayer game are more than four
 */
public class MaxPlayersException extends Exception {
    public MaxPlayersException(){
        System.err.println("Exception: Max Players");
    }
}
