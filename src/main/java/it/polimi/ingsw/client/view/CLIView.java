package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.CLI.*;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.messages.fromClient.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.ASCII_Resources;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.EmptySlotException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    boolean stillWaiting;

    public CLIView(LightModel clientLightModel){
        scanner = new Scanner(System.in);
        this.clientLightModel = clientLightModel;
        leaderCardsTracer = new LeaderCardsTracer();
        marketTracer = new MarketTracer();
        faithPathTracer = new FaithPathTracer();
        depositsTracer = new DepositsTracer();
        dvCardsTracer = new DvCardsTracer();
        stillWaiting = true;
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
            //TODO: send an update message to update the faithTrack of the client

//            System.out.println("Resource chosen successfully");

            if ( strIn.contains("third") ) {
                //TODO: send an update message to update the faithTrack of the client
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
            //TODO:send an update message to update the deposits of the client
            //TODO: send an update message to update the faithTrack of the client

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
            //shows 4 options of leader cards to choose
            leaderCardsTracer.printLeaderCards(leaderCards).forEach(System.out::println);

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
        ClientMessage selection = null;
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
                        leaderCardsTracer.printLeaderCards(clientLightModel.getLeaderCards()).forEach(System.out::println);

                    show = true;
                } else if (choice.equals("a")) {
                    //Take resources from market
                    marketTracer.marketTracer(clientLightModel.getMarketBoard());
                    boolean isRow = true;
                    Integer rowOrColNum = 0;
                    System.out.println("\nInsert external marble in the market.");
                    boolean OK = false;
                    while(!OK) {
                        System.out.println("Row or column?");
                        String rowOrCol = scanner.nextLine();
                        if(rowOrCol.equalsIgnoreCase("COLUMN")){
                            OK = true;
                            isRow = false;
                        } else if(rowOrCol.equalsIgnoreCase("ROW")){
                            OK = true;
                            isRow = true;
                        }
                    }

                    while(OK){
                        try {
                            if(isRow){
                                System.out.println("Insert row number:");
                                rowOrColNum = scanner.nextInt();
                                if(rowOrColNum < 1 || rowOrColNum > 3){
                                    System.out.println("Row number doesn't exists! Type again");
                                } else OK = false;
                            } else {
                                System.out.println("Insert column number:");
                                rowOrColNum = scanner.nextInt();
                                if(rowOrColNum < 1 || rowOrColNum > 4){
                                    System.out.println("Column number doesn't exists! Type again");
                                } else OK = false;
                            }
                        } catch (InputMismatchException e){
                            OK = false;
                            System.out.println("Bad input format. Type again row or column number");
                        }
                    }
                    show = false;
                    selected = true;
                    selection = new PickResourcesMessage(isRow, rowOrColNum);
                } else if (choice.equals("b")) {
                    //Buy development card

                    show = false;
                    selected = true;
                    //selection = new BuyDevCardMessage();
                } else if (choice.equals("c")) {
                    //Activate production

                    show = false;
                    selected = true;
                    //selection = new ActivateProductionMessage();
                } else if (choice.equals("d")) {
                    //Activate Leader card

                    show = false;
                    selected = true;
                    //selection = new ActivateLeaderCardMessage();
                } else if (choice.equals("e")) {
                    //Discard leader card

                    show = false;
                    selected = true;
                    //selection = new DiscardLeaderCardMessage();
                } else if (choice.equals("f")) {
                    //Move warehouse resources

                    show = false;
                    selected = true;
                    //selection = new MoveWarehouseResourcesMessage();
                } else System.out.println("Invalid choice. Type again.");
            }
        }
        return selection;
    }

    @Override
    public ClientMessage storeResources(List<Resource> resources){
        this.showResources(resources);
        return null;
    }


    /**
     * this method manage the time that the client will be in the waiting room waiting for other players.
     * It allows the client to type commands on the terminal and to exit the game or to start it at any time.
     *
     * @return  A ClientMessage object that will handle the choose of the client. It could be a AskStartGameMessage,
     *          a ExitFromGameMessage or a EmptyMessage. The EmptyMessage is returned only when the game was started
     *          from another client.
     */
    @Override
    public ClientMessage waitingRoom() {

        String input;

        // Use of bufferedReader because it has the method ready() that is useful for our purpose
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(isr);

        System.out.println("Type START to start the game or EXIT to leave the game.");

        while (stillWaiting) {
            try {
                if (buffer.ready()){
                    input = buffer.readLine();
                    if (input.equalsIgnoreCase("START")) {
                        return new AskStartGameMessage();
                    } else if (input.equalsIgnoreCase("EXIT")) {
                        return new ExitFromGameMessage();
                    } else
                        System.out.println("Bad command: Type again another choice.\nType START to start the game or EXIT to leave the game.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new EmptyMessage();
    }

    /**
     * This method is used from the StartGameMessage to end the while(stillWaiting) loop of the method waitingRoom()
     */
    @Override
    public void endWaitingRoom() {
        stillWaiting = false;
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
            ArrayList<String> output = leaderCardsTracer.printLeaderCards(leaderCards);
            output.forEach(System.out::println);

    }

    @Override
    public void showResources(List<Resource> resources) {
        int i = 1;
        System.out.println("\t# Resources #\t");
        for(Resource res : resources) {
            System.out.println(i + ") " + res.toString());
            i++;
        }
    }



}
