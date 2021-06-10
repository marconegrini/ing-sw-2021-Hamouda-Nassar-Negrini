package it.polimi.ingsw.messages.fromServer.update;

import it.polimi.ingsw.client.CLI.MarketTracer;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarketBoard;

/**
 * update method to update client's marketboard after marbles have been picked
 */
public class UpdateMarketboardMessage extends ServerMessage {

    private MarketBoard marketBoard;

    public UpdateMarketboardMessage(MarketBoard marketBoard) {
        super(ServerMessageType.UPDATEMARKETBOARD);
        this.marketBoard = marketBoard;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getLightModel().setMarketBoard(this.marketBoard);
    }
}
