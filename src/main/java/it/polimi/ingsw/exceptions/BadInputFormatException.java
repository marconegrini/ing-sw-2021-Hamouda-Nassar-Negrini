package it.polimi.ingsw.exceptions;

public class BadInputFormatException extends Exception{
    public BadInputFormatException(){
        System.err.println("Exception: Bad input format");
    }
}
