package it.polimi.ingsw.exceptions;

public class StorageOutOfBoundsException extends Exception{
    public StorageOutOfBoundsException() {
        System.err.println("Exception: Specified storage out of bounds");
    }
}
