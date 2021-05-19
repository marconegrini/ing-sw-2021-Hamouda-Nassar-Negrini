package it.polimi.ingsw.CLI;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardCost;
import it.polimi.ingsw.model.cards.LeaderCards.DiscountLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.ProdPowerLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.WhiteMarbleLeaderCard;
import it.polimi.ingsw.model.enumerations.ASCII_DV_Cards;
import it.polimi.ingsw.model.enumerations.ASCII_Resources;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static it.polimi.ingsw.model.enumerations.CardType.*;

public class LeaderCardsTracer {
    public ArrayList<String> printLeaderCards(ArrayList<LeaderCard> leaderCards) {
        ArrayList<String> results = new ArrayList<>();

        ArrayList<List<LeaderCardCost>> cardCostsList = new ArrayList<>();
        HashMap<Resource, Integer> storageCardCost = new HashMap<>();
        char cardChar = '`'; //the char before "a" in ascii code
        String costString = "";
        String powerString = "";

        String stringFormat = "%-6s %-20s %-12d %-20s %-20s"; // "CARD", "TYPE", "V_POINTS", "*_COST_*", "LEADER_POWER"

        String tempStr = "";
        results.add("\t # Leader Cards # \t");

        results.add(String.format("%-6s %-20s %-12s %-20s %-20s", "CARD", "TYPE", "V_POINTS", "*_COST_*", "LEADER_POWER"));

        for (LeaderCard leaderCard : leaderCards) {

            if ((List.of(DISCOUNT, PRODUCTION, MARBLE).contains(leaderCard.getCardType()))) {
                if (DISCOUNT.equals(leaderCard.getCardType())) {
                    DiscountLeaderCard discountLeaderCard = (DiscountLeaderCard) leaderCard;
                    cardCostsList.add(discountLeaderCard.getActivationCost());
                    //getting the activation cost of disc. leader card as ascii shapes
                    for (LeaderCardCost leaderCardCost : discountLeaderCard.getActivationCost()) {

                        String value = leaderCardCost.getLevel().toString();

                        costString += value + ASCII_DV_Cards.getDVShape(leaderCardCost.getColor().toString()) + ",";
                    }//getting the disc. leader card power as ascii shapes
                    for (Resource resource : discountLeaderCard.getLeaderCardPower().keySet()) {

                        String value = discountLeaderCard.getLeaderCardPower().get(resource).toString();

                        powerString += "-" +value + ASCII_Resources.getShape(resource.toString()) + ",";
                    }
                    results.add( String.format(stringFormat, cardChar++, discountLeaderCard.getCardType(), discountLeaderCard.getVictoryPoints(), costString, powerString));


                } else if (PRODUCTION.equals(leaderCard.getCardType())) {
                    ProdPowerLeaderCard prodPowerLeaderCard = (ProdPowerLeaderCard) leaderCard;
                    cardCostsList.add(prodPowerLeaderCard.getActivationCost());
                    //getting the activation cost of prod. leader card as ascii shapes
                    for (LeaderCardCost leaderCardCost : prodPowerLeaderCard.getActivationCost()) {

                        String value = leaderCardCost.getLevel().toString();

                        costString += value + ASCII_DV_Cards.getDVShape(leaderCardCost.getColor().toString()) + ",";
                    }//getting the prod. leader card power as ascii shapes
                    for (Resource resource : prodPowerLeaderCard.getLeaderCardPower().keySet()) {

                        String value = prodPowerLeaderCard.getLeaderCardPower().get(resource).toString();

                        powerString += value + ASCII_Resources.getShape(resource.toString())  + "" + ",";
                    }
                    results.add( String.format(stringFormat, cardChar++, prodPowerLeaderCard.getCardType(), prodPowerLeaderCard.getVictoryPoints(), costString, powerString));


                } else if (MARBLE.equals(leaderCard.getCardType())) {
                    WhiteMarbleLeaderCard whiteMarbleLeaderCard = (WhiteMarbleLeaderCard) leaderCard;
                    cardCostsList.add(whiteMarbleLeaderCard.getActivationCost());
                    //getting the activation cost of disc. leader card as ascii shapes
                    for (LeaderCardCost leaderCardCost : whiteMarbleLeaderCard.getActivationCost()) {

                        String value = leaderCardCost.getLevel().toString();

                        costString += value + ASCII_DV_Cards.getDVShape(leaderCardCost.getColor().toString()) + ",";
                    }//getting the disc. leader card power as ascii shapes
                    for (Resource resource : whiteMarbleLeaderCard.getLeaderCardPower().keySet()) {

                        String value = whiteMarbleLeaderCard.getLeaderCardPower().get(resource).toString();

                        powerString += value + ASCII_Resources.getShape(resource.toString()) + ",";
                    }
                    results.add( String.format(stringFormat, cardChar++, whiteMarbleLeaderCard.getCardType(), whiteMarbleLeaderCard.getVictoryPoints(), costString, powerString));

                }

            }


        }


        return results;
    }

    private String getCardCost() {
return null;
    }


}
