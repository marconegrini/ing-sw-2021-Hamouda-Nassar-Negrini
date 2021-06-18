package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.server.Server;

/**
 * Shows to the client in waiting room the number of participants waiting with him
 */
public class ParticipantsMessage extends ServerMessage{

    private Integer participantsNumber;

    public ParticipantsMessage(Integer participantsNumber) {
        super(ServerMessageType.PARTICIPANTS);
        this.participantsNumber = participantsNumber;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getView().showParticipantsNumber(participantsNumber.toString());
    }
}
