package it.polimi.ingsw.model.exceptions;

public class IllegalInsertionException extends Exception{
    public IllegalInsertionException(){
        System.err.println("Exception: Illegal insertion");
    }
}
