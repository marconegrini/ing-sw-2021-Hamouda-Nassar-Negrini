package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.CLI.LeaderCardsTracer;
import it.polimi.ingsw.messages.fromClient.CalamaioResponse;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromClient.LoginMessage;
import it.polimi.ingsw.messages.fromClient.SelectLeaderCardMessage;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.ASCII_Resources;
import it.polimi.ingsw.model.exceptions.EmptySlotException;

import java.util.ArrayList;
import java.util.InputMismatchException;
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
    public ClientMessage initializeCalamaio(String strIn) {

        ClientMessage calamaioResponse;

        String resources = "1."+ ASCII_Resources.SHIELD+" - Shield\n";
        resources += "2."+ ASCII_Resources.COIN+" - Coin\n";
        resources += "3."+ ASCII_Resources.SERVANT+" - Servant\n";
        resources += "4."+ ASCII_Resources.STONE+" - Stone\n";
        int chosenResource=0;
        int chosenResource2=0;
        int destStorage1=0;
        int destStorage2=0;


        System.out.println(strIn);
        if (strIn.contains("first")){
            System.out.println(strIn);
        }
        else if( (strIn.contains("second")) || (strIn.contains("third") )){
            System.out.println(strIn);
            System.out.println(resources);
            chosenResource=scanner.nextInt();
            while ( chosenResource>4 || chosenResource<1 ){
                System.out.println("Wrong input, please choose another resource\n");
                System.out.println(resources);
                chosenResource=scanner.nextInt();
            }
            System.out.println("in which destination storage do you want to save the resource?\n");
            destStorage1=scanner.nextInt();
            while ( destStorage1>3 || destStorage1<1 ){
                System.out.println("Wrong input, please choose another storage number\n");;
                destStorage1=scanner.nextInt();
            }
            //TODO:aggiungi la risorsa scelta alla struttura del client

            System.out.println("Resource chosen were added successfully");

            if ( strIn.contains("third") ) {
                //TODO:aggiungi un punto fede alla struttura del client
            }
        }

        else if(strIn.contains("fourth")){

            System.out.println(strIn);
            System.out.println(resources);
            chosenResource=scanner.nextInt();

            //when the both conditions are true continue;; it's evaluated before entering the loop.
            while ( (chosenResource>4||chosenResource<1)){
                System.out.println("Wrong input, please choose another resource\n");
                System.out.println(resources);
                chosenResource = scanner.nextInt();
            }
            System.out.println("in which destination storage do you want to save the resource?\n");
            destStorage1=scanner.nextInt();
            while ( destStorage1>3 || destStorage1<1 ){
                System.out.println("Wrong input, please choose another storage number\n");;
                destStorage1=scanner.nextInt();
            }
            //when the both conditions are true continue;; it's evaluated before entering the loop.
            System.out.println("enter the second resource that you want to chose: ");
            chosenResource2=scanner.nextInt();
            while ( (chosenResource2>4||chosenResource2<1)){
                System.out.println("Wrong input, please choose another resource\n");
                System.out.println(resources);
                chosenResource2 = scanner.nextInt();
            }
            System.out.println("in which destination storage do you want to save the resource?\n");
            destStorage1=scanner.nextInt();
            while ( destStorage2>3 || destStorage2<1 ){
                System.out.println("Wrong input, please choose another storage number\n");
                destStorage2=scanner.nextInt();
            }
            //TODO:aggiungi le due risorse scelte alla struttura del client
            //TODO:aggiungi un punto fede alla struttura del client
//            System.out.println("Resource chosen successfully");
        }
        return new CalamaioResponse(chosenResource, chosenResource2, destStorage1, destStorage2);
    }

//
//    @Override
//    public void printToClient() {
//        String
//    }



    @Override
    public ClientMessage selectLeaderCards(List<LeaderCard> leaderCards){
        String firstCard = "";
        String secondCard = "";
        Integer firstIndex = 0;
        Integer secondIndex = 0;
        System.out.println("Select 2 leader cards within possible ones.");
        try {
            ArrayList<String> output = leaderCardsTracer.printLeaderCards(leaderCards);
            output.forEach(System.out::println);
        } catch (EmptySlotException e){
            System.out.println("Selected invalid slot");
            System.exit(-2);
        }

        boolean OK = false;
        while(!OK) {
                //TODO Number format exception
                System.out.println("Select first card: ");
                firstCard = scanner.next();
                System.out.println("Select second card: ");
                secondCard = scanner.next();
                if(firstCard.equals("a") || firstCard.equals("b") || firstCard.equals("c") || firstCard.equals("d")){
                    if(secondCard.equals("a") || secondCard.equals("b") || secondCard.equals("c") || secondCard.equals("d")){
                        if(!firstCard.equals(secondCard)){
                            OK = true;
                            if(firstCard.equals("a")) firstIndex = 0;
                            if(firstCard.equals("b")) firstIndex = 1;
                            if(firstCard.equals("c")) firstIndex = 2;
                            if(firstCard.equals("d")) firstIndex = 3;
                            if(secondCard.equals("a")) secondIndex = 0;
                            if(secondCard.equals("b")) secondIndex = 1;
                            if(secondCard.equals("c")) secondIndex = 2;
                            if(secondCard.equals("d")) secondIndex = 3;
                        } else System.out.println("Card values are identical. Type again.");
                    } else System.out.println("Second card value doesn't exists. Type again.");
                } else System.out.println("First card value doesn't exists. Type again.");
        }
        return new SelectLeaderCardMessage(firstIndex, secondIndex);
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showLeaderCards(List<LeaderCard> leaderCards){
        try {
            ArrayList<String> output = leaderCardsTracer.printLeaderCards(leaderCards);
            output.forEach(System.out::println);
        } catch (EmptySlotException e){
            System.out.println("Selected invalid slot");
            System.exit(-2);
        }
    }

}
