package it.polimi.ingsw.server.model.exceptions;

public class MaxPlayersException extends Exception {
    public MaxPlayersException(){
        System.err.println("Exception: Max Players");
    }
}