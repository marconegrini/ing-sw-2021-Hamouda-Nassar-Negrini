package it.polimi.ingsw.server.model.exceptions;

public class AlreadyDiscardedLeaderCardException extends Exception{
    public AlreadyDiscardedLeaderCardException(){
        System.err.println("Exception: Leader Card already activated");
    }

}
