package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardCost;
import it.polimi.ingsw.model.cards.LeaderCards.DiscountLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.ProdPowerLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.StorageLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.WhiteMarbleLeaderCard;
import it.polimi.ingsw.enumerations.*;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.parser.LeaderCardParser;
import it.polimi.ingsw.server.handlers.SinglePlayerGameHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static it.polimi.ingsw.enumerations.CardType.*;

/**
 * Class that traces leader cards for the CLI
 */
public class LeaderCardsTracer {

    //private static final Logger logger = Logger.getLogger(SinglePlayerGameHandler.class.getName());

    public ArrayList<String> printLeaderCards(List<LeaderCard> leaderCards) {
        ArrayList<String> results = new ArrayList<>();


        HashMap<Resource, Integer> storageCardCost = new HashMap<>();
        char cardChar = 'a'; //the char before "a" in ascii code
        String costString = "";
        String statusString = "";
        String powerString = "";

        String stringFormat = "%-6s %-18s %-22s %-12d %-33s %-30s"; // "CARD", "TYPE", "V_POINTS", "*_COST_*", "LEADER_POWER"

        String tempStr = "";
        results.add("\n\t\t" + ANSITextFormat.BOLD_ITALIC +"# Leader Cards #"+ ANSITextFormat.RESET +"\t");

        results.add(String.format("%-6s %-18s %-12s %-13s %-27s %-15s", "CARD", "STATUS", "TYPE", "V_POINTS", "*_COST_*", "LEADER_POWER"));

        for (LeaderCard leaderCard : leaderCards) {

            if ((List.of(DISCOUNT, PRODUCTION, MARBLE).contains(leaderCard.getCardType()))) {
                if (DISCOUNT.equals(leaderCard.getCardType())) {
                    DiscountLeaderCard discountLeaderCard = (DiscountLeaderCard) leaderCard;
                    //getting the activation cost of disc. leader card as ascii shapes
                    for (LeaderCardCost leaderCardCost : discountLeaderCard.getActivationCost()) {

                        String value = leaderCardCost.getLevel().toString();

                        costString += value + ASCII_DV_Cards.getDVShape(leaderCardCost.getColor().toString()) + ", ";
                    }

                    if(leaderCard.isActivated()){
                        statusString = ANSITextFormat.GREEN_COLOR+"ACTIVATED"+ANSITextFormat.RESET+"\t\t ";
                    } else if(leaderCard.isDiscarded()){
                        statusString = ANSITextFormat.RED_COLOR+"DISCARDED"+ANSITextFormat.RESET+"\t\t ";
                    } else {
                        statusString = "NOT ACTIVATED";
                    }

                    //getting the disc. leader card power as ascii shapes
                    for (Resource resource : discountLeaderCard.getLeaderCardPower().keySet()) {

                        String value = discountLeaderCard.getLeaderCardPower().get(resource).toString();

                        powerString += ANSITextFormat.BOLD + "-" + value + ANSITextFormat.RESET.toString() + ASCII_Resources.getShape(resource.toString()) + ",";
                    }
                    powerString = "           " + powerString;  //adding (11) spacing before power string
                    costString = costString.substring(0, costString.length() - 2); //remove the last ", "
                    powerString = powerString.substring(0, powerString.length() - 1); //remove the last ","

                    results.add(String.format(stringFormat, "(" + cardChar++ + ")",statusString, ANSITextFormat.BOLD.toString() + discountLeaderCard.getCardType() + ANSITextFormat.RESET.toString(), discountLeaderCard.getVictoryPoints(), costString, powerString,"|"));
                    costString = "";
                    powerString = "";

                } else if (PRODUCTION.equals(leaderCard.getCardType())) {
                    ProdPowerLeaderCard prodPowerLeaderCard = (ProdPowerLeaderCard) leaderCard;
                    //getting the activation cost of prod. leader card as ascii shapes
                    for (LeaderCardCost leaderCardCost : prodPowerLeaderCard.getActivationCost()) {

                        String value = leaderCardCost.getLevel().toString();

                        costString += value + ASCII_DV_Cards.getDVShape(leaderCardCost.getColor().toString()) + ", ";
                    }

                    if(leaderCard.isActivated()){
                        statusString = ANSITextFormat.GREEN_COLOR+"ACTIVATED"+ANSITextFormat.RESET+"\t\t ";
                    } else if(leaderCard.isDiscarded()){
                        statusString = ANSITextFormat.RED_COLOR+"DISCARDED"+ANSITextFormat.RESET+"\t\t ";
                    } else {
                        statusString = "NOT ACTIVATED";
                    }

                    //getting the prod. leader card power as ascii shapes
                    for (Resource resource : prodPowerLeaderCard.getLeaderCardPower().keySet()) {

                        String value = prodPowerLeaderCard.getLeaderCardPower().get(resource).toString();

                        powerString += value + ASCII_Resources.getShape(resource.toString()) + " ❭ " + prodPowerLeaderCard.getOutProductionResourceNum() + "(?), " + prodPowerLeaderCard.getOutProductionFaithPoints() + "✢";
                    }
                    powerString = "  " + powerString; //adding (9) spacing before power string
                    costString = costString.substring(0, costString.length() - 2); //remove the last ", "
//                    powerString = powerString.substring(0, powerString.length() - 1); //remove the last ","
                    results.add(String.format(stringFormat, "(" + cardChar++ + ")",statusString, ANSITextFormat.BOLD.toString() + prodPowerLeaderCard.getCardType() + ANSITextFormat.RESET.toString(), prodPowerLeaderCard.getVictoryPoints(), costString, powerString,"|"));
                    costString = "";
                    powerString = "";

                } else if (MARBLE.equals(leaderCard.getCardType())) {
                    WhiteMarbleLeaderCard whiteMarbleLeaderCard = (WhiteMarbleLeaderCard) leaderCard;
                    //getting the activation cost of marble leader card as ascii shapes
                    for (LeaderCardCost leaderCardCost : whiteMarbleLeaderCard.getActivationCost()) {

                        String value = leaderCardCost.getLevel().toString();

                        costString += value + ASCII_DV_Cards.getDVShape(leaderCardCost.getColor().toString()) + ", ";
                    }

                    if(leaderCard.isActivated()){
                        statusString = ANSITextFormat.GREEN_COLOR+"ACTIVATED"+ANSITextFormat.RESET+"\t\t ";
                    } else if(leaderCard.isDiscarded()){
                        statusString = ANSITextFormat.RED_COLOR+"DISCARDED"+ANSITextFormat.RESET+"\t\t ";
                    } else {
                        statusString = "NOT ACTIVATED";
                    }

                    //getting the marble leader card power as ascii shapes
                    for (Resource resource : whiteMarbleLeaderCard.getLeaderCardPower().keySet()) {

                        String value = whiteMarbleLeaderCard.getLeaderCardPower().get(resource).toString();

                        powerString += ASCII_Marbles.WHITE + ANSITextFormat.BOLD.toString() + "="+ ANSITextFormat.RESET + value + ASCII_Resources.getShape(resource.toString());
                    }
                    powerString = "         " + powerString; //adding (9) spacing before power string
                    costString = costString.substring(0, costString.length() - 2); //remove the last ", "
//                    powerString = powerString.substring(0, powerString.length() - 1); //remove the last ","
                    results.add(String.format(stringFormat, "(" + cardChar++ + ")",statusString, ANSITextFormat.BOLD.toString() + whiteMarbleLeaderCard.getCardType() + ANSITextFormat.RESET.toString(), whiteMarbleLeaderCard.getVictoryPoints(), costString, powerString,"|"));
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

                //the type of the resource of that specific Storage Leader Card.
                Resource leaderResource = storageLeaderCard.storageType();


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
                        powerString += "\u001b[48;5;28m " +ASCII_Resources.getShape(leaderResource.toString()) + " " + ANSITextFormat.RESET.toString() ;
                    }
                    //prints the empty slots (with a different color and shady background)
                    for (int i = 0; i < (emptyCap); i++) {
                        powerString += "\u001b[48;5;16m " + ASCII_Resources.getShape(leaderResource.toString()) + " " + ANSITextFormat.RESET.toString();
                    }
                }else{
                    //prints the empty slots (with a different color and shady background)
                    for (int i = 0; i < (storageLeaderCard.getMaxCapacity()); i++) {
                        powerString += "\u001b[48;5;16m " + ASCII_Resources.getShape(leaderResource.toString()) + " " +ANSITextFormat.RESET.toString();
                        tempStr="\u001b[48;5;16m " + ASCII_Resources.getShape(leaderResource.toString()) + "  " +"  \u001B[0m";
                    }
                }

                if(leaderCard.isActivated()){
                    statusString = ANSITextFormat.GREEN_COLOR+"ACTIVATED"+ANSITextFormat.RESET+"\t\t ";
                } else if(leaderCard.isDiscarded()){
                    statusString = ANSITextFormat.RED_COLOR+"DISCARDED"+ANSITextFormat.RESET+"\t\t ";
                } else {
                    statusString = "NOT ACTIVATED";
                }

                powerString = spacing + powerString; //adding (9) spacing before power string
                results.add(String.format(stringFormat, "(" + cardChar++ + ")",statusString, ANSITextFormat.BOLD.toString() + storageLeaderCard.getCardType() + ANSITextFormat.RESET.toString(), storageLeaderCard.getVictoryPoints(), costString, powerString,"|"));

                //reinitialize
                costString = "";
                powerString = "";
            }


        }

        return results;
    }

}
