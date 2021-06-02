package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.model.enumerations.LorenzoCardType;

public class SinglePlayerActionMessage extends ServerMessage{

    private String actionPerformed;
    private LorenzoCardType lorenzoCard;

    public SinglePlayerActionMessage(String actionPerformed, LorenzoCardType lorenzoCard) {
        super(ServerMessageType.SINGLEPLAYERACTION);
        this.actionPerformed = actionPerformed;
        this.lorenzoCard = lorenzoCard;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        serverHandler.getView().showMessage(actionPerformed);
    }
}
