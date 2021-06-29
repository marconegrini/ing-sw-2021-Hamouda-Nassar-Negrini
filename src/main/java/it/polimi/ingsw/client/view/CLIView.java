package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.CLI.*;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.messages.fromClient.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.StorageLeaderCard;
import it.polimi.ingsw.enumerations.ANSITextFormat;
import it.polimi.ingsw.enumerations.ASCII_Resources;
import it.polimi.ingsw.enumerations.CardType;
import it.polimi.ingsw.enumerations.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Class that manages CLI interactions with users
 */
public class CLIView extends View {

    Scanner scanner;
    LightModel clientLightModel;
    LeaderCardsTracer leaderCardsTracer;
    MarketTracer marketTracer;
    FaithPathTracer faithPathTracer;
    DepositsTracer depositsTracer;
    DvCardsTracer dvCardsTracer;
    VaticanSectionsTracer vaticanSectionsTracer;

    boolean stillWaiting;

    public CLIView(LightModel clientLightModel) {
        scanner = new Scanner(System.in);
        this.clientLightModel = clientLightModel;
        leaderCardsTracer = new LeaderCardsTracer();
        marketTracer = new MarketTracer();
        faithPathTracer = new FaithPathTracer();
        vaticanSectionsTracer = new VaticanSectionsTracer();
        depositsTracer = new DepositsTracer();
        dvCardsTracer = new DvCardsTracer();
        stillWaiting = true;
    }

