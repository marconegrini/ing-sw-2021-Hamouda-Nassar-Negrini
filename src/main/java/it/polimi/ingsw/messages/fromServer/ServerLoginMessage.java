package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.LoginMessage;

import java.util.Scanner;

public class ServerLoginMessage extends ServerMessage{

    public ServerLoginMessage() {
        super(ServerMessageType.LOGIN);
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {
        //System.out.println("Inside client process");
        Scanner scanner = new Scanner(System.in);
        String nickname;
        boolean isMultiplayer;

        //Ask for nickname
        System.out.println("Insert your nickname:");
        do {
            nickname = scanner.nextLine();
            if (nickname.isBlank() || nickname.isEmpty()) System.out.println("You can't put an empty name. Type another nickname:");
        } while (nickname.isBlank() || nickname.isEmpty());

        //Ask for game modality
        System.out.println("Do you want to play a multiplayer game? [Yes/No]");

        while (true){
            String choice = scanner.nextLine();
            if (!choice.isEmpty()){
                if(!choice.isBlank()){
                    if (choice.equalsIgnoreCase("YES") || choice.equalsIgnoreCase("NO")) {
                        isMultiplayer = choice.equalsIgnoreCase("YES");
                        break;
                    }
                }
            }
            System.out.println("Invalid input. Type again your choice");
        }

        //Send back the answer message
        serverHandler.sendJson(new LoginMessage(nickname, isMultiplayer));
    }
}
