package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.server.Server;

public class ParticipantsMessage extends ServerMessage{

    private Integer participantsNumber;

    public ParticipantsMessage() {
        super(ServerMessageType.PARTICIPANTS);
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        //serverHandler.updateParticipants(Server.getParticipants());
    }
}
