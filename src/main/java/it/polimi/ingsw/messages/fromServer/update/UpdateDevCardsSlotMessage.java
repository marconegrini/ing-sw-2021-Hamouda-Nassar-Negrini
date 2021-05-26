package it.polimi.ingsw.messages.fromServer.update;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.util.HashMap;
import java.util.List;

public class UpdateDevCardsSlotMessage extends ServerMessage {

    HashMap<Integer, DevelopmentCard> cardsInSlot;

    public UpdateDevCardsSlotMessage(HashMap<Integer, DevelopmentCard> cardsInSlot) {
        super(ServerMessageType.UPDATEDEVCARDSSLOT);
        this.cardsInSlot = cardsInSlot;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getLightModel().setPeekDevCardsInSlot(cardsInSlot);
    }
}
