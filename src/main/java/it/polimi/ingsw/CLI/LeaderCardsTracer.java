package it.polimi.ingsw.CLI;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardCost;
import it.polimi.ingsw.model.cards.LeaderCards.DiscountLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.ProdPowerLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.StorageLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.WhiteMarbleLeaderCard;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.exceptions.EmptySlotException;
import it.polimi.ingsw.model.exceptions.IllegalInsertionException;
import it.polimi.ingsw.model.exceptions.StorageOutOfBoundsException;
import it.polimi.ingsw.model.parser.LeaderCardParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.enumerations.CardType.*;

public class LeaderCardsTracer {
    public ArrayList<String> printLeaderCards(List<LeaderCard> leaderCards) throws EmptySlotException {
        ArrayList<String> results = new ArrayList<>();


        HashMap<Resource, Integer> storageCardCost = new HashMap<>();
        char cardChar = 'a'; //the char before "a" in ascii code
        String costString = "";
        String powerString = "";

        String stringFormat = "%-6s %-22s %-12d %-33s %-40s"; // "CARD", "TYPE", "V_POINTS", "*_COST_*", "LEADER_POWER"

        String tempStr = "";
        results.add("\t # Leader Cards # \t");

        results.add(String.format("%-6s %-12s %-12s %-35s %-20s", "CARD", "TYPE", "V_POINTS", "*_COST_*", "LEADER_POWER"));

        for (LeaderCard leaderCard : leaderCards) {

            if ((List.of(DISCOUNT, PRODUCTION, MARBLE).contains(leaderCard.getCardType()))) {
                if (DISCOUNT.equals(leaderCard.getCardType())) {
                    DiscountLeaderCard discountLeaderCard = (DiscountLeaderCard) leaderCard;
                    //getting the activation cost of disc. leader card as ascii shapes
                    for (LeaderCardCost leaderCardCost : discountLeaderCard.getActivationCost()) {

                        String value = leaderCardCost.getLevel().toString();

                        costString += value + ASCII_DV_Cards.getDVShape(leaderCardCost.getColor().toString()) + ", ";
                    }//getting the disc. leader card power as ascii shapes
                    for (Resource resource : discountLeaderCard.getLeaderCardPower().keySet()) {

                        String value = discountLeaderCard.getLeaderCardPower().get(resource).toString();

                        powerString += "\u001b[1m" + "-" + value + "\u001B[0m" + ASCII_Resources.getShape(resource.toString()) + ",";
                    }
                    powerString = "           " + powerString;  //adding (11) spacing before power string
                    costString = costString.substring(0, costString.length() - 2); //remove the last ", "
                    powerString = powerString.substring(0, powerString.length() - 1); //remove the last ","

                    results.add(String.format(stringFormat, "(" + cardChar++ + ")", "\u001b[1m" + discountLeaderCard.getCardType() + "\u001B[0m", discountLeaderCard.getVictoryPoints(), costString, powerString));
                    costString = "";
                    powerString = "";

                } else if (PRODUCTION.equals(leaderCard.getCardType())) {
                    ProdPowerLeaderCard prodPowerLeaderCard = (ProdPowerLeaderCard) leaderCard;
                    //getting the activation cost of prod. leader card as ascii shapes
                    for (LeaderCardCost leaderCardCost : prodPowerLeaderCard.getActivationCost()) {

                        String value = leaderCardCost.getLevel().toString();

                        costString += value + ASCII_DV_Cards.getDVShape(leaderCardCost.getColor().toString()) + ", ";
                    }//getting the prod. leader card power as ascii shapes
                    for (Resource resource : prodPowerLeaderCard.getLeaderCardPower().keySet()) {

                        String value = prodPowerLeaderCard.getLeaderCardPower().get(resource).toString();

                        powerString += value + ASCII_Resources.getShape(resource.toString()) + " ❭ " + prodPowerLeaderCard.getOutProductionResourceNum() + "(?), " + prodPowerLeaderCard.getOutProductionFaithPoints() + "✢";
                    }
                    powerString = "  " + powerString; //adding (9) spacing before power string
                    costString = costString.substring(0, costString.length() - 2); //remove the last ", "
//                    powerString = powerString.substring(0, powerString.length() - 1); //remove the last ","
                    results.add(String.format(stringFormat, "(" + cardChar++ + ")", "\u001b[1m" + prodPowerLeaderCard.getCardType() + "\u001B[0m", prodPowerLeaderCard.getVictoryPoints(), costString, powerString));
                    costString = "";
                    powerString = "";

                } else if (MARBLE.equals(leaderCard.getCardType())) {
                    WhiteMarbleLeaderCard whiteMarbleLeaderCard = (WhiteMarbleLeaderCard) leaderCard;
                    //getting the activation cost of marble leader card as ascii shapes
                    for (LeaderCardCost leaderCardCost : whiteMarbleLeaderCard.getActivationCost()) {

                        String value = leaderCardCost.getLevel().toString();

                        costString += value + ASCII_DV_Cards.getDVShape(leaderCardCost.getColor().toString()) + ", ";
                    }//getting the marble leader card power as ascii shapes
                    for (Resource resource : whiteMarbleLeaderCard.getLeaderCardPower().keySet()) {

                        String value = whiteMarbleLeaderCard.getLeaderCardPower().get(resource).toString();

                        powerString += ASCII_Marbles.WHITE + "\u001b[1m = \u001B[0m" + value + ASCII_Resources.getShape(resource.toString());
                    }
                    powerString = "         " + powerString; //adding (9) spacing before power string
                    costString = costString.substring(0, costString.length() - 2); //remove the last ", "
//                    powerString = powerString.substring(0, powerString.length() - 1); //remove the last ","
                    results.add(String.format(stringFormat, "(" + cardChar++ + ")", "\u001b[1m" + whiteMarbleLeaderCard.getCardType() + "\u001B[0m", whiteMarbleLeaderCard.getVictoryPoints(), costString, powerString));
                    costString = "";
                    powerString = "";

                }

            } else if (STORAGE.equals(leaderCard.getCardType())) {
                String spacing = "0";
                String spacingServant = "    ";
                String spacingStoneShield = "  ";
                String spacingCoin = "      ";

                StorageLeaderCard storageLeaderCard = (StorageLeaderCard) leaderCard;
                //getting the activation cost of storage leader card as ascii shapes
                    //Assertion: there is just one type of resource in a leader card of type storage
                String temStr = "0";
                for (Resource resource : storageLeaderCard.getActivationCost().keySet()) {

                    String value = storageLeaderCard.getActivationCost().get(resource).toString();

                    costString += value + ASCII_Resources.getShape(resource.toString()) + ", ";
                }

                //the type of the resource of  that specific Storage Leader Card.
                Resource leaderResource = storageLeaderCard.getSlots().keySet().stream().findAny().get();


                if (leaderResource.toString().equals("COIN")){
                    spacing = spacingCoin;
                }else if (leaderResource.toString().equals("STONE")|leaderResource.toString().equals("SHIELD")){
                    spacing=spacingStoneShield;
                }
                else {spacing=spacingServant;}

                if (storageLeaderCard.getOccupiedSlots()>0) {

                    //getting the marble leader card power as ascii shapes  //the hashMap returns the full slots of the StorageLeaderCard, the getMaxCapacity() returns the max capacity even empty slots.

                    int maxCapacity = storageLeaderCard.getMaxCapacity();
                    int emptyCap = maxCapacity - storageLeaderCard.getLeaderCardPower().get(leaderResource);

                    //prints the occupied slots withe the respective resources
                    for (int i = 0; i < (maxCapacity - emptyCap); i++) {
                        powerString += "\u001b[48;5;28m " +ASCII_Resources.getShape(leaderResource.toString()) + " " + "\u001B[0m" ;
                    }
                    //prints the empty slots (with a different color and shady background)
                    for (int i = 0; i < (emptyCap); i++) {
                        powerString += "\u001b[48;5;16m " + ASCII_Resources.getShape(leaderResource.toString()) + " " + "\u001B[0m";
                    }
                }else{
                    //prints the empty slots (with a different color and shady background)
                    for (int i = 0; i < (storageLeaderCard.getMaxCapacity()); i++) {
                        powerString += "\u001b[48;5;16m " + ASCII_Resources.getShape(leaderResource.toString()) + " " +"\u001B[0m";
                        tempStr="\u001b[48;5;16m " + ASCII_Resources.getShape(leaderResource.toString()) + "  " +"  \u001B[0m";
                    }
                }
//                    powerString = powerString.substring(0,powerString.length() - 1 ) ;
                System.out.println("\n"+tempStr+"\n");


//                for (Resource resource : storageLeaderCard.getLeaderCardPower().keySet()) {
//
//                    String value = storageLeaderCard.getLeaderCardPower().get(resource).toString();
//
//                    for (int i = 0; i < 2; i++) {
//                        if (i > Character.getNumericValue((value).charAt(0))) {
//                            powerString += "1" +"\u001b[48;5;246m"+ " " + ASCII_Resources.getShape(resource.toString()) + " " + "\u001b[0m" ;
//                        }
//                    }
//                }
//                costString = costString.substring(0, costString.length()-2); //remove the last ", "



                powerString = spacing + powerString; //adding (9) spacing before power string
                results.add(String.format(stringFormat, "(" + cardChar++ + ")", "\u001b[1m" + storageLeaderCard.getCardType() + "\u001B[0m", storageLeaderCard.getVictoryPoints(), costString, powerString));

                //reinitialize
                costString = "";
                powerString = "";
            }


        }


        return results;
    }

