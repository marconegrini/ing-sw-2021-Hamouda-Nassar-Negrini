package it.polimi.ingsw.server.model.exceptions;

public class AlreadyActivatedLeaderCardException extends Exception{
    public AlreadyActivatedLeaderCardException(){
        System.err.println("Exception: Leader card already activated");
    }

}
