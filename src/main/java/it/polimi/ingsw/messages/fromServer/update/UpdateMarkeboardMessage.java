package it.polimi.ingsw.messages.fromServer.update;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarketBoard;

public class UpdateMarkeboardMessage extends ServerMessage {

    private MarketBoard marketBoard;

    public UpdateMarkeboardMessage(MarketBoard marketBoard) {
        super(ServerMessageType.UPDATEMARKETBOARD);
        this.marketBoard = marketBoard;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getLightModel().setMarketBoard(this.marketBoard);
    }
}
