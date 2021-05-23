package it.polimi.ingsw.model.exceptions;

public class WrongCardTypeException extends Exception{
    public WrongCardTypeException() {
        System.err.println("Exception: wrong card type (might be a wrong casting for a leader card)");
    }
}
