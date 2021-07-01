package it.polimi.ingsw.exceptions;

/**
 * Exception class thrown when the player tries to buy a development card deck with no cards
 */
public class EmptySlotException extends Exception{
    public EmptySlotException(){
        System.err.println("Exception: EmptySlot");
    }
}
