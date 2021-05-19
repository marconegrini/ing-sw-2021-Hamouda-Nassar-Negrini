package it.polimi.ingsw.messages.updateFromServer;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.server.controller.TurnManager;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UpdateLeaderCardMessage extends Message {

    List<LeaderCard> leaderCards;

    public UpdateLeaderCardMessage(String nickname, List<LeaderCard> leaderCards){
        super(nickname, MessageType.UPDATELEADERCARD);
        this.leaderCards = leaderCards;
    }

    @Override
    public boolean serverProcess(Player player, TurnManager turnManager) {
        Gson gson = new Gson();
        String messageToSend = gson.toJson(this);
        try {
            player.getToClient().writeUTF(messageToSend);
        } catch (IOException e){
            System.err.println("Exception occurred while sending json");
        }
        return true;
    }

    @Override
    public boolean clientProcess(DataOutputStream dos){
        System.out.println("\nchoose a leader card (index from 1 to 4):");
        int i = 1;
        for(LeaderCard leaderCard : leaderCards){
            System.out.println(i + ")\n" + leaderCard.toString());
        }
        Scanner scanner = new Scanner(System.in);
        boolean again = true;
        while(again) {
            System.out.println("Choose first leader card index: ");
            String index1 = scanner.nextLine();
            Integer firstIndex;
            System.out.println("Choose second leader card index: ");
            String index2 = scanner.nextLine();
            Integer secondIndex;
            try {
                firstIndex = Integer.parseInt(index1);
                secondIndex = Integer.parseInt(index2);
                if((firstIndex < 1 || firstIndex > 4) || (secondIndex < 1 || secondIndex > 4)){
                    System.out.println("One ore both indexes are out of bounds. Type again your choice:");
                    again = true;
                } else {
                    again = false;
                }
            } catch (NumberFormatException e) {
                again = true;
                System.out.println("The input format is not valid. Type again your choice: ");
            }
        }
        return false;
    }
}
