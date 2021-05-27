package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.CLI.FaithPathTracer;
import it.polimi.ingsw.client.CLI.LeaderCardsTracer;
import it.polimi.ingsw.messages.fromClient.CalamaioResponseMessage;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.messages.fromClient.LoginMessage;
import it.polimi.ingsw.messages.fromClient.SelectLeaderCardMessage;
import it.polimi.ingsw.client.CLI.MarketTracer;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.messages.fromClient.*;
import it.polimi.ingsw.messages.fromServer.ServerMessage;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.ASCII_Resources;
import it.polimi.ingsw.model.exceptions.EmptySlotException;

import java.util.*;

/**
 * Class that manages CLI interactions with users
 */
public class CLIView extends View{

    Scanner scanner;
    LightModel clientLightModel;
    LeaderCardsTracer leaderCardsTracer;
    MarketTracer marketTracer;
    FaithPathTracer faithPathTracer;
    DepositsTracer depositsTracer;
    DvCardsTracer dvCardsTracer;

    public CLIView(LightModel clientLightModel){
        scanner = new Scanner(System.in);
        this.clientLightModel = clientLightModel;
        leaderCardsTracer = new LeaderCardsTracer();
        marketTracer = new MarketTracer();
        faithPathTracer = new FaithPathTracer();
        depositsTracer = new DepositsTracer();
        dvCardsTracer = new DvCardsTracer();
    }

    /**
     * Method that authenticates the user
     * @return A LoginMessage for the server
     */
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

//            System.out.println("Resource chosen successfully");

            if ( strIn.contains("third") ) {
                //TODO:aggiungi un punto fede alla struttura del client
            }
        }

        else if(strIn.contains("fourth")){
            int s=2;
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
        return new CalamaioResponseMessage(chosenResource, chosenResource2, destStorage1, destStorage2);
    }

//
//    @Override
//    public void printToClient() {
//        String
//    }

    /**
     * Show possible options of leader cards to choose and asks the client to select two of them.
     * @param leaderCards ArrayList of four leader cards
     * @return a SelectLeaderCards message for the server
     */
    @Override
    public ClientMessage selectLeaderCards(List<LeaderCard> leaderCards){
        String firstCard = "";
        String secondCard = "";
        Integer firstIndex = 0;
        Integer secondIndex = 0;
        System.out.println("Select 2 leader cards within possible ones.");
        try {
            //shows 4 options of leader cards to choose
            leaderCardsTracer.printLeaderCards(leaderCards).forEach(System.out::println);
        } catch (EmptySlotException e){
            System.out.println("Selected invalid slot");
            System.exit(-2);
        }
        boolean OK = false;
        while(!OK) {
                //TODO Number format exception
                System.out.println("Select first card: ");
                firstCard = scanner.nextLine();
                System.out.println("Select second card: ");
                secondCard = scanner.nextLine();
                //checks integrity of the two specified characters
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

    /**
     * Actions menu displayed every time a user starts his turn. Before selecting the action to perform, the user can call
     * the "show" command as long as he wants to see the status of personal board, market board and development cards deck.
     * @return a ClientMessage type corresponding to the action selected
     */
    @Override
    public ClientMessage selectAction() {
        String choice = "";
        ServerMessage selection;
        boolean selected = false;
        boolean show = true;
        while(!selected) {
            System.out.println("Select action to perform:\na) Take resources from market\nb) Buy development card\nc) Activate production");
            System.out.println("Sub actions:\nd) Activate leader card\ne) Discard leader card\nf) Move warehouse resources");
            System.out.println("\n[Type show + market/deposits/slots/faith path/development deck/slots/leader cards to see eventual updates]");
            while (show) {
                //if the user selects a show command, he will remain inside this WHILE and the scanner will be ready for a second
                //read. If an action command is specified, "show" is set to false, "selected" is set to true and the WHILE stops.
                System.out.println("\nMake a choice:");
                choice = scanner.nextLine();
                if (choice.equals("show market")) {
                    marketTracer.marketTracer(clientLightModel.getMarketBoard());
                    show = true;
                } else if (choice.equals("show deposits")) {
                    depositsTracer.depositsTracer(clientLightModel.getWarehouse(), clientLightModel.getCoffer()).forEach(System.out::println);
                    show = true;
                } else if (choice.equals("show faith path")) {
                    System.out.println("\n\t # Faith Path # \t");
                    faithPathTracer.faithPathTracer(clientLightModel.getOtherPlayersFaithPathPosition(), clientLightModel.getFaithPathPosition()).forEach(System.out::println);
                    show = true;
                } else if (choice.equals("show development deck")) {
                    System.out.println("\n\t # Development Cards Deck # \t");
                    dvCardsTracer.printDVCard(clientLightModel.getDevelopmentCardsDeck()).forEach(System.out::println);
                    show = true;
                } else if (choice.equals("show slots")) {

                    System.out.println("\n\t # Development Cards Slots # \t");
                    HashMap<Integer, DevelopmentCard> devCardsSlot = clientLightModel.getPeekDevCardsInSlot();

                    if(devCardsSlot.isEmpty())
                        System.out.println("\nEmpty development card slots!");
                    else for(Integer i : devCardsSlot.keySet()){
                        System.out.println("\n\t# Slot " + i + " #\t");
                        ArrayList<DevelopmentCard> dc = new ArrayList();
                        dc.add(devCardsSlot.get(i));
                        dvCardsTracer.printDVCard(dc).forEach(System.out::println);
                    }

                    show = true;
                }else if (choice.equals("show leader cards")) {
                    try {
                        leaderCardsTracer.printLeaderCards(clientLightModel.getLeaderCards()).forEach(System.out::println);
                    } catch (EmptySlotException e){
                        e.printStackTrace();
                    }
                    show = true;

                } else if (choice.equals("a")) {
                    show = false;
                    selected = true;
                    //return new PickResourcesMessage();
                } else if (choice.equals("b")) {

                    show = false;
                    selected = true;
                    //return new BuyDevCardMessage();
                } else if (choice.equals("c")) {

                    show = false;
                    selected = true;
                    //return new ActivateProductionMessage();
                } else if (choice.equals("d")) {

                    show = false;
                    selected = true;
                    //return new ActivateLeaderCardMessage();
                } else if (choice.equals("e")) {

                    show = false;
                    selected = true;
                    //return new DiscardLeaderCardMessage();
                } else if (choice.equals("f")) {

                    show = false;
                    selected = true;
                    //return new MoveWarehouseResourcesMessage();
                } else System.out.println("Invalid choice. Type again.");
            }
        }
        return null;
    }

    /**
     * A simple show method that prints messages on the console
     * @param message message to print
     */
    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints in console specified leader cards
     * @param leaderCards leader cards to print
     */
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

    @Override
    public void showComponent() {

    }

}
