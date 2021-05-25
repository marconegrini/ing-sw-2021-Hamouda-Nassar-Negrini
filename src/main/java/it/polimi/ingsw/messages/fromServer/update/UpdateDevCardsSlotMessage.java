package it.polimi.ingsw.messages.fromServer.update;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;

public class UpdateDevCardsSlotMessage extends ServerMessage {

    public UpdateDevCardsSlotMessage() {
        super(ServerMessageType.UPDATEDEVCARDSSLOT);
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {

    }
}
