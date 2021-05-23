package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.server.handlers.ClientHandler;

public class PickResourcesMessage extends ClientMessage{

    private int rowOrColNum;
    private boolean isRow;

    public PickResourcesMessage(int rowOrColNum, boolean isRow) {
        super(ClientMessageType.PICKRESOURCES);
        this.rowOrColNum = rowOrColNum;
        this.isRow = isRow;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {

    }
}