    private String getCardCost() {
        return null;
    }


    public static void main(String[] args) throws EmptySlotException, StorageOutOfBoundsException, IllegalInsertionException {
        LeaderCardParser leaderCardParser = new LeaderCardParser("src/main/java/it/polimi/ingsw/model/jsonFiles/LeaderCardJson.json");
        List<LeaderCard> leaderCards = leaderCardParser.getLeaderCardsDeck();
        leaderCardParser.close();


        //leaderCards1 do not contains <Marble> leader cards
        List<LeaderCard> leaderCards1 = leaderCards.stream()
                .filter(x -> !(x.getCardType().equals(CardType.STORAGE)))
                .collect(Collectors.toList());

        List<StorageLeaderCard> leaderCards2 = (List<StorageLeaderCard>)(List<?>) leaderCards.stream()
                .filter(x -> (x.getCardType().equals(CardType.STORAGE)))
                .map(x->x=(StorageLeaderCard)x)
                .collect(Collectors.toList());


        Optional<StorageLeaderCard> st =leaderCards2.stream().filter(x->x.storageType().equals(Resource.COIN)).findFirst();
               st.get().putResourceInCardStorage(null,Resource.COIN);

        Optional<StorageLeaderCard> st2 =leaderCards2.stream().filter(x->x.storageType().equals(Resource.SHIELD)).findFirst();
        st2.get().putResourceInCardStorage(null,Resource.SHIELD);
        st2.get().putResourceInCardStorage(null,Resource.SHIELD);




        System.out.println(st.get().getOccupiedSlots());
//        leaderCards2.stream().findAny().get().putResourceInCardStorage(null,Resource.STONE);

        LeaderCardsTracer leaderCardsTracer = new LeaderCardsTracer();
        ArrayList<String> output = leaderCardsTracer.printLeaderCards(leaderCards);
        output.forEach(System.out::println);

    }

}
