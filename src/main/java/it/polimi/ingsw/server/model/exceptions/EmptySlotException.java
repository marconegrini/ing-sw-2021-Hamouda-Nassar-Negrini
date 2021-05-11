package it.polimi.ingsw.server.model.exceptions;

public class EmptySlotException extends Exception{
    public EmptySlotException(){
        System.err.println("Exception: EmptySlot");
    }
}
