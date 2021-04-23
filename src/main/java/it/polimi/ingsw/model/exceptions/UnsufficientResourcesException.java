package it.polimi.ingsw.model.exceptions;

public class UnsufficientResourcesException extends Exception{
    public UnsufficientResourcesException(){
        System.err.println("Exception: Player doesn't have enough resources");
    }
}
