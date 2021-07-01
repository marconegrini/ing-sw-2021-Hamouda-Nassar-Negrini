package it.polimi.ingsw.exceptions;

/**
 * Exception class thrown when the specified storage's index is out of bounds.
 */
public class StorageOutOfBoundsException extends Exception{
    public StorageOutOfBoundsException() {
        System.err.println("Exception: Specified storage out of bounds");
    }
}
