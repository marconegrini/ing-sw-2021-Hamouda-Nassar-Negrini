package it.polimi.ingsw.CLI;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enumerations.ASCII_Shapes;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.parser.DevelopmentCardParser;

import java.util.ArrayList;

public class CliDvCardsTracer {
    /**
     * Method that takes any dvCards ArrayList and gives bake a list of Strings to be printed consequently as every string is a dv Card for the CLI
     * @param dvCards ArrayList of development cards
     * @return List of strings, every string (line) represents a dvCard for the CLI
     */
    public ArrayList<String> printDVCard(ArrayList<DevelopmentCard> dvCards) {

        ArrayList<String> results = new ArrayList<String>();
        String cardLetter;
        char cardChar;
        cardChar = '`';
        cardLetter = "`"; //the char before "a" in ascii code
        boolean withNumbers=false;
        int number = 0;
        int maxStrCost=0;
        int maxStrProdIn=0;
        int maxStrProdOut=0;

//        System.out.println(ASCII_Shapes.COIN);
//        System.out.println(ASCII_Shapes.SHIELD);
//        System.out.println(ASCII_Shapes.STONE);
//        System.out.println(ASCII_Shapes.SERVANT);

        results.add(String.format("%-6s %-8s %-9s %-12s %-20s %-20s %-20s", "CARD", "COLOR", "LEVEL", "V_POINTS", "*_COST_*", "REQUIRES", "GIVES"));


        for (DevelopmentCard dvCard : dvCards) {

            ColorEnum colorCard = ColorEnum.getBackgroundEnum(dvCard.getColor().toString());
            String stringFormat =     "%-6s" + colorCard + "\u001b[30m" + "%-8s" + "\t"+ "%-9s %-12d" + ColorEnum.RESET ; //+ "\t%-30"+ // %30.30s %20.30s";

            cardChar++;
            cardLetter=String.valueOf(cardChar);
            if (cardChar > 'z' | !withNumbers)
            {
                cardChar='a';
                withNumbers=true;
            }

            if (withNumbers && ( cardChar == 'a' ))
            {
                number++;
            }

            if (withNumbers)
            {
                cardLetter = String.valueOf(cardChar) + number;
            }

            ArrayList<String> shapes = getShapes(dvCard);


            cardLetter="("+cardLetter+")";

            String tempStr = String.format(stringFormat, cardLetter, dvCard.getColor(), dvCard.getLevel(), dvCard.getVictoryPoints());

            //1 elem => 0 delete of the next dashs
            //2 elem => 4 delete of the next dashes
            //3 elem => 8 delete of the next dashes

//            String varDash1 = "---";
//            String varDash2 = "----------------";
//            String varDash3 = "---------------"; // + "--->"

            String varDash1 = "   ";
            String varDash2 = "                ";
            String varDash3 = "         --->    ";

//            System.out.println("length"+shapes.get(0).length());
            int elemOfShape0 = dvCard.getCardCost().keySet().size();
            int elemOfShape1 = dvCard.getProductionIn().keySet().size();
            int elemOfShape2 = dvCard.getProductionOut().keySet().size();


            if (elemOfShape0 == 2) {
                varDash2 = varDash2.substring(4); //remove three dashes
            }
            if (elemOfShape0 == 3) {
                varDash2 = varDash2.substring(8); //remove 7 dashes
            }

            if (elemOfShape1 == 2) {
                varDash3 = varDash3.substring(4); //remove three dashes
            }
            if (elemOfShape1 == 3) {
                varDash3 = varDash3.substring(8); //remove three dashes
            }


            tempStr =  tempStr + varDash1 + shapes.get(0)+ varDash2 + shapes.get(1) + varDash3 + shapes.get(2) ;
            results.add(tempStr);

        }
        return results;
    }

    //    @org.jetbrains.annotations.NotNull
    private ArrayList<String> getShapes(DevelopmentCard dvCard) {
        ArrayList<String> strings=new ArrayList<>();
        String costString="";
        String productionInString="";
        String productionOutString="";




        for(Resource resource: dvCard.getCardCost().keySet()) {

            String value = dvCard.getCardCost().get(resource).toString();

            costString += value + ASCII_Shapes.getShape(resource.toString()) + ",";
        }
        costString=costString.substring(0,costString.length()-1); //remove the last ","
        costString="❰"+costString+"❱";
        for(Resource resource: dvCard.getProductionIn().keySet()) {

            String value = dvCard.getProductionIn().get(resource).toString();

            productionInString += value + ASCII_Shapes.getShape(resource.toString()) + ",";
        }
        productionInString = productionInString.substring(0,productionInString.length()-1); //remove the last ","
        productionInString="❰"+productionInString+"❱";
        for(Resource resource: dvCard.getProductionOut().keySet()) {

            String value = dvCard.getProductionOut().get(resource).toString();

            productionOutString += value + ASCII_Shapes.getShape(resource.toString()) + ",";
        }
        productionOutString=productionOutString.substring(0,productionOutString.length()-1); //remove the last ","
        productionOutString="❰"+productionOutString+"❱";


//        System.out.println(costString);
//        System.out.println(productionInString);
//        System.out.println(productionOutString);

        strings.add(costString);
        strings.add(productionInString);
        strings.add(productionOutString);

        return strings;
    }

    //for testing purpose
    public static void main(String[] args) {

        DevelopmentCardParser parser = new DevelopmentCardParser("src/main/java/it/polimi/ingsw/model/jsonFiles/DevCardJson.json");
        ArrayList<DevelopmentCard> deck = parser.getDevelopmentCardsDeck();
        parser.close();

//        deck.forEach(System.out::println);

        CliDvCardsTracer traceDvCards = new CliDvCardsTracer();
        traceDvCards.printDVCard(deck).forEach(System.out::println);
    }

}
