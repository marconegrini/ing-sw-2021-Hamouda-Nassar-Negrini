package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.CLI.LeaderCardsTracer;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromClient.LoginMessage;
import it.polimi.ingsw.messages.fromClient.SelectLeaderCardMessage;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.exceptions.EmptySlotException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLIView extends View{

    Scanner scanner;
    LeaderCardsTracer leaderCardsTracer;

    public CLIView(){
        scanner = new Scanner(System.in);
        leaderCardsTracer = new LeaderCardsTracer();
    }

    @Override
    public ClientMessage logClient(){

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
        return new LoginMessage(nickname, isMultiplayer);
    }

    @Override
    public ClientMessage selectLeaderCards(List<LeaderCard> leaderCards){
        Integer firstIndex = 0;
        Integer secondIndex = 0;
        System.out.println("Select 2 leader cards within possible ones (indexes from 1-4).");
        try {
            ArrayList<String> output = leaderCardsTracer.printLeaderCards(leaderCards);
            output.forEach(System.out::println);
        } catch (EmptySlotException e){
            System.out.println("Selected invalid slot");
            System.exit(-2);
        }

        boolean OK = false;
        while(!OK) {
            System.out.println("Insert first index: ");
            firstIndex = scanner.nextInt();
            System.out.println("Insert second index: ");
            secondIndex = scanner.nextInt();
            if (!firstIndex.equals(secondIndex)) {
                if((1 <= firstIndex && firstIndex <= leaderCards.size()) &&
                        (1 <= secondIndex && secondIndex <= leaderCards.size())){
                    OK = true;
                    firstIndex--;
                    secondIndex--;
                } else System.out.println("One or both typed indexes are not between 1 and 4.");
            } else System.out.println("Indexes are identical.");
        }
        return new SelectLeaderCardMessage(firstIndex, secondIndex);
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

}
