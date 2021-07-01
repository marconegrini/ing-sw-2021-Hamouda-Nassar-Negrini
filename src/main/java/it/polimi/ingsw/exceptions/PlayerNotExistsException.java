package it.polimi.ingsw.exceptions;

/**
 * Exception class thrown when the multiplayer game has reached more than four players.
 */
public class PlayerNotExistsException extends Exception{
    public PlayerNotExistsException(){System.err.println("Invalid nickname: There isn't any player with this nickname");}
}
