package it.polimi.ingsw.model.exceptions;

public class PlayerNotExistsException extends Exception{
    public PlayerNotExistsException(){System.err.println("Invalid nickname: There isn't any player with this nickname");}
}