package it.polimi.ingsw.exceptions;

public class AlreadyDiscardedLeaderCardException extends Exception{
    public AlreadyDiscardedLeaderCardException(){
        System.err.println("Exception: Leader Card already discarded");
    }

}