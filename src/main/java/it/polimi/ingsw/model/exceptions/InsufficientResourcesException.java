package it.polimi.ingsw.model.exceptions;

public class InsufficientResourcesException extends Exception{
    public InsufficientResourcesException(){
        System.err.println("Exception: Player doesn't have enough resources");
    }
}