    /**
     * Method that authenticates the user
     *
     * @return A LoginMessage for the server
     */
    @Override
    public ClientMessage logClient() {

        String nickname;
        boolean isMultiplayer;

        //Ask for nickname
        System.out.println("Insert your nickname:");
        do {
            nickname = secureReadString("[a-zA-Z0-9_]+");
            if (nickname.isBlank() || nickname.isEmpty())
                System.out.println("You can't put an empty name. Type another nickname:");
        } while (nickname.isBlank() || nickname.isEmpty());

        //Ask for game modality
        System.out.println("Do you want to play a multiplayer game? [Yes/No]");

        while (true) {
            String choice = scanner.nextLine();
            if (!choice.isEmpty()) {
                if (!choice.isBlank()) {
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

    /**
     * Initialises calamaio and enables users to select resources to insert in warehouse
     * @param strIn
     * @return
     */
    @Override
    public ClientMessage initializeCalamaio(String strIn) {

        ClientMessage calamaioResponse;

        String resources = "1." + ASCII_Resources.SHIELD + " - Shield\n";
        resources += "2." + ASCII_Resources.COIN + " - Coin\n";
        resources += "3." + ASCII_Resources.SERVANT + " - Servant\n";
        resources += "4." + ASCII_Resources.STONE + " - Stone\n";
        int chosenResource = 0;
        int chosenResource2 = 0;
        int destStorage1 = 0;
        int destStorage2 = 0;


        if (strIn.contains("first")) {
            System.out.println(strIn);
        } else if ((strIn.contains("second")) || (strIn.contains("third"))) {
            System.out.println(strIn);
            System.out.println(resources);
            chosenResource = secureReadInt("[1-4]");

            System.out.println("in which destination storage do you want to save the resource?\n");

            destStorage1 = secureReadInt("[1-3]");


//            System.out.println("Resource chosen successfully");

            if (strIn.contains("third")) {
            }
        } else if (strIn.contains("fourth")) {
            int s = 2;
            System.out.println(strIn);
            System.out.println(resources);

            chosenResource = secureReadInt("[1-4]");

            System.out.println("in which destination storage do you want to save the first resource?\n");
            destStorage1 = secureReadInt("[1-3]");

            System.out.println("enter the second resource that you want to chose: ");
            chosenResource2 = secureReadInt("[1-4]");

            System.out.println("in which destination storage do you want to save the second resource?\n");
            destStorage2 = secureReadInt("[1-3]");

        }
        return new CalamaioResponseMessage(chosenResource, chosenResource2, destStorage1, destStorage2);
    }


    //error happends only with the 4th player (that should choose 2 resources).
    @Override
    public ClientMessage calamaioErrHandelr(String strIn) {
        if (strIn.equals("StorageOutOfBoundsException")){
            System.out.println("Storage shelf out of Bound, please choose another shelf and consider moving elements between shelves if necessary.\n");
        }
        else if(strIn.equals("IllegalInsertionException")){
            System.out.println("Illegal insertion in shelves, please choose another shelf and consider moving elements between shelves if necessary.\n");
        }

        return initializeCalamaio("You are the fourth player, choose 2 resources:\n");
    }

    /**
     * Show possible options of leader cards to choose and asks the client to select two of them.
     *
     * @param leaderCards ArrayList of four leader cards
     * @return a SelectLeaderCards message for the server, containing the indexes of the two selected leader cards
     */
    @Override
    public ClientMessage selectLeaderCards(List<LeaderCard> leaderCards) {
        String firstCard = "";
        String secondCard = "";
        Integer firstIndex = 0;
        Integer secondIndex = 0;
        System.out.println("Select 2 leader cards within possible ones.");
        //shows 4 options of leader cards to choose
        leaderCardsTracer.printLeaderCards(leaderCards).forEach(System.out::println);

        boolean OK = false;
        while (!OK) {
            //TODO Number format exception
            System.out.println("Select first card: ");
            firstCard = secureReadString("[a-d]");
            System.out.println("Select second card: ");
            secondCard = secureReadString("[a-d]");
            if (!firstCard.equals(secondCard)) {
                OK = true;
                firstIndex = firstCard.charAt(0) - 'a';
                secondIndex = secondCard.charAt(0) - 'a';
            } else System.out.println("Card values are identical. Type again.");
        }
        return new SelectLeaderCardMessage(firstIndex, secondIndex);
    }

    /**
     * Actions menu displayed every time a user starts his turn. Before selecting the action to perform, the user can call
     * the "show" command as long as he wants to see the status of personal board, market board and development cards deck.
     * Contains code to perform all action and each if statement returns a ClientMessageType of the respective action.
     * @return a ClientMessage type corresponding to the action selected
     * @return a ClientMessage type for the server corresponding to the action selected
     */
    @Override
    public ClientMessage selectAction(String choice, boolean err) {
        ClientMessage selection = null;
        boolean selected = false;
        boolean show = true;
        boolean return_ = false;
        boolean useStorageLCs = false;


        while (!selected) {
            if (!err || return_) {
                System.out.println(ANSITextFormat.BOLD+"Select action to perform:\n"+ANSITextFormat.RESET+"a) Take resources from market\nb) Buy development card\nc) Activate production");
                System.out.println("Sub actions:\nd) Activate leader card\ne) Discard leader card\nf) Move warehouse resources");
                System.out.println("\n[Type show + market/deposits/slots/faith path/development deck/slots/leader cards to see eventual updates]");
            }
            while (show) {
                if (return_)
                {
                    System.out.println(ANSITextFormat.BOLD+"Select action to perform:\n"+ANSITextFormat.RESET+"a) Take resources from market\nb) Buy development card\nc) Activate production");
                    System.out.println("Sub actions:\nd) Activate leader card\ne) Discard leader card\nf) Move warehouse resources");
                    System.out.println("\n[Type show + market/deposits/slots/faith path/development deck/slots/leader cards to see eventual updates]");
                }

                //if the user selects a show command, he will remain inside this WHILE and the scanner will be ready for a second
                //read. If an action command is specified, "show" is set to false, "selected" is set to true and the WHILE stops.
                System.out.println("\nMake a choice:");
                //if it's a normal read from user's input
                if (!err || return_) {
                    choice = scanner.nextLine();
                }//otherwise use the input passed as argument, it's the case when it's an error message and a client has already choosed what he wants.
                switch (choice) {
                    case "show market":
                        marketTracer.marketTracer(clientLightModel.getMarketBoard());
                        show = true;
                        break;
                    case "show deposits":
                        depositsTracer.depositsTracer(clientLightModel.getWarehouse(), clientLightModel.getCoffer()).forEach(System.out::println);
                        show = true;
                        break;
                    case "show faith path":
                        System.out.println(ANSITextFormat.BOLD_ITALIC+"\n\t # Faith Path # \t\n"+ANSITextFormat.RESET);
                        faithPathTracer.faithPathTracer(clientLightModel.getOtherPlayersFaithPathPosition(), clientLightModel.getFaithPathPosition()).forEach(System.out::println);
                        vaticanSectionsTracer.showVaticanSections(clientLightModel.getVaticanSections());
                        show = true;
                        break;
                    case "show development deck":
                        System.out.println("\n\t # Development Cards Deck # \t");
                        dvCardsTracer.printDVCard(clientLightModel.getDevelopmentCardsDeck()).forEach(System.out::println);
                        show = true;
                        break;
                    case "show slots":
                        showDevelopmentSlots();
                        show = true;
                        break;
                    case "show leader cards":
                        leaderCardsTracer.printLeaderCards(clientLightModel.getLeaderCards()).forEach(System.out::println);

                        show = true;
                        break;
                    case "a":
                        //Take resources from market
                        marketTracer.marketTracer(clientLightModel.getMarketBoard());
                        boolean isRow = true;
                        int rowOrColNum = 0;
                        System.out.println("\nInsert external marble in the market.");
                        boolean OK = false;
                        while (!OK) {
                            System.out.println("Row or column?\ntype \"return\" to select another action\n");
                            String rowOrCol = scanner.nextLine();
                            if (rowOrCol.equalsIgnoreCase("COLUMN")) {
                                OK = true;
                                isRow = false;
                            } else if (rowOrCol.equalsIgnoreCase("ROW")) {
                                OK = true;
                                isRow = true;
                            } else if (rowOrCol.equalsIgnoreCase("RETURN")) {
                                OK = false;
                                selected = false;
                                show = true;
                                return_ = true;
                                break;
                            }

                        }

                        while (OK) {
                            try {
                                if (isRow) {
                                    System.out.println("Insert row number:");
                                    rowOrColNum = secureReadInt("[1-3]");
                                } else {
                                    System.out.println("Insert column number:");
                                    rowOrColNum = secureReadInt("[1-4]");
                                }
                                OK = false;
                            } catch (InputMismatchException e) {
                                OK = false;
                                System.out.println("Bad input format. Type again row or column number\n");
                            }

                            String tempRead = "";
                            List<StorageLeaderCard> slds = new ArrayList<>();
                            if (clientLightModel.getLeaderCards().stream().anyMatch(x -> x.getCardType().equals(CardType.STORAGE) && x.isActivated())) {
                                for (LeaderCard ld : clientLightModel.getLeaderCards()) {
                                    if (ld.getCardType().equals(CardType.STORAGE))
                                        slds.add(((StorageLeaderCard) ld));
                                }
                                if (slds.stream().anyMatch(StorageLeaderCard::hasAvailableSlots)) {
                                    System.out.println("Do you want to use your storage leader cards power (y/n)? ");
                                    tempRead = secureReadString("(?i)(yes|no)|(y|n)(?-i)"); //recognizes y|Y|n|N|yes|Yes|YES|no|No|NO // (?i): opens the case insensitive read of input   //(?-i) ends the case insensitive read of input
                                    useStorageLCs = tempRead.matches("(?i)(yes)|(y)(?-i)"); //return true if yes and false if no.
                                }
                            }

                            show = false;
                            selected = true;
                            selection = new PickResourcesMessage(isRow, rowOrColNum, useStorageLCs);
                        }

                        break;
                    case "b":
                        if (err) {
                            System.out.println("Please check if you have sufficient resources to buy the card\n" +
                                    "Check also if you have inserted a wrong slot or an occupied slot number\n" +
                                    "Then try insert again a valid input: \n");
                            show = true;
                            selected = false;
                        } else {
                            //Buy development card
                            show = false;
                            selected = true;
                            return_ = false;
                        }
                        selection = buyDVCard(clientLightModel.getDevelopmentCardsDeck(), err);
                        err = false;
                        if (selection == null)  //if == null --> means the player choosed to choose another action.
                        {
                            selected = false;
                            show = true;
                            return_ = true;
//                            System.out.println("Make a choice: ");
//                            choice = secureReadString("[a-z]*");
                        }else{
                            show = false;
                            selected = true;
                            return_ = false;
                        }
                        break;
                    case "c":
                        //Activate production
                        selection = activateProduction();
                        if (selection == null){
                            show = true;
                            selected = false;
                            return_ = true;
                        }else {
                            show = false;
                            selected = true;
                            return_ = false;
                        }
                        break;
                    case "d": {
                        //Activate Leader card
                        Integer index = 0;
                        depositsTracer.depositsTracer(clientLightModel.getWarehouse(), clientLightModel.getCoffer());
                        leaderCardsTracer.printLeaderCards(clientLightModel.getLeaderCards()).forEach(System.out::println);
                        System.out.println("Select a leader card to activate:\n");
                        System.out.println("or type \"return\" to choose another action\n");
                        boolean okCards = false;
                        while (!okCards) {
                            String selectedCard = scanner.nextLine();
                            if (selectedCard.toUpperCase().equals("RETURN"))
                            {
                                show = true;
                                selected = false;
                                return_ = true;
                                okCards = true;
                            }else {
                                switch (selectedCard) {
                                    case "a":
                                        index = 0;
                                        okCards = true;
                                        break;
                                    case "b":
                                        index = 1;
                                        okCards = true;
                                        break;
                                    default:
                                        System.out.println("Invalid input. Type again.");
                                        break;
                                }
                            }
                        }
                        if (!return_) {
                            selection = new ActivateLeaderCardMessage(index);
                            show = false;
                            selected = true;
                        }
                        break;
                    }
                    case "e": {
                        //Discard leader card
                        Integer index = 0;
                        leaderCardsTracer.printLeaderCards(clientLightModel.getLeaderCards()).forEach(System.out::println);
                        System.out.println("Select a leader card to discard:");
                        System.out.println("or type \"return\" to choose another action\n");
                        boolean okCards = false;
                        while (!okCards) {
                            String selectedCard = scanner.nextLine();
                            if (selectedCard.toUpperCase().equals("RETURN")) {
                                show = true;
                                selected = false;
                                return_ = true;
                                okCards = true;
                            } else {
                                switch (selectedCard) {
                                    case "a":
                                        index = 0;
                                        okCards = true;
                                        break;
                                    case "b":
                                        index = 1;
                                        okCards = true;
                                        break;
                                    default:
                                        System.out.println("Invalid input. Type again.");
                                        break;
                                }
                            }
                        }
                        if (!return_) {
                            selection = new DiscardLeaderCardMessage(index);
                            show = false;
                            selected = true;
                        }
                        break;
                    }
                    case "f":
                        //Move warehouse resources
                        String ask = "";
                        Integer sourceStorage = 0;
                        Integer destStorage = 0;
                        Integer storage = 0;
                        for (int i = 0; i < 2; i++) {
                            if (i == 0) ask = "source shelf";
                            if (i == 1) ask = "destination shelf";
                            boolean okStorage = false;
                            System.out.println("Select " + ask + " storage (1 to 3):");
                            System.out.println("type \"return\" to choose another action");
                            try {
                                String input = secureReadString("(?i)(return)(?-i)|[1-3]");
                                if (input.matches("(?i)(return)(?-i)")){
                                    return_ = true;
                                    show = true;
                                    selected = false;
                                    break; //break the for loop
                                }else{
                                    storage = Integer.parseInt(input);
                                }
                                if (i == 0) sourceStorage = storage;
                                if (i == 1) destStorage = storage;
                            } catch (InputMismatchException e) {
                                scanner.nextLine();
                                System.out.println("Invalid input. Type again.");
                            }
                        }
                        if (!return_) {
                            selection = new MoveWarehouseResourcesMessage(sourceStorage, destStorage);
                            show = false;
                            selected = true;
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Type again.");
                        break;

                }

            }
        }
        return selection;
    }

    /**
     * Method invoked by ResourcesToStoreMessage, sent by server at the end of pick resources, containing the resources taken from market.
     * @param resources
     * @return InsertResourcesInWarehouseMessage, containing specified shelf and resources to insert or discard.
     */
    @Override
    public ClientMessage storeResources(List<Resource> resources) {
        this.showResources(resources);
        depositsTracer.depositsTracer(clientLightModel.getWarehouse(), clientLightModel.getCoffer()).forEach(System.out::println);
        System.out.println("Type INSERT or DISCARD to insert resources in warehouse or to discard them. \nNB: When discarding a resource you give other players a faith path point each");
        boolean OK = false;
        boolean discard = false;
        String CHOICE = "";
        Integer shelf = 0;
        List<Resource> resourcesToStore = new ArrayList<>();
        //asks the client to insert or discard resources
        while (!OK) {
            CHOICE = scanner.nextLine();
            CHOICE = CHOICE.toUpperCase(Locale.ROOT);
            switch (CHOICE) {
                case ("INSERT"):
                    OK = true;
                    break;
                case ("DISCARD"):
                    discard = true;
                    OK = true;
                    break;
                case (""):
                    break;
                default:
                    System.out.println("Type again your choice.");
                    break;
            }
        }
        //uses CHOICE to print the action to perform. Enables to put INSERT and DISCARD branch inside the same while cycle.
        CHOICE = CHOICE.toLowerCase(Locale.ROOT);

        //slot to insert resources
        Integer selected = 0;
        String returnedStr = "";
        //enters here only if INSERT option has been chosen.
        //Needed to acquire shelf value.
        if (!discard) {

                System.out.println("Select shelf to insert resources (1 to 3)");
                shelf = secureReadInt("[1-3]");

//            leaderCardsTracer.printLeaderCards(clientLightModel.getLeaderCards().stream().filter(x->x.getCardType().equals(CardType.STORAGE)).collect(Collectors.toList())).forEach(System.out::println);
//            shelf = secureReadInt("[a-" + '`' + clientLightModel.getLeaderCards().stream().filter(x->x.getCardType().equals(CardType.STORAGE)).count() + "]|[1-3]");

        }

        boolean resourceOK = false;
        //cycle that asks the user tho choose resources to discard or insert (in the selected slot)
        while (!resourceOK) {

            //Enters here only if resources List sent from server haven't been selected all already.
            //In the else branch, terminates while and sends message
            if(resources.size() > 0) {
                try {
                    this.showResources(resources);
                    System.out.println("Select resource to " + CHOICE + ":");
                    selected = secureReadInt("[1-"+ resources.size() +"]");

                    if (selected >= 1 && selected <= resources.size()) {
                        //resources are ordinated in View from 1 to resourcesIn.size()
                        selected--;
                        resourcesToStore.add(resources.get(selected));
                        Resource res = resources.get(selected);
                        resources.remove(res);

                        if(!resources.isEmpty()) {
                            String yn = "";
                            String inShelf = "";
                            if (!discard) inShelf = " in selected shelf";
                            System.out.println(CHOICE + " another resource" + inShelf + "? (YES/NO)");
                            boolean done = false;
                            while (!done) {
                                yn = scanner.nextLine();
                                yn = yn.toUpperCase(Locale.ROOT);
                                switch (yn) {
                                    case ("NO|N"):
                                        resourceOK = true;
                                        done = true;
                                        break;
                                    case ("YES|Y"):
                                        resourceOK = false;
                                        done = true;
                                        break;
                                    case (""):
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Type again.");
                                        break;
                                }
                            }
                        } else {
                            resourceOK = true;
                        }
                    } else System.out.println("Invalid resource number. Type again");

                } catch (InputMismatchException e) {
                    scanner.nextLine();
                    System.out.println("Invalid input.Type again.");
                }

            } else {
                System.out.println("Available resources ended!");
                resourceOK = true;
            }
        }
        System.out.println(resourcesToStore);
        return new InsertResourcesInWarehouseMessage(discard, resourcesToStore, shelf);
    }

    /**
     * Invoked by selectAction method if activate production option has been chosen. Production can be normal or of the
     * personal board.
     * @return an ActivateProductionMessage containing slots from which start production and eventually leader resources
     * if a production power leader card has been activated.
     */
    @Override
    public ClientMessage activateProduction(){
        ClientMessage toReturn = null;
        depositsTracer.depositsTracer(clientLightModel.getWarehouse(), clientLightModel.getCoffer()).forEach(System.out::println);
        System.out.println("Do you want to activate normal or personal production? Type NORMAL or PERSONAL to choose.\n");
        System.out.println("or type \"return\" to choose another action:\n");
        List<Integer> slots = new ArrayList<>();
        List<Resource> leaderResource;
        boolean done = false;
        while(!done){
            String prodType = scanner.nextLine();
            prodType = prodType.toUpperCase();
            if (prodType.equals("RETURN"))
                return null;
            switch (prodType){
                case ("NORMAL"):
                    showDevelopmentSlots();
                    Integer slotNumber= 0;
                    //if and only if there is at least one development card slot filled.
                    if(!clientLightModel.getPeekDevCardsInSlot().isEmpty()) {
                        boolean okSlot = false;
                        while (!okSlot) {
                            if (slots.size() < 3) {
                                try {
                                    //from 0 to 2
                                    System.out.println("Select slot in which you want to activate production:");
                                    slotNumber = scanner.nextInt();
                                    slotNumber--;
                                    if(!slots.contains(slotNumber)){
                                        slots.add(slotNumber);
                                        System.out.println("Do you want to select other slots in which activate production?");
                                        boolean okResponse = false;
                                        String response = "";
                                        while (!okResponse) {
                                            response = scanner.nextLine();
                                            response = response.toUpperCase();
                                            switch (response) {
                                                case "YES":
                                                    okSlot = false;
                                                    okResponse = true;
                                                    break;
                                                case "NO":
                                                    okSlot = true;
                                                    okResponse = true;
                                                    break;
                                                case "":
                                                    break;
                                                default:
                                                    System.out.println("Invalid input. Type again.");
                                                    break;
                                            }
                                        }
                                    } else {
                                        System.out.println("Slot already selected. Type again.");
                                    }
                                } catch (InputMismatchException e) {
                                    scanner.nextLine();
                                    System.out.println("Invalid input. Type again.");
                                }
                            } else {
                                System.out.println("From client: Already selected the maximum of 3 slots!");
                                okSlot = true;
                            }
                        }
                    }

                   leaderResource = leaderProductionRoutine();
                   System.out.println(slots.toString() + leaderResource);
                   done = true;
                   toReturn = new ActivateProductionMessage(slots, leaderResource);
                   break;
                case ("PERSONAL"):
                    //the user selects two resources from warehouse and a resource to get as output
                    System.out.println("Select two resources to take from warehouse as production input. Then select a resource to get as production output.");
                    List<Resource> resource = new ArrayList<>();
                    Resource prodIn1 = null;
                    Resource prodIn2 = null;
                    Resource prodOut = null;
                    resource.add(Resource.STONE); //in position 0
                    resource.add(Resource.COIN); //in position 1
                    resource.add(Resource.SERVANT); //in position 2
                    resource.add(Resource.SHIELD); //in position 3
                    showResources(resource);
                    Integer resourceIndex = 0;
                    String ask = null;
                    for(int i = 0; i < 3; i++) {
                        if (i == 0) ask = "first production input";
                        if (i == 1) ask = "second production input";
                        if (i == 2) ask = "production out";
                        Resource res = null;
                        boolean okResource = false;
                        while (!okResource){
                            System.out.println("Select " + ask + " resource:");
                            try {
                                resourceIndex = scanner.nextInt();
                                resourceIndex--;
                                switch (resourceIndex) {
                                    case 0:
                                        res = Resource.STONE;
                                        okResource = true;
                                        break;
                                    case 1:
                                        res = Resource.COIN;
                                        okResource = true;
                                        break;
                                    case 2:
                                        res = Resource.SERVANT;
                                        okResource = true;
                                        break;
                                    case 3:
                                        res = Resource.SHIELD;
                                        okResource = true;
                                        break;

                                    default:
                                        System.out.println("Invalid resource index. Select again.");
                                        break;
                                }
                                if (i == 0) prodIn1 = res;
                                if (i == 1) prodIn2 = res;
                                if (i == 2) prodOut = res;
                            } catch (InputMismatchException e) {
                                scanner.nextLine();
                                System.out.println("Invalid input. Type again.");
                            }
                        }
                    }
                    leaderResource = leaderProductionRoutine();
                    toReturn = new ActivatePersonalProductionMessage(prodIn1, prodIn2, prodOut, leaderResource);
                    done = true;
                    break;
                default:
                    System.out.println("Invalid input.Type again.");
                    break;
            }
        }
        return toReturn;
    }

    /**
     * Used to notify users when waiting in waiting room before starting the game. Tells how many other players
     * are waiting with them
     * @param s
     */
    @Override
    public void showParticipantsNumber(String s) {
        System.out.println("In waiting with:" + s + " players");
    }

    @Override
    public ClientMessage buyDVCard(ArrayList<DevelopmentCard> devCards, boolean err) {

        System.out.println(ANSITextFormat.BOLD.toString() + ANSITextFormat.ITALIC.toString() + "\n\t\t## DEVELOPMENT CARDS DECK ##\n" + ANSITextFormat.RESET);
        dvCardsTracer.printDVCard(clientLightModel.getDevelopmentCardsDeck()).forEach(System.out::println);

        int row = 0, column = 0, devCardSlot;


        System.out.println("Which Development card do you want to buy?");
        System.out.println();


//        if (err)
//            scanner.nextLine(); //flush the scanner;
        System.out.println("Choose a card or type \"return\" to choose another action: \n");


        char maxChar = (char) ('a' + devCards.size() - 1);

        String choice = secureReadString("(return|RETURN)|[a-" + maxChar + "]");

        if (choice.matches("(?i)(return)(?-i)")){ //when the player chooses return
            return null;
        }
        else {
            while (choice.charAt(0) < 'a' || choice.charAt(0) > 'a' + devCards.size() - 1) {
                System.out.println("Invalid input please try again: ");
                choice = secureReadString("[a-" + maxChar + "]");
            }

            System.out.println("In which cards slots do you want to put the development card in?");
            devCardSlot = secureReadInt("[1-3]");


            char charIn = choice.charAt(0);

            //first row in the DV deck
            if (charIn - 'a' < 4) {
                row = 1;
                column = charIn - 'a';
            }
            //second row in the DV deck
            else if (charIn - 'a' < 8) {
                row = 2;
                column = charIn - 'a' - 4;
            }
            //third row in the DV deck
            else if (charIn - 'a' < 12) {
                row = 3;
                column = charIn - 'a' - 8;
            }
            devCardSlot--; //make it starts from 0
            row--; //make it starts from 0

            System.out.println(row + " " + column + " " + devCardSlot + " ");

            return new BuyDevCardMessage(row, column, devCardSlot);
        }

}


    /**
     * method invoked to secure the read of user's input. Asks to type again if input integrity rules are not satisfied
     * @param pattern
     * @return
     */
    //reading securely using RegEx <3
    private String secureReadString(String pattern) {
        String input = "";
        boolean valid = false;
        while (!valid) {

            valid = true;
            input = scanner.nextLine();
            if (!Pattern.matches(pattern, input)) {
                System.out.println("Invalid input please try again with input between " + pattern + "\n");
                valid = false;
            }
        }
        return input;
    }
    /**
     * method invoked to secure the read of user's input. Asks to type again if input integrity rules are not satisfied
     * @param pattern
     * @return
     */
    private int secureReadInt(String pattern) {
        String input = "";
        boolean valid = false;
        while (!valid) {

            valid = true;
            input = scanner.nextLine();
            if (!Pattern.matches(pattern, input)) {
                System.out.println("Invalid input please try again with input between " + pattern + "\n");
                valid = false;
            }
        }
        return Integer.parseInt(input);
    }


    /**
     * this method manage the time that the client will be in the waiting room waiting for other players.
     * It allows the client to type commands on the terminal and to exit the game or to start it at any time.
     *
     * @return A ClientMessage object that will handle the choose of the client. It could be a AskStartGameMessage,
     * a ExitFromGameMessage or a EmptyMessage. The EmptyMessage is returned only when the game was started
     * from another client.
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
                if (buffer.ready()) {
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
     * This method is used from StartGameMessage to end the while(stillWaiting) loop of the method waitingRoom()
     */
    @Override
    public void endWaitingRoom() {
        stillWaiting = false;
    }

    @Override
    public void startGame() {
        this.showMessage("Game started!", false, false);
    }

    /**
     * A simple show method that prints messages on the console
     *
     * @param message message to print
     */
    @Override
    public void showMessage(String message, boolean forGuiAlso, boolean error) {
        if (error) System.out.println(ANSITextFormat.RED_COLOR+message+ANSITextFormat.RESET);
        else System.out.println(message);
    }

    /**
     * Prints in console specified leader cards
     *
     * @param leaderCards leader cards to print
     */
    @Override
    public void showLeaderCards(List<LeaderCard> leaderCards){
        System.out.println("Your leader cards:");
            ArrayList<String> output = leaderCardsTracer.printLeaderCards(leaderCards);
            output.forEach(System.out::println);
    }

    /**
     * prints in console specified resources
     * @param resources
     */
    @Override
    public void showResources(List<Resource> resources) {
        int i = 1;
        System.out.println(ANSITextFormat.ITALIC+"\t# Resources to store #\t"+ANSITextFormat.RESET);
        if(resources.size() > 0) {
           for (Resource res : resources) {
               System.out.println(i + ") " + res.toString() +" "+ ASCII_Resources.getShape(res.toString()));
               i++;
           }
        } else System.out.println("You don't have resources to store!");
    }

    /**
     * Prints development cards slot if non empty
     */
    public void showDevelopmentSlots(){
        System.out.println("\n\t"+ANSITextFormat.BOLD_ITALIC +"# Development Cards Slots # " + ANSITextFormat.RESET);
        HashMap<Integer, DevelopmentCard> devCardsSlot = clientLightModel.getPeekDevCardsInSlot();
        if(devCardsSlot.isEmpty())
            System.out.println("\nEmpty development card slots!");
        else {
            for (int i = 0; i < 3; i++) {
                //for(Integer i : devCardsSlot.keySet()){
                System.out.println("\n\t# Slot (" + (i + 1) + ") #\t");
                ArrayList<DevelopmentCard> dc = new ArrayList();
                if(devCardsSlot.containsKey(i)) {
                    dc.add(devCardsSlot.get(i));
                    dvCardsTracer.printDVCard(dc).stream().map(x->x.substring(4)).forEach(System.out::println);
                } else System.out.println("\n");
            }
        }
    }

    /**
     * Invoked by activateProduction: returns
     * @return the list of resources freely chose by the user if a production power leader card has been activated.
     * Returns null otherwise
     */
    public List<Resource> leaderProductionRoutine(){
        List<Resource> leaderResource = null;
        for(LeaderCard lc : clientLightModel.getLeaderCards()){
            if(lc.getCardType().equals(CardType.PRODUCTION))
                if(lc.isActivated()){
                    leaderResource = new ArrayList<>();
                    System.out.println("You have a production power leader card activated: if you have the needed resource to activate the leader power,");
                    System.out.println("select an additional resource to get from production. You will get it together with a faith path.\n");
                    List<Resource> leaderResources = new ArrayList<>();
                    leaderResources.add(Resource.STONE); //in position 0
                    leaderResources.add(Resource.COIN); //in position 1
                    leaderResources.add(Resource.SERVANT); //in position 2
                    leaderResources.add(Resource.SHIELD); //in position 3
                    showResources(leaderResources);
                    Integer resourceIndex = 0;
                    boolean okResource = false;
                    System.out.println("Select a resource:");
                    while(!okResource){
                        try {
                            resourceIndex = scanner.nextInt();
                            resourceIndex--;
                            switch(resourceIndex){
                                case 0:
                                    leaderResource.add(Resource.STONE);
                                    okResource = true;
                                    break;
                                case 1:
                                    leaderResource.add(Resource.COIN);
                                    okResource = true;
                                    break;
                                case 2:
                                    leaderResource.add(Resource.SERVANT);
                                    okResource = true;
                                    break;
                                case 3:
                                    leaderResource.add(Resource.SHIELD);
                                    okResource = true;
                                    break;

                                default:
                                    System.out.println("Invalid resource index. Select again.");
                                    break;
                            }
                            okResource = true;
                        } catch (InputMismatchException e){
                            scanner.nextLine();
                            System.out.println("Invalid input. Type again.");
                        }
                    }
                }
        }
        return leaderResource;
    }


}
