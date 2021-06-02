package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.messages.fromServer.InWaitingRoomMessage;
import it.polimi.ingsw.messages.fromServer.ParticipantsMessage;
import it.polimi.ingsw.messages.fromServer.ServerLoginErrorMessage;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.handlers.ClientHandler;


public class LoginMessage extends ClientMessage {
    private String nickname;
    private boolean isMultiplayer;

    public LoginMessage(String nickname, boolean isMultiplayer) {
        super(ClientMessageType.LOGIN);
        this.nickname = nickname;
        this.isMultiplayer = isMultiplayer;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {

        if (isMultiplayer) {
            //Server.printCurrentPlayers();
            if (Server.nicknameAlreadyExist(nickname))
                clientHandler.sendJson(new ServerLoginErrorMessage("Error: Nickname already exists."));
            else {
                clientHandler.setNickname(nickname);
                System.out.println("Added " + clientHandler.getNickname());
                Server.add(clientHandler);
                if(Server.getPlayersNumber() == 4)
                    Server.startMultiplayerGame();
                else {
                    Server.sendParticipantsNumberUpdate();
                    clientHandler.sendJson(new InWaitingRoomMessage());
                    clientHandler.sendJson(new ParticipantsMessage(Server.getPlayersNumber() - 1));
                }

                /*
                if(Server.getPlayersNumber() == 2)
                Server.startMultiplayerGame();
                 */
            }
        } else {
            clientHandler.setNickname(nickname);
            System.out.println(nickname + " started a single player game");
            Server.startSinglePlayerGame(clientHandler);
        }
    }
}
