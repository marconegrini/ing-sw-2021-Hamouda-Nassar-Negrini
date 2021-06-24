package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;

import java.util.Scanner;

/**
 * Sent to the client if there are errors with login credentials, such as a repeated nickname.
 */
public class ServerLoginErrorMessage extends ServerMessage{

    String errorMessage;

    public ServerLoginErrorMessage(String errorMessage) {
        super(ServerMessageType.LOGIN_ERROR);
        this.errorMessage = errorMessage;
    }

    @Override
    public void clientProcess(ServerHandler serverHandler) {


        serverHandler.getView().showMessage("Insert another nickname",true , true);

        serverHandler.sendJson(serverHandler.getView().logClient());
        /*
        Scanner scanner = new Scanner(System.in);
        String nickname;
        boolean isMultiplayer;

        System.out.println(errorMessage);

        //Ask for nickname
        System.out.println("Insert another nickname:");
        do {
            nickname = scanner.nextLine();
            if (nickname.equals("")) System.out.println("You can't put an empty name. Type another nickname:");
        } while (nickname.equals(""));

        //Ask for game modality
        System.out.println("Do you want to play a multiplayer game? [Yes/No]");

        while (true){
            String choice = scanner.nextLine();
            if (!choice.equals("")){
                if (choice.equalsIgnoreCase("YES") || choice.equalsIgnoreCase("NO")){
                    isMultiplayer = choice.equalsIgnoreCase("YES");
                    break;
                }
            } else System.out.println("Invalid input. Type again your choice");
        }

        //Send back the answer message
        serverHandler.sendJson(new it.polimi.ingsw.messages.fromClient.LoginMessage(nickname, isMultiplayer));
    */
    }

}
