package it.polimi.ingsw.exceptions;

public class EmptySlotException extends Exception{
    public EmptySlotException(){
        System.err.println("Exception: EmptySlot");
    }
}
