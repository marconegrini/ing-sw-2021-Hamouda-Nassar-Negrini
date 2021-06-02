package it.polimi.ingsw.model.exceptions;

public class EmptyDeckException extends Exception {
    public EmptyDeckException(){
        System.err.println("Exception: Empty Deck");
    }
}
