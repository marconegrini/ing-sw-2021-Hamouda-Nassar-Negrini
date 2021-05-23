package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.server.handlers.ClientHandler;


public class BuyDevCardMessage extends ClientMessage{

    private int row;
    private int column;
    private int devCardSlot;

    public BuyDevCardMessage(int row, int column, int devCardSlot ) {
        super(ClientMessageType.BUYDEVELOPMENTCARD);
        this.row = row;
        this.column = column;
        this.devCardSlot = devCardSlot;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {

    }
}
