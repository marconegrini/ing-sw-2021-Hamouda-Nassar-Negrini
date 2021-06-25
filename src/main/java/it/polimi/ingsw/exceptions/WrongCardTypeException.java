package it.polimi.ingsw.exceptions;

public class WrongCardTypeException extends Exception{
    public WrongCardTypeException() {
        System.err.println("Exception: wrong card type (might be a wrong casting for a leader card)");
    }
}
