package it.polimi.ingsw.messages.fromServer.update;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.messages.fromServer.ServerMessageType;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.util.ArrayList;

public class UpdateDevCardsDeckMessage extends ServerMessage {

    private ArrayList<DevelopmentCard> developmentCardsDeck;

    public UpdateDevCardsDeckMessage(ArrayList<DevelopmentCard> developmentCardsDeck) {
        super(ServerMessageType.UPDATEDEVCARDSDECK);
        this.developmentCardsDeck = developmentCardsDeck;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getLightModel().setDevelopmentCardsDeck(this.developmentCardsDeck);
    }
}
