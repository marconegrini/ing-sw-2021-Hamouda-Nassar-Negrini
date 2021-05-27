package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.server.Server;

public class ParticipantsMessage extends ServerMessage{

    private Integer participantsNumber;

    public ParticipantsMessage(Integer participantsNumber)
    {
        super(ServerMessageType.PARTICIPANTS);
        this.participantsNumber = participantsNumber;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getView().showMessage("Waiting with other " + participantsNumber + " players");
    }
}
