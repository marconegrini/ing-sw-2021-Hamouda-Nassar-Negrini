package it.polimi.ingsw.server.controller;

public class ErrorMessage extends Message{
    private final String error;

    public ErrorMessage(String nickname, String error) {
        super(nickname, MessageType.ERROR);
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "nickname=" + getNickname() +
                ", error=" + error +
                '}';
    }

    @Override
    public String fromJsonToMessage() {
        return null;
    }
}
