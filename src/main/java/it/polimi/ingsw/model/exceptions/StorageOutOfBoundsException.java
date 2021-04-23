package it.polimi.ingsw.model.exceptions;

public class StorageOutOfBoundsException extends Exception{
    public StorageOutOfBoundsException() {
        System.err.println("Exception: Specified storage out of bounds");
    }
}
